package backend.service;

import backend.repository.CompanyRepository;
import backend.repository.ServiceTypeRepository;
import backend.repository.ServicePricingRepository;
import backend.repository.TransactionHistoryRepository;
import concordia.domain.Company;
import concordia.domain.ServiceType;
import concordia.domain.ServicePricing;
import concordia.domain.TransactionHistory;
import java.util.List;

public class InventoryService {
    private final CompanyRepository companyRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final ServicePricingRepository servicePricingRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;

    public InventoryService(CompanyRepository companyRepository, ServiceTypeRepository serviceTypeRepository, ServicePricingRepository servicePricingRepository, TransactionHistoryRepository transactionHistoryRepository) {
        this.companyRepository = companyRepository;
        this.serviceTypeRepository = serviceTypeRepository;
        this.servicePricingRepository = servicePricingRepository;
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    public List<ServiceType> getAllServiceTypes() {
        return serviceTypeRepository.getAllServiceTypes();
    }

    public List<ServicePricing> getAllServicePricings() {
        return servicePricingRepository.getAllServicePricings();
    }

    public List<TransactionHistory> getAllTransactionHistories() {
        return transactionHistoryRepository.getAllTransactionHistories();
    }

    public List<Company> getAllCompanies() {
        return companyRepository.getAllCompanies();
    }

    public void addServiceType(ServiceType serviceType) {
        serviceTypeRepository.insertServiceType(serviceType);
    }

    public void addServicePricing(ServicePricing servicePricing) {
        servicePricingRepository.insertServicePricing(servicePricing);
    }

    public void addTransactionHistory(TransactionHistory transactionHistory) {
        transactionHistoryRepository.insertTransactionHistory(transactionHistory);
    }



    public void updateServiceType(ServiceType serviceType) {
        serviceTypeRepository.updateServiceType(serviceType);
    }

    public void updateServicePricing(ServicePricing servicePricing) {
        servicePricingRepository.updateServicePricing(servicePricing);
    }

    public void updateTransactionHistory(TransactionHistory transactionHistory) {
        transactionHistoryRepository.updateTransactionHistory(transactionHistory);
    }

    public void deleteServiceType(int serviceTypeId) {
        serviceTypeRepository.deleteServiceType(serviceTypeId);
    }

    public void deleteServicePricing(int pricingId) {
        servicePricingRepository.deleteServicePricing(pricingId);
    }

    public void deleteTransactionHistory(int transactionId) {
        transactionHistoryRepository.deleteTransactionHistory(transactionId);
    }
}
