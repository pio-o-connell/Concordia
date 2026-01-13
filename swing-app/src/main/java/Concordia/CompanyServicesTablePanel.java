package concordia;

// CompanyServicesTablePanel Class retrieves the data structure and renders both the Companies and Service Types tables.
// CompanyServicesTablePanel also has 2 anonymous classes containing action listeners, called for when user selects a row
// from Company or ServiceType tables respectively.
// Selecting a row in the Company table has the effect of updating the Service Types Window.
// Both the Companies and Service Types tables are non editable - risk of data inconsistency too high

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.Dimension;
import java.util.List;
import java.util.function.Consumer;
import concordia.domain.Company;
import concordia.domain.ServiceType;
import concordia.domain.ServicePricing;

public class CompanyServicesTablePanel extends JPanel {
        public void setCompanySelectionListener(java.util.function.Consumer<Company> listener) {
            this.companySelectionListener = listener;
        }

        public void setServiceTypeSelectionListener(java.util.function.Consumer<ServiceType> listener) {
            this.serviceTypeSelectionListener = listener;
        }
    private static final long serialVersionUID = 1L;
    private static final boolean FALSE = false;
    private boolean DEBUG = false;
    public JTable companyTable;
    public JTextField companyFilterText;
    public JTextArea companyStatusText;
    public TableRowSorter<CompanyModel> companyNameSorter;

    // Table Panel 2
    public JTable serviceTypeTable;
    public TableRowSorter<ServiceTypeModel> serviceTypeNameSorter;
    public final List<Company> companies;
    public ServiceTypeModel[] serviceTypeModelRefreshRef = new ServiceTypeModel[1];
    public CompanyModel[] companyModelRefreshRef = new CompanyModel[1];
    private Consumer<Company> companySelectionListener;
    private Consumer<ServiceType> serviceTypeSelectionListener;

    // Constructor
    public CompanyServicesTablePanel(List<Company> companies) {
        super();
        setOpaque(true);
        setBackground(new java.awt.Color(240, 240, 255));
        this.companies = companies;
        setLayout(new java.awt.BorderLayout());
        companyModelRefreshRef[0] = new CompanyModel(this.companies);
        companyNameSorter = new TableRowSorter<CompanyModel>(companyModelRefreshRef[0]);
        companyTable = new JTable(companyModelRefreshRef[0]) {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        java.awt.Font servicesTableFont = new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 13);
        companyTable.setFont(servicesTableFont);
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
                        // ...existing code for handling company selection...
                    }
                }
            }
        );
        // ...existing code for serviceTypeTable and other UI setup...
    }
    // --- Model classes migrated from CompanyServicesTablePanel.java ---
    // Uses CompanyDto and ServiceTypeDto from swing-app's dto package
    public static class CompanyModel extends AbstractTableModel {
        private final String[] columnNames = {"ID", "Name", "Title"};
        private final java.util.List<concordia.domain.Company> companies;

        public CompanyModel(java.util.List<concordia.domain.Company> companies) {
            this.companies = companies;
        }

        @Override
        public int getRowCount() {
            return companies.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            concordia.domain.Company company = companies.get(rowIndex);
            switch (columnIndex) {
                case 0: return company.getCompanyId();
                case 1: return company.getCompanyName();
                case 2: return company.getCompanyTitle();
                default: return null;
            }
        }

        public concordia.domain.Company getCompanyAt(int rowIndex) {
            return companies.get(rowIndex);
        }
    }

    public static class ServiceTypeModel extends AbstractTableModel {
        private final String[] columnNames = {"ID", "Name"};
        private final java.util.List<concordia.domain.ServiceType> serviceTypes;

        public ServiceTypeModel(java.util.List<concordia.domain.ServiceType> serviceTypes) {
            this.serviceTypes = serviceTypes;
        }

        @Override
        public int getRowCount() {
            return serviceTypes.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            concordia.domain.ServiceType serviceType = serviceTypes.get(rowIndex);
            switch (columnIndex) {
                case 0: return serviceType.getServiceTypeId();
                case 1: return serviceType.getTypeName();
                default: return null;
            }
        }

        public concordia.domain.ServiceType getServiceTypeAt(int rowIndex) {
            return serviceTypes.get(rowIndex);
        }
    }
}
