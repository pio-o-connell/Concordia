
package concordia.infrastructure;
import concordia.annotations.Component;
import concordia.annotations.Configuration;

import concordia.service.InventoryService;
import concordia.controller.InventoryController;
import concordia.Mainframe;
// import java.sql.Connection;
// import java.sql.DriverManager;

@Component
@Configuration
public class ApplicationBootstrap {
    public static void main(String[] args) {
        try {
            jakarta.persistence.EntityManagerFactory emf = jakarta.persistence.Persistence.createEntityManagerFactory("concordiaPU");
            jakarta.persistence.EntityManager em = emf.createEntityManager();
            concordia.repository.CompanyRepository companyRepo = new concordia.repository.CompanyRepository(em);
            concordia.repository.ServiceRepository serviceRepo = new concordia.repository.ServiceRepository(em);
            concordia.repository.TransactionHistoryRepository historyRepo = new concordia.repository.TransactionHistoryRepository(em);
            concordia.repository.ServicesRepository servicesRepo = new concordia.repository.ServicesRepository(em);
            concordia.repository.ServicePricingRepository servicePricingRepo = new concordia.repository.ServicePricingRepository(em);
            InventoryService service = new InventoryService(companyRepo, serviceRepo, historyRepo, servicesRepo, servicePricingRepo);
            InventoryController controller = new InventoryController(service);
            Mainframe frame = new Mainframe("Concordia Inventory System", controller);
            frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
