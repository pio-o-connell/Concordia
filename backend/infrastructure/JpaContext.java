package backend.infrastructure;

import backend.repository.CompanyRepository;
import backend.repository.ServiceTypeRepository;
import backend.repository.ServicePricingRepository;
import backend.repository.ServiceRepository;
import backend.repository.TransactionHistoryRepository;
import backend.repository.UserRepository;
import backend.service.InventoryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

/**
 * Lightweight holder that wires an {@link EntityManager} together with
 * the repositories and services that depend on it. Open a context per
 * request and close it when finished to avoid sharing entity managers
 * across servlet threads.
 */
public final class JpaContext implements AutoCloseable {
    private final EntityManager entityManager;
    private final CompanyRepository companyRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final ServicePricingRepository servicePricingRepository;
    private final ServiceRepository serviceRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;
    private final UserRepository userRepository;
    private final InventoryService inventoryService;

    private JpaContext(EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManagerFactory.createEntityManager();
        this.companyRepository = new CompanyRepository(entityManager);
        this.serviceTypeRepository = new ServiceTypeRepository(entityManager);
        this.servicePricingRepository = new ServicePricingRepository(entityManager);
        this.serviceRepository = new ServiceRepository(entityManager);
        this.transactionHistoryRepository = new TransactionHistoryRepository(entityManager);
        this.userRepository = new UserRepository(entityManager);
        this.inventoryService = new InventoryService(companyRepository, serviceTypeRepository, servicePricingRepository, transactionHistoryRepository, serviceRepository);
    }

    public ServiceRepository serviceRepository() {
        return serviceRepository;
    }

    public static JpaContext open(EntityManagerFactory entityManagerFactory) {
        return new JpaContext(entityManagerFactory);
    }

    public InventoryService inventoryService() {
        return inventoryService;
    }

    public UserRepository userRepository() {
        return userRepository;
    }

    public CompanyRepository companyRepository() {
        return companyRepository;
    }

    // Removed itemRepository() as ItemRepository is obsolete.


    @Override
    public void close() {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
