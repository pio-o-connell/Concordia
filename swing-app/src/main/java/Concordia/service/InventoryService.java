
package concordia.service;

import concordia.annotations.Services;
import concordia.repository.CompanyRepository;
import concordia.repository.ServiceRepository;
import concordia.repository.TransactionHistoryRepository;
import concordia.repository.ServicesRepository;
import concordia.repository.ServicePricingRepository;
import concordia.domain.Company;
import concordia.domain.ServiceType;
import concordia.domain.ServicePricing;
import concordia.domain.TransactionHistory;
import java.util.List;

@Services
public class InventoryService {
    private final CompanyRepository companyRepo;
    private final ServiceRepository serviceRepo;
    private final TransactionHistoryRepository historyRepo;
    private final ServicesRepository servicesRepo;
    private final ServicePricingRepository servicePricingRepo;

    public InventoryService(CompanyRepository companyRepo, ServiceRepository serviceRepo, TransactionHistoryRepository historyRepo, ServicesRepository servicesRepo, ServicePricingRepository servicePricingRepo) {
        this.companyRepo = companyRepo;
        this.serviceRepo = serviceRepo;
        this.historyRepo = historyRepo;
        this.servicesRepo = servicesRepo;
        this.servicePricingRepo = servicePricingRepo;
    }

    // --- Services CRUD methods ---
    public List<concordia.domain.Services> getAllServices() {
        return servicesRepo.getAllServices();
    }

    public void addService(concordia.domain.Services service) {
        servicesRepo.insertService(service);
    }

    public void updateService(concordia.domain.Services service) {
        servicesRepo.updateService(service);
    }

    public void deleteService(int serviceId) {
        servicesRepo.deleteService(serviceId);
    }

    // --- ServicePricing CRUD methods ---
    public List<concordia.domain.ServicePricing> getAllServicePricingsDirect() {
        return servicePricingRepo.getAllServicePricings();
    }

    public void addServicePricingDirect(concordia.domain.ServicePricing servicePricing) {
        servicePricingRepo.insertServicePricing(servicePricing);
    }

    public void updateServicePricing(concordia.domain.ServicePricing servicePricing) {
        servicePricingRepo.updateServicePricing(servicePricing);
    }

    public void deleteServicePricingDirect(int servicePricingId) {
        servicePricingRepo.deleteServicePricing(servicePricingId);
    }

    // --- ServiceType and TransactionHistory methods ---
    public List<ServiceType> getAllServiceTypes() {
        return serviceRepo.getAllServiceTypes();
    }

    public List<ServicePricing> getAllServicePricings() {
        return serviceRepo.getAllServicePricings();
    }

    public List<TransactionHistory> getAllTransactionHistory() {
        return historyRepo.getAllTransactionHistory();
    }

    public List<Company> getAllCompanies() {
        return companyRepo.loadCompaniesWithUsers();
    }

    public void addServiceType(String typeName) {
        serviceRepo.insertNewServiceType(typeName);
    }

    public void addServicePricing(int serviceTypeId, double price, String currency, String effectiveDate) {
        serviceRepo.insertNewServicePricing(serviceTypeId, price, currency, effectiveDate);
    }

    public void deleteServiceType(int serviceTypeId) {
        serviceRepo.deleteServiceType(serviceTypeId);
    }

    public void deleteServicePricing(int servicePricingId) {
        serviceRepo.deleteServicePricing(servicePricingId);
    }

    public void addTransactionHistory(int serviceTypeId, int amount, String location, String provider, String deliveryDate, String notes) {
        historyRepo.insertTransactionHistory(serviceTypeId, amount, location, provider, deliveryDate, notes);
    }

    public void updateTransactionHistory(TransactionHistory hist) {
        historyRepo.updateTransactionHistory(hist);
    }

    public void deleteTransactionHistory(int transactionId) {
        historyRepo.deleteTransactionHistory(transactionId);
    }
}
