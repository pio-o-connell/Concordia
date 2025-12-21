
package WareHouse;


//import TableWindow1;

//import Mainframe;

//import TableFilterDemo1;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import WareHouse.domain.Company;
import WareHouse.domain.Item;
import WareHouse.domain.User;
import WareHouse.domain.history;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*---------------------------------------------------------------------------------------
/*
 * The 'Mainframe' class gets on with the main business of rendering the frame.
 * It invokes 3 other classes DetailPanel for the new data to be entered.
 * and TableWindow1 and TableWindow2 for mirroring the data in the main classes
 * 
 ---------------------------------------------------------------------------------------*/


import WareHouse.controller.InventoryController;

public class Mainframe extends JFrame {
		// Call this method after company selection changes
		public void updateCompanySelection(int newCompanyIndex) {
			companyIndex = newCompanyIndex;
			// All data updates should be handled via controller/service and detailPanel public methods
			// UI updates should use detailPanel.setFields or similar
		}
	private static final long serialVersionUID = 1L;

	private int companyIndex=0, itemIndex=0, historyIndex=0;
	private TableWindow1 detailTable1; // contains the company table and items table
	private TableWindow2 detailTable2; // contains the history table
	private DetailsPanel detailPanel; // contains the table for new record entry
	private int historyRecordNo=30;
	
	/*-------------------------------------------------------
	 * These could be implemented using an abstract class
	 --------------------------------------------------------*/

	
		
		private final InventoryController controller;

		public Mainframe(String title, InventoryController controller) throws Exception{
			super(title);
			this.controller = controller;
			// Set initial frame size for proper split
			setSize(1200, 700); // You can adjust this size as needed
			setMinimumSize(new java.awt.Dimension(1200, 700));
			// Database connection and logic should be handled by repository/controller layer
			// Example: controller.initialize();

			
			
			
			//Necessary for initial table window display
			final java.util.List<Company> tableCompanyPointer = controller.getAllCompanies();
			final ArrayList<Company> companyList = new ArrayList<>(tableCompanyPointer);
			final ArrayList<Item> itemList = companyList.isEmpty() ? new ArrayList<>() : new ArrayList<>(companyList.get(0).getItems());
			final ArrayList<history> historyList = itemList.isEmpty() ? new ArrayList<>() : new ArrayList<>(itemList.get(0).getHistory());
			
			
			

			
			
			
			// display the table panels for company and items panels
			final TableWindow1 detailTable1 = new TableWindow1(itemList, companyList, historyList, controller);
			detailTable1.setOpaque(true); //content panes must be opaque
			
			// display the history panels
			final TableWindow2 detailTable2 = new TableWindow2(itemList, companyList, historyList, controller);
			detailTable2.setOpaque(true); //content panes must be opaque
			
			
			setLayout(new BorderLayout());

			
			final JTextArea textArea = new JTextArea();
			
			
			// display the 'new inventory to add' panel and add event listener for 'Add'
			detailPanel = new DetailsPanel(controller);
			detailPanel.addDetailListener(new DetailListener(){
				public void detailEventOccured(DetailEvent event){
					String text = event.getText();
					textArea.append(text);
				}});
			
			       detailTable1.setOpaque(true);
			       detailTable1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLUE, 3));
			       detailTable1.setPreferredSize(new java.awt.Dimension(300, 200));
			       detailTable1.add(new javax.swing.JLabel("TableWindow1 (WEST bottom)"));

			       detailTable2.setOpaque(true);
			       detailTable2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED, 3));
			       detailTable2.setPreferredSize(new java.awt.Dimension(300, 200));
			       detailTable2.add(new javax.swing.JLabel("TableWindow2 (EAST)"));

				detailPanel.setOpaque(true);
				detailPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLUE, 3));
				detailPanel.setBackground(new java.awt.Color(240, 240, 255));
				detailPanel.setPreferredSize(new java.awt.Dimension(300, 200));
				detailPanel.add(new javax.swing.JLabel("DetailsPanel (WEST top)"));
			       detailPanel.addDetailListener(new DetailListener() {
				       public void detailEventOccured(DetailEvent event) {
					       String text = event.getText();
					       textArea.append(text);
				       }
			       });

			   // --- Restore inter-panel selection logic ---
			   // On launch, select first company, first item, first history, and update DetailsPanel
			   if (!companyList.isEmpty()) {
				   detailTable1.table.setRowSelectionInterval(0, 0);
				   detailTable1.table2.setRowSelectionInterval(0, 0);
				   detailTable2.table3.setRowSelectionInterval(0, 0);
				   Company firstCompany = companyList.get(0);
				   Item firstItem = firstCompany.getItems().isEmpty() ? null : firstCompany.getItems().get(0);
				   history firstHistory = (firstItem != null && !firstItem.getHistory().isEmpty()) ? firstItem.getHistory().get(0) : null;
				   detailPanel.setCurrentCompanyField(firstCompany.getCompanyName());
				   if (firstHistory != null) {
					   detailPanel.setFields(firstItem.getItemName(), firstHistory.getLocation(), firstHistory.getSupplier(), firstHistory.getDeliveryDate(), String.valueOf(firstHistory.getAmount()));
				   } else if (firstItem != null) {
					   detailPanel.setFields(firstItem.getItemName(), "", "", "", String.valueOf(firstItem.getQuantity()));
				   }
			   }

			   // Add selection listeners to keep panels in sync
			   detailTable1.table.getSelectionModel().addListSelectionListener(e -> {
				   if (!e.getValueIsAdjusting()) {
					   int companyIdx = detailTable1.table.getSelectedRow();
					   if (companyIdx >= 0 && companyIdx < companyList.size()) {
						   Company selectedCompany = companyList.get(companyIdx);
						   detailPanel.setCurrentCompanyField(selectedCompany.getCompanyName());
						   // Update items table for selected company
						   ArrayList<Item> filteredItems = new ArrayList<>(selectedCompany.getItems());
						   detailTable1.model2[0] = new TableWindow1.MyTableModel2(filteredItems, 0);
						   detailTable1.table2.setModel(detailTable1.model2[0]);
						   detailTable1.sorter2.setModel(detailTable1.model2[0]);
						   if (!filteredItems.isEmpty()) {
							   detailTable1.table2.setRowSelectionInterval(0, 0);
						   }
					   }
				   }
			   });

			   detailTable1.table2.getSelectionModel().addListSelectionListener(e -> {
				   if (!e.getValueIsAdjusting()) {
					   int companyIdx = detailTable1.table.getSelectedRow();
					   int itemIdx = detailTable1.table2.getSelectedRow();
					   if (companyIdx >= 0 && companyIdx < companyList.size()) {
						   Company selectedCompany = companyList.get(companyIdx);
						   ArrayList<Item> filteredItems = new ArrayList<>(selectedCompany.getItems());
						   if (itemIdx >= 0 && itemIdx < filteredItems.size()) {
							   Item selectedItem = filteredItems.get(itemIdx);
							   // Update history table for selected item
							   ArrayList<history> itemHistory = new ArrayList<>(selectedItem.getHistory());
							   detailTable2.model = new TableWindow2.MyTableModel(itemHistory, 0);
							   detailTable2.table3.setModel(detailTable2.model);
							   detailTable2.sorter3.setModel(detailTable2.model);
							   if (!itemHistory.isEmpty()) {
								   detailTable2.table3.setRowSelectionInterval(0, 0);
								   history firstHistory = itemHistory.get(0);
								   detailPanel.setFields(selectedItem.getItemName(), firstHistory.getLocation(), firstHistory.getSupplier(), firstHistory.getDeliveryDate(), String.valueOf(firstHistory.getAmount()));
							   } else {
								   detailPanel.setFields(selectedItem.getItemName(), "", "", "", String.valueOf(selectedItem.getQuantity()));
							   }
						   }
					   }
				   }
			   });

			   detailTable2.table3.getSelectionModel().addListSelectionListener(e -> {
				   if (!e.getValueIsAdjusting()) {
					   int companyIdx = detailTable1.table.getSelectedRow();
					   int itemIdx = detailTable1.table2.getSelectedRow();
					   int historyIdx = detailTable2.table3.getSelectedRow();
					   if (companyIdx >= 0 && companyIdx < companyList.size()) {
						   Company selectedCompany = companyList.get(companyIdx);
						   ArrayList<Item> filteredItems = new ArrayList<>(selectedCompany.getItems());
						   if (itemIdx >= 0 && itemIdx < filteredItems.size()) {
							   Item selectedItem = filteredItems.get(itemIdx);
							   ArrayList<history> itemHistory = new ArrayList<>(selectedItem.getHistory());
							   if (historyIdx >= 0 && historyIdx < itemHistory.size()) {
								   history selectedHistory = itemHistory.get(historyIdx);
								   detailPanel.setFields(selectedItem.getItemName(), selectedHistory.getLocation(), selectedHistory.getSupplier(), selectedHistory.getDeliveryDate(), String.valueOf(selectedHistory.getAmount()));
							   }
						   }
					   }
				   }
			   });

			   // Create scroll pane for DetailsPanel (WEST)
			   javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(detailPanel);
			   scrollPane.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.GREEN, 3));
			   scrollPane.setPreferredSize(new java.awt.Dimension(800, 700)); // 2/3 of 1200 width

			   // Create a panel to stack company and item tables vertically (EAST)
			   detailTable1.setPreferredSize(new java.awt.Dimension(400, 350));
			   detailTable2.setPreferredSize(new java.awt.Dimension(Integer.MAX_VALUE, 350));
			   detailTable2.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 350));
			   detailTable2.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
			   JPanel eastPanel = new JPanel();
			   eastPanel.setLayout(new javax.swing.BoxLayout(eastPanel, javax.swing.BoxLayout.Y_AXIS));
			   eastPanel.add(detailTable1);

			   // Use JSplitPane to split WEST (DetailsPanel) and EAST (tables)
			   javax.swing.JSplitPane splitPane = new javax.swing.JSplitPane(javax.swing.JSplitPane.HORIZONTAL_SPLIT, scrollPane, eastPanel);
			   splitPane.setDividerLocation(800); // 2/3 of 1200
			   splitPane.setResizeWeight(0.67); // WEST gets 2/3
			   splitPane.setContinuousLayout(true);

			   Container c = getContentPane();
			   c.setLayout(new BorderLayout());
			   c.add(splitPane, BorderLayout.CENTER);
			   c.add(detailTable2, BorderLayout.SOUTH);
		}
}







