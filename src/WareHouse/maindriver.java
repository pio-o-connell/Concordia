package WareHouse;
import javax.swing.JFrame;
import java.sql.Connection;
import java.sql.DriverManager;

public class maindriver {
	// All data and initialization should be handled by the controller layer

	public static void main(String[] args) throws Exception {
		// Example: controller.initialize();
		// Now launch the main window
		javax.swing.SwingUtilities.invokeLater(() -> {
			JFrame frame;
			try {
				String url = "jdbc:mysql://127.0.0.1:3306/warehouse?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
				String user = "root";
				String password = "ROOT";
				Connection connection = DriverManager.getConnection(url, user, password);
				WareHouse.repository.CompanyRepository companyRepo = new WareHouse.repository.CompanyRepository(connection);
				WareHouse.repository.ItemRepository itemRepo = new WareHouse.repository.ItemRepository(connection);
				WareHouse.repository.HistoryRepository historyRepo = new WareHouse.repository.HistoryRepository(connection);
				WareHouse.service.InventoryService service = new WareHouse.service.InventoryService(companyRepo, itemRepo, historyRepo);
				WareHouse.controller.InventoryController controller = new WareHouse.controller.InventoryController(service);
				frame = new Mainframe("Warehouse Inventory System", controller);
				frame.setSize(800, 100);
				frame.setResizable(true);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}