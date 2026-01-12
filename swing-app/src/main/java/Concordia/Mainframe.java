package concordia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JFrame;
import concordia.controller.InventoryController;
import concordia.domain.Company;
import concordia.domain.ServiceType;
import concordia.domain.ServicePricing;

public class Mainframe extends JFrame {
    public Mainframe(String title, InventoryController controller) {
        super(title);
        setSize(1200, 700);
        setMinimumSize(new java.awt.Dimension(1200, 700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new java.awt.BorderLayout());

        AdminPanel adminPanel = new AdminPanel(controller);
        List<Company> companies = new ArrayList<>(controller.getAllCompanies());

        CompanyServicesTablePanel companyServicesPanel = new CompanyServicesTablePanel(companies);
        TransactionHistoryPanel transactionHistoryPanel = new TransactionHistoryPanel(companies, Collections.emptyList());

        final Company[] currentCompany = new Company[1];
        final ServiceType[] currentServiceType = new ServiceType[1];

        transactionHistoryPanel.setHistorySelectionListener(historyEntry -> {
            Company activeCompany = currentCompany[0];
            ServiceType activeServiceType = currentServiceType[0];
            adminPanel.displaySelection(activeCompany, activeServiceType, historyEntry);
        });

        companyServicesPanel.setCompanySelectionListener(company -> {
            currentCompany[0] = company;
            adminPanel.displaySelection(company, null, null);
        });
        companyServicesPanel.setServiceTypeSelectionListener(serviceType -> {
            Company activeCompany = currentCompany[0];
            currentServiceType[0] = serviceType;
            // You may want to update transactionHistoryPanel with serviceType-related history here
            // transactionHistoryPanel.updateHistory(...);
            adminPanel.displaySelection(activeCompany, serviceType, null);
        });

        javax.swing.JScrollPane adminScrollPane = new javax.swing.JScrollPane(adminPanel);
        adminScrollPane.setPreferredSize(new java.awt.Dimension(560, 680));
        adminScrollPane.setMinimumSize(new java.awt.Dimension(400, 680));
        adminScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        javax.swing.JScrollPane companyScrollPane = new javax.swing.JScrollPane(companyServicesPanel);
        companyScrollPane.setPreferredSize(new java.awt.Dimension(520, 680));
        companyScrollPane.setMinimumSize(new java.awt.Dimension(360, 680));
        companyScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        javax.swing.JSplitPane splitPane = new javax.swing.JSplitPane(
            javax.swing.JSplitPane.HORIZONTAL_SPLIT,
            adminScrollPane,
            companyScrollPane);
        splitPane.setDividerLocation(580);
        splitPane.setResizeWeight(0.5);
        splitPane.setContinuousLayout(true);
        splitPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        add(splitPane, java.awt.BorderLayout.CENTER);
        add(transactionHistoryPanel, java.awt.BorderLayout.SOUTH);

        transactionHistoryPanel.selectFirstRow();
    }
}

