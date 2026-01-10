package concordia;

//	import Mainframe;
/*------------------------------------------------------------------------------------------------------------------*/
// CompanyItemTablePanel Class retrieves the data structure and renders both the Companies and Items tables.
// CompanyItemTablePanel also has 2 anonymous class containing  action listeners,called for when user selects an table item
// from Company or Item tables respectively.
// Selecting a row in the Company table has the effect of updating the Items Window. 
// Selecting a row in the Items table has the effect of updating the History table (class located in Table Window 2).
//
// Both the Companies and Items tables are non editable - risk of data inconsistency too high
/*-------------------------------------------------------------------------------------------------------------------*/
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.Dimension;
import java.util.List;
import java.util.function.Consumer;
import concordia.domain.Company;
import concordia.domain.Item;

public class CompanyItemTablePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final boolean FALSE = false;
    private boolean DEBUG = false;
    public JTable companyTable;
    public JTextField companyFilterText;
    public JTextArea companyStatusText;
    public TableRowSorter<CompanyModel> companyNameSorter;

    /// Table Panel 2
    public JTable itemTable;
    public JTextField itemFilterText;
    public JTextArea itemStatusText;
    public TableRowSorter<ItemModel> itemNameSorter;
    public final List<Company> companies;
    public ItemModel[] itemModelRefreshRef = new ItemModel[1];
    public CompanyModel[] companyModelRefreshRef = new CompanyModel[1];
    private Consumer<Company> companySelectionListener;
    private Consumer<Item> itemSelectionListener;

    // Code for first 2 windows i.e. company and items window

        public CompanyItemTablePanel(List<Company> companies) {
        super();
        setOpaque(true);
        setBackground(new java.awt.Color(240, 240, 255));
        this.companies = companies;

        // Use a split pane so the company list consumes ~1/3 of the height and
        // the item list the remaining 2/3.
        setLayout(new java.awt.BorderLayout());
        companyModelRefreshRef[0] = new CompanyModel(this.companies);
        companyNameSorter = new TableRowSorter<CompanyModel>(companyModelRefreshRef[0]);
        companyTable = new JTable(companyModelRefreshRef[0]) {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; // Cancel the editing of any cell
            }
        };
        java.awt.Font itemTableFont = new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 13);
        companyTable.setFont(itemTableFont);
        companyTable.setRowHeight(24);
        companyTable.setSelectionBackground(new java.awt.Color(220, 220, 255));
        companyTable.setSelectionForeground(java.awt.Color.BLACK);
        companyTable.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        int viewRow = companyTable.getSelectedRow();
                        if (viewRow < 0) {
                            companyStatusText.setText("");
                        } else {
                            int modelRow = companyTable.convertRowIndexToModel(viewRow);
                            Company selectedCompany = companyModelRefreshRef[0].getCompanyAtRow(modelRow);
                            if (selectedCompany == null) {
                                return;
                            }
                            companyStatusText.setText(String.format(" Company selected: %s.", selectedCompany.getCompanyName()));
                            refreshItemsForCompany(selectedCompany);
                            if (companySelectionListener != null) {
                                companySelectionListener.accept(selectedCompany);
                            }
                        }
                    }
                });
        JScrollPane companyTableScrollPane = new JScrollPane(companyTable);
        companyTableScrollPane.setPreferredSize(new Dimension(400, 160));
        JLabel companyFilterTextLabel = new JLabel("Filter Text:", SwingConstants.TRAILING);
        companyFilterTextLabel.setPreferredSize(new Dimension(10, 10));
        companyFilterText = new JTextField();
        companyFilterText.setPreferredSize(new Dimension(Short.MAX_VALUE, 24));
        companyFilterText.setMaximumSize(new Dimension(Short.MAX_VALUE, 24));
        // Whenever companyFilterText changes, invoke filterCompaniesTable.
        companyFilterText.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        filterCompaniesTable();
                    }

                    public void insertUpdate(DocumentEvent e) {
                        filterCompaniesTable();
                    }

                    public void removeUpdate(DocumentEvent e) {
                        filterCompaniesTable();
                    }
                });
    companyFilterTextLabel.setLabelFor(companyFilterText);
    JLabel companyNotesLabel = new JLabel("Notes:", SwingConstants.TRAILING);
    companyNotesLabel.setPreferredSize(new Dimension(50, 50));
    companyNotesLabel.setLabelFor(companyStatusText);
    companyStatusText = new JTextArea("History for", 5, 20);
    companyStatusText.setLineWrap(true);
    companyStatusText.setWrapStyleWord(true);
    companyStatusText.setEditable(false);
    JPanel companyControlsPanel = createFilterAndNotesPanel(
        companyFilterTextLabel,
        companyFilterText,
        companyNotesLabel,
        companyStatusText);

        // --- FIX: Declare and initialize l12, l22, and form2 for the second
        // filter/notes panel ---
        JLabel itemFilterTextLabel = new JLabel("Filter Text:", SwingConstants.TRAILING);
        itemFilterTextLabel.setPreferredSize(new Dimension(10, 10));
        itemFilterText = new JTextField();
        itemFilterText.setPreferredSize(new Dimension(Short.MAX_VALUE, 24));
        itemFilterText.setMaximumSize(new Dimension(Short.MAX_VALUE, 24));
        JLabel itemNotesLabel = new JLabel("Notes:", SwingConstants.TRAILING);
        itemNotesLabel.setPreferredSize(new Dimension(50, 50));
        itemNotesLabel.setLabelFor(itemStatusText);
        itemStatusText = new JTextArea("History for", 5, 20);
        itemStatusText.setLineWrap(true);
        itemStatusText.setWrapStyleWord(true);
        itemStatusText.setEditable(false);
        JPanel itemControlsPanel = createFilterAndNotesPanel(
            itemFilterTextLabel,
            itemFilterText,
            itemNotesLabel,
            itemStatusText);

        // Only show items for the first company on startup
        List<Item> initialItems = companies.isEmpty()
                ? java.util.Collections.emptyList()
                : new java.util.ArrayList<>(companies.get(0).getItems());
        itemModelRefreshRef[0] = new ItemModel(initialItems, 0);
        itemNameSorter = new TableRowSorter<ItemModel>(itemModelRefreshRef[0]);
        itemTable = new JTable(itemModelRefreshRef[0]) {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return FALSE; // Disallow the editing of any cell
            }
        };
        itemTable.setRowSorter(itemNameSorter);
        itemTable.setPreferredScrollableViewportSize(new Dimension(500, 200));
        itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemTable.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        if (event.getValueIsAdjusting()) {
                            return;
                        }
                        int viewRow = itemTable.getSelectedRow();
                        if (viewRow < 0) {
                            itemStatusText.setText("");
                            if (itemSelectionListener != null) {
                                itemSelectionListener.accept(null);
                            }
                            return;
                        }
                        int modelRow = itemTable.convertRowIndexToModel(viewRow);
                        Item selectedItem = itemModelRefreshRef[0].getItemAt(modelRow);
                        if (selectedItem == null) {
                            itemStatusText.setText("");
                            return;
                        }
                        String notes = (selectedItem.getNotes() != null) ? selectedItem.getNotes() : "";
                        if (notes.trim().isEmpty()) {
                            itemStatusText.setText("No notes found.");
                        } else {
                            itemStatusText.setText(notes);
                        }
                        if (itemSelectionListener != null) {
                            itemSelectionListener.accept(selectedItem);
                        }
                    }
                });
        // Set up filter and itemStatusText listeners
        itemFilterText.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        filterItemsTable();
                    }

                    public void insertUpdate(DocumentEvent e) {
                        filterItemsTable();
                    }

                    public void removeUpdate(DocumentEvent e) {
                        filterItemsTable();
                    }
                });
        itemFilterTextLabel.setLabelFor(itemFilterText);
        JScrollPane itemTableScrollPane = new JScrollPane(itemTable);

        JPanel companyPanel = new JPanel(new java.awt.BorderLayout());
        JSplitPane companySplitPane = buildHorizontalSplit(
            companyTableScrollPane,
            companyControlsPanel,
            0.65);
        companyPanel.add(companySplitPane, java.awt.BorderLayout.CENTER);

        JPanel itemPanel = new JPanel(new java.awt.BorderLayout());
        JSplitPane itemSplitPane = buildHorizontalSplit(
            itemTableScrollPane,
            itemControlsPanel,
            0.7);
        itemPanel.add(itemSplitPane, java.awt.BorderLayout.CENTER);

        javax.swing.JSplitPane splitPane = new javax.swing.JSplitPane(
            javax.swing.JSplitPane.VERTICAL_SPLIT,
            companyPanel,
            itemPanel);
        splitPane.setResizeWeight(0.33);
        splitPane.setDividerLocation(0.33);
        splitPane.setContinuousLayout(true);
        splitPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        splitPane.setDividerSize(6);

        add(splitPane, java.awt.BorderLayout.CENTER);
    }

    private JPanel createFilterAndNotesPanel(JLabel filterLabel, JTextField filterField,
            JLabel notesLabel, JTextArea notesArea) {
        JPanel panel = new JPanel(new java.awt.GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 12, 0, 0));
        panel.setPreferredSize(new Dimension(320, 10));

        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(0, 0, 8, 0);
        gbc.anchor = java.awt.GridBagConstraints.NORTHWEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(filterLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panel.add(filterField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.fill = java.awt.GridBagConstraints.NONE;
        gbc.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panel.add(notesLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = java.awt.GridBagConstraints.BOTH;
        JScrollPane notesScrollPane = new JScrollPane(notesArea);
        notesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        notesScrollPane.setPreferredSize(new Dimension(260, 160));
        panel.add(notesScrollPane, gbc);

        return panel;
    }

    private JSplitPane buildHorizontalSplit(JComponent leftComponent, JComponent rightComponent, double resizeWeight) {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftComponent, rightComponent);
        split.setResizeWeight(resizeWeight);
        split.setContinuousLayout(true);
        split.setDividerSize(6);
        split.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        return split;
    }

    private void refreshItemsForCompany(Company company) {
        List<Item> filteredItems = (company != null && company.getItems() != null)
                ? new java.util.ArrayList<>(company.getItems())
                : java.util.Collections.emptyList();
        itemModelRefreshRef[0].updateModel(filteredItems);
        itemTable.revalidate();
        itemTable.repaint();
        if (itemTable.getRowCount() > 0) {
            itemTable.setRowSelectionInterval(0, 0);
        } else if (itemSelectionListener != null) {
            itemSelectionListener.accept(null);
        }
    }

    public void setCompanySelectionListener(Consumer<Company> listener) {
        this.companySelectionListener = listener;
    }

    public void setItemSelectionListener(Consumer<Item> listener) {
        this.itemSelectionListener = listener;
    }

    public void initializeSelection() {
        if (companyTable.getRowCount() > 0) {
            companyTable.setRowSelectionInterval(0, 0);
        } else if (itemSelectionListener != null) {
            itemSelectionListener.accept(null);
        }
    }

    /**
     * Update the row filter regular expression from the expression in
     * the text box.
     */
    private void filterCompaniesTable() {
        RowFilter<CompanyModel, Object> rf = null;
            String filterText = companyFilterText.getText();
            if (filterText == null || filterText.isEmpty()) {
                companyNameSorter.setRowFilter(null);
                return;
            }
            try {
                rf = RowFilter.regexFilter("(?i)" + filterText);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        companyNameSorter.setRowFilter(rf);
    }

    private void filterItemsTable() {
        RowFilter<ItemModel, Object> rf = null;
            String filterText = itemFilterText.getText();
            if (filterText == null || filterText.isEmpty()) {
                itemNameSorter.setRowFilter(null);
                return;
            }
            try {
                rf = RowFilter.regexFilter("(?i)" + filterText);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        itemNameSorter.setRowFilter(rf);
    }

    class CompanyModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        private String[] columnNames = {
                "Company Name"
        };

        /*
         * private String[] columnNames = {"Company Id",
         * // "Company Name",
         * };
         */
        private List<Company> company;
        private Object[][] data;

        public CompanyModel(List<Company> company) {
            this.company = company;
            int listSize = company.size();
            data = new Object[listSize][1];
            for (int i = 0; i < listSize; i++) {
                // data[i][0]=(Object)company.get(i).getCompanyId();
                data[i][0] = (Object) company.get(i).getCompanyName();
            }

        };

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        public Company getCompanyAtRow(int row) {
            if (row < 0 || row >= company.size()) {
                return null;
            }
            return company.get(row);
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell. If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            if (getRowCount() == 0) {
                return Object.class;
            }
            Object value = getValueAt(0, c);
            return (value == null) ? Object.class : value.getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            // Note that the data/cell address is constant,
            // no matter where the cell appears onscreen.
            if (col < 2) {
                return false;
            } else {
                return true;
            }
        }

        public void updateModel() {

        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                        + " to " + value
                        + " (an instance of "
                        + value.getClass() + ")");
            }

            data[row][col] = value;
            fireTableCellUpdated(row, col);

            if (DEBUG) {
                System.out.println("New value of data:");
                printDebugData();
            }
        }

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i = 0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j = 0; j < numCols; j++) {
                    System.out.print("  " + data[i][j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }

    }

    public static class ItemModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        private String[] columnNames = {
                "Service Name",
                "Service ID"
        };
        @SuppressWarnings("deprecation")

        private List<Item> items;
        private Object[][] data;

        public ItemModel(List<Item> items, int index) {
            this.items = items;
            int listSize = items.size();
            data = new Object[listSize][2];
            for (int i = 0; i < listSize; i++) {
                data[i][0] = items.get(i).getItemName();
                data[i][1] = items.get(i).getItemId();
            }
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        public Class getColumnClass(int c) {
            if (getRowCount() == 0) {
                return Object.class;
            }
            Object value = getValueAt(0, c);
            return (value == null) ? Object.class : value.getClass();
        }

        public boolean isCellEditable(int row, int col) {
            // Only allow editing for the Total(s) column, if needed
            return col == 1;
        }

        public Item getItemAt(int row) {
            if (row < 0 || row >= items.size()) {
                return null;
            }
            return items.get(row);
        }

        public void updateModel(List<Item> items) {
            this.items = (items != null) ? new java.util.ArrayList<>(items) : java.util.Collections.emptyList();
            int listSize = this.items.size();
            data = new Object[listSize][2];
            for (int i = 0; i < listSize; i++) {
                data[i][0] = this.items.get(i).getItemName();
                data[i][1] = this.items.get(i).getQuantity();
            }
            fireTableDataChanged();
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }
    }

}
