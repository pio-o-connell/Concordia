package concordia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JFrame;
import concordia.controller.InventoryController;
import concordia.domain.Company;
import concordia.domain.Item;
import concordia.domain.history;

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

        CompanyItemTablePanel companyItemPanel = new CompanyItemTablePanel(companies);
        TransactionHistoryPanel transactionHistoryPanel = new TransactionHistoryPanel(Collections.emptyList(), companies, Collections.emptyList());

        final Company[] currentCompany = new Company[1];
        final Item[] currentItem = new Item[1];

        transactionHistoryPanel.setHistorySelectionListener(historyEntry -> {
            Company activeCompany = currentCompany[0];
            Item activeItem = currentItem[0];
            adminPanel.displaySelection(activeCompany, activeItem, historyEntry);
        });

        companyItemPanel.setCompanySelectionListener(company -> {
            currentCompany[0] = company;
            adminPanel.displaySelection(company, null, null);
        });
        companyItemPanel.setItemSelectionListener(item -> {
            Company activeCompany = currentCompany[0];
            currentItem[0] = item;
            List<history> historyList = (item != null && item.getHistory() != null)
                    ? new ArrayList<>(item.getHistory())
                    : Collections.emptyList();
            transactionHistoryPanel.updateHistory(historyList);
            if (historyList.isEmpty()) {
                adminPanel.displaySelection(activeCompany, item, null);
            }
        });

        javax.swing.JScrollPane adminScrollPane = new javax.swing.JScrollPane(adminPanel);
        adminScrollPane.setPreferredSize(new java.awt.Dimension(560, 680));
        adminScrollPane.setMinimumSize(new java.awt.Dimension(400, 680));
        adminScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        javax.swing.JScrollPane companyScrollPane = new javax.swing.JScrollPane(companyItemPanel);
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

        companyItemPanel.initializeSelection();
        transactionHistoryPanel.selectFirstRow();
    }
}

