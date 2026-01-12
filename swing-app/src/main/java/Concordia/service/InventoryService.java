
package concordia.service;
import concordia.annotations.Service;

import concordia.repository.CompanyRepository;
import concordia.repository.ServiceRepository;
import concordia.repository.TransactionHistoryRepository;
import concordia.domain.Company;
import concordia.domain.ServiceType;
import concordia.domain.ServicePricing;
import concordia.domain.TransactionHistory;
import java.util.List;

@Service
public class InventoryService {
    private final CompanyRepository companyRepo;
    private final ServiceRepository serviceRepo;
    private final TransactionHistoryRepository historyRepo;
    public InventoryService(CompanyRepository companyRepo, ServiceRepository serviceRepo, TransactionHistoryRepository historyRepo) {
        this.companyRepo = companyRepo;
        this.serviceRepo = serviceRepo;
        this.historyRepo = historyRepo;
    }

    // Retrieve all service types
    public List<ServiceType> getAllServiceTypes() {
        return serviceRepo.getAllServiceTypes();
    }

    // Retrieve all service pricing
    public List<ServicePricing> getAllServicePricings() {
        return serviceRepo.getAllServicePricings();
    }

    // Retrieve all transaction history records
    public List<TransactionHistory> getAllTransactionHistory() {
        return historyRepo.getAllTransactionHistory();
    }

    // Example thin delegation methods
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
