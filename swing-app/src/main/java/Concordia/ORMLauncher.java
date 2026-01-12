package concordia;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import concordia.repository.CompanyRepository;
import concordia.repository.ServiceRepository;
import concordia.repository.TransactionHistoryRepository;
import concordia.service.InventoryService;
import concordia.controller.InventoryController;
import concordia.domain.Company;
import concordia.domain.ServiceType;
import concordia.domain.ServicePricing;
import concordia.domain.TransactionHistory;
import concordia.domain.User;
import javax.swing.SwingUtilities;

public class ORMLauncher {
    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("concordiaPU");
            em = emf.createEntityManager();
            // Wire up repositories, service, controller
            CompanyRepository companyRepo = new CompanyRepository(em);
            ServiceRepository serviceRepo = new ServiceRepository(em);
            TransactionHistoryRepository historyRepo = new TransactionHistoryRepository(em);
            InventoryService service = new InventoryService(companyRepo, serviceRepo, historyRepo);
            InventoryController controller = new InventoryController(service);

            // Fetch data for panels
            java.util.List<Company> companies = controller.getAllCompanies();
            java.util.List<ServiceType> serviceTypes = controller.getAllServiceTypes();
            java.util.List<ServicePricing> servicePricings = controller.getAllServicePricings();
            java.util.List<TransactionHistory> transactionHistories = controller.getAllTransactionHistory();

            // Launch the main UI (Mainframe) with the controller
            SwingUtilities.invokeLater(() -> {
                Mainframe frame = new Mainframe("Concordia ORM App", controller);
                frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Do not close em/emf here, keep open for app lifetime
        }
    }
}
