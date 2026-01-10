package concordia;
import java.util.List;
import java.util.function.Consumer;

/*------------------------------------------------------------------------------------------------------------------*/
//TableWindow2 Class retrieves the data structure (from memory) and renders the History tables.
//CompanyItemTablePanel also has 1 anonymous class containing  action listeners,called for when user selects an table item
//from History tables respectively.
// History tables is non editable - risk of data inconsistency too high
//Selecting a row in the History table has the effect of updating the data entry controls (located in the AdminPanel Class).
//
// Most Companies purchase manufacturers items from the same suppliers usually this will ease the data entry procedure
/*-------------------------------------------------------------------------------------------------------------------*/

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.Dimension;
import concordia.domain.Company;
import concordia.domain.Item;
import concordia.domain.history;


public class TransactionHistoryPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final JTable transactionHistoryTable;
    private final JTextField historyTransactionFilterText;
    private final JTextArea historyTransactionStatusText;
    private final TableRowSorter<MyTableModel> historyTransactionNameSorter;
    private final MyTableModel transactionHistoryTableModel;
    private Consumer<history> historySelectionListener;

        public TransactionHistoryPanel(List<Item> items, List<Company> companies, List<history> history) {
        super(new java.awt.BorderLayout());
        setOpaque(true);
        setBackground(new java.awt.Color(255, 240, 240));
        setMinimumSize(new java.awt.Dimension(300, 220));
        setPreferredSize(new java.awt.Dimension(900, 280));

        // Use the full history list provided to this panel so all
        // records are displayed in the table (not just the first
        // company's first item's history).
        java.util.List<history> historyList = (history != null)
            ? new java.util.ArrayList<>(history)
            : new java.util.ArrayList<>();
        transactionHistoryTableModel = new MyTableModel(historyList, 0);
        historyTransactionNameSorter = new TableRowSorter<MyTableModel>(transactionHistoryTableModel);
        transactionHistoryTable = new JTable(transactionHistoryTableModel) {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; // Disallow the editing of any cell
            }
        };
        java.awt.Font tableFont = new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 13);
        transactionHistoryTable.setFont(tableFont);
        transactionHistoryTable.setRowHeight(24);
        transactionHistoryTable.setSelectionBackground(new java.awt.Color(255, 220, 220));
        transactionHistoryTable.setSelectionForeground(java.awt.Color.BLACK);
        transactionHistoryTable.setFillsViewportHeight(true);
        transactionHistoryTable.setRowSorter(historyTransactionNameSorter);
        transactionHistoryTable.setPreferredScrollableViewportSize(new Dimension(500, 200));
        transactionHistoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        transactionHistoryTable.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        if (event.getValueIsAdjusting()) {
                            return;
                        }
                        int viewRow = transactionHistoryTable.getSelectedRow();
                        if (viewRow < 0) {
                            historyTransactionStatusText.setText("");
                            if (historySelectionListener != null) {
                                historySelectionListener.accept(null);
                            }
                            return;
                        }
                        int modelRow = transactionHistoryTable.convertRowIndexToModel(viewRow);
                        history selected = transactionHistoryTableModel.getHistoryAtRow(modelRow);
                        String notes = (selected != null) ? selected.getNotes() : null;
                        if (notes == null || notes.trim().isEmpty()) {
                            historyTransactionStatusText.setText("No notes found.");
                        } else {
                            historyTransactionStatusText.setText(notes);
                        }
                        if (historySelectionListener != null) {
                            historySelectionListener.accept(selected);
                        }
                    }
                });

        JScrollPane transactionHistoryTableScrollPane = new JScrollPane(transactionHistoryTable);
        transactionHistoryTableScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        add(transactionHistoryTableScrollPane, java.awt.BorderLayout.CENTER);

        JPanel transactionHistoryFormPanel = new JPanel(new java.awt.GridBagLayout());
        transactionHistoryFormPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JLabel historyTransactionFilterTextLabel = new JLabel("Filter Text:", SwingConstants.TRAILING);
        historyTransactionFilterTextLabel.setPreferredSize(new Dimension(10, 10));
        historyTransactionFilterText = new JTextField();
        historyTransactionFilterText.setPreferredSize(new Dimension(Short.MAX_VALUE, 24));
        historyTransactionFilterText.setMaximumSize(new Dimension(Short.MAX_VALUE, 24));
        // Whenever filterText changes, invoke newFilter.
        historyTransactionFilterText.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        newFilter();
                    }

                    public void insertUpdate(DocumentEvent e) {
                        newFilter();
                    }

                    public void removeUpdate(DocumentEvent e) {
                        newFilter();
                    }
                });
    historyTransactionFilterTextLabel.setLabelFor(historyTransactionFilterText);
    historyTransactionStatusText = new JTextArea("History for", 5, 20);
    JLabel historyTransactionNotesLabel = new JLabel("Notes:", SwingConstants.TRAILING);
    historyTransactionNotesLabel.setPreferredSize(new Dimension(50, 50));
    historyTransactionNotesLabel.setLabelFor(historyTransactionStatusText);
    historyTransactionStatusText.setLineWrap(true);
    historyTransactionStatusText.setWrapStyleWord(true);
    historyTransactionStatusText.setEditable(false);
    JScrollPane scrollPane12 = new JScrollPane(historyTransactionStatusText);
    scrollPane12.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
    gbc.insets = new java.awt.Insets(0, 0, 8, 0);
    gbc.anchor = java.awt.GridBagConstraints.NORTHWEST;

    gbc.gridx = 0;
    gbc.gridy = 0;
    transactionHistoryFormPanel.add(historyTransactionFilterTextLabel, gbc);

    gbc.gridx = 1;
    gbc.weightx = 1.0;
    gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
    transactionHistoryFormPanel.add(historyTransactionFilterText, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 0;
    gbc.fill = java.awt.GridBagConstraints.NONE;
    gbc.anchor = java.awt.GridBagConstraints.NORTHWEST;
    transactionHistoryFormPanel.add(historyTransactionNotesLabel, gbc);

    gbc.gridx = 1;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.fill = java.awt.GridBagConstraints.BOTH;
    transactionHistoryFormPanel.add(scrollPane12, gbc);
        add(transactionHistoryFormPanel, java.awt.BorderLayout.SOUTH);
    }

    /**
     * Update the row filter regular expression from the expression in
     * the text box.
     */
    private void newFilter() {
        RowFilter<MyTableModel, Object> rf = null;
        String filterText = historyTransactionFilterText.getText();
        if (filterText == null || filterText.isEmpty()) {
            historyTransactionNameSorter.setRowFilter(null);
            return;
        }
        try {
            rf = RowFilter.regexFilter("(?i)" + filterText);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        historyTransactionNameSorter.setRowFilter(rf);
    }

    public void updateHistory(List<history> newHistory) {
        updateHistory(newHistory, true);
    }

    public void updateHistory(List<history> newHistory, boolean autoSelectFirst) {
        transactionHistoryTableModel.updateHistory(newHistory);
        historyTransactionStatusText.setText("History for");
        if (autoSelectFirst && transactionHistoryTable.getRowCount() > 0) {
            transactionHistoryTable.setRowSelectionInterval(0, 0);
        } else {
            transactionHistoryTable.clearSelection();
            if (historySelectionListener != null) {
                historySelectionListener.accept(null);
            }
        }
    }

    public void selectFirstRow() {
        if (transactionHistoryTable.getRowCount() > 0) {
            transactionHistoryTable.setRowSelectionInterval(0, 0);
        }
    }

    public void setHistorySelectionListener(Consumer<history> listener) {
        this.historySelectionListener = listener;
    }

    public static class MyTableModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        private String[] columnNames = {
            "Delivery Date",
            "Service",
            "Service Size",
            "Location",
            "Amount",
            "Provider" };
        private List<history> history;
        private Object[][] data;

        public MyTableModel(List<history> history, int index1) {
            updateHistory(history);
        }

        public void updateHistory(List<history> history) {
            this.history = (history != null) ? new java.util.ArrayList<>(history) : java.util.Collections.emptyList();
            int listSize = this.history.size();
            data = new Object[listSize][6];
            for (int i = 0; i < listSize; i++) {
                data[i][0] = this.history.get(i).getDeliveryDate();
 //               data[i][1] = this.history.get(i).getService();
  //              data[i][2] = this.history.get(i).getServiceSize();
                data[i][3] = this.history.get(i).getLocation();
                data[i][4] = this.history.get(i).getAmount();
                data[i][5] = this.history.get(i).getProvider();
            }
            fireTableDataChanged();
        }

        // Helper to get the History entity associated with a given table row.
        public history getHistoryAtRow(int row) {
            if (row < 0 || row >= history.size()) {
                return null;
            }
            return history.get(row);
        }

        public Object[][] convertTo2D() {
            int listSize = history.size();
            final Object[][] data2 = new Object[listSize][6];
            for (int i = 0; i < listSize; i++) {
                data2[i][0] = (Object) history.get(i).getDeliveryDate();
   //             data2[i][1] = (Object) history.get(i).getService();
    //            data2[i][2] = (Object) history.get(i).getServiceSize();
                data2[i][3] = (Object) history.get(i).getLocation();
                data2[i][4] = (Object) history.get(i).getAmount();
                data2[i][5] = (Object) history.get(i).getProvider();
            }
            return data2;
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

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            if (row < 0 || row >= data.length) {
                return;
            }
            if (data[row] == null || col < 0 || col >= data[row].length) {
                return;
            }
            data[row][col] = value;
            fireTableCellUpdated(row, col);
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

        public void set() {

        }
    }
}
// ...existing code...
