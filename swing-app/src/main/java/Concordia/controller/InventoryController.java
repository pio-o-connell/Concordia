
package concordia.controller;

import concordia.annotations.Controller;
import concordia.service.InventoryService;
import concordia.domain.Company;
import concordia.domain.ServiceType;
import concordia.domain.ServicePricing;
import concordia.domain.TransactionHistory;
import concordia.domain.Services;
import java.util.List;

@Controller
public class InventoryController {
    private final InventoryService service;
    public InventoryController(InventoryService service) {
        this.service = service;
    }

    // --- Services CRUD methods ---
    public void addService(Services service) {
        this.service.addService(service);
    }

    public void updateService(Services service) {
        this.service.updateService(service);
    }

    public void deleteService(int serviceId) {
        this.service.deleteService(serviceId);
    }

    // --- ServicePricing CRUD methods (direct) ---
    public List<ServicePricing> getAllServicePricingsDirect() {
        return this.service.getAllServicePricingsDirect();
    }

    public void addServicePricingDirect(ServicePricing servicePricing) {
        this.service.addServicePricingDirect(servicePricing);
    }

    public void updateServicePricing(ServicePricing servicePricing) {
        this.service.updateServicePricing(servicePricing);
    }

    public void deleteServicePricingDirect(int servicePricingId) {
        this.service.deleteServicePricingDirect(servicePricingId);
    }

    // --- Standard methods ---
    public List<Services> getAllServices() {
        return service.getAllServices();
    }

    public List<ServiceType> getAllServiceTypes() {
        return service.getAllServiceTypes();
    }

    public List<ServicePricing> getAllServicePricings() {
        return service.getAllServicePricings();
    }

    public List<TransactionHistory> getAllTransactionHistory() {
        return service.getAllTransactionHistory();
    }

    public List<Company> getAllCompanies() {
        return service.getAllCompanies();
    }

    public void addServiceType(String typeName) {
        service.addServiceType(typeName);
    }

    public void addServicePricing(int serviceTypeId, double price, String currency, String effectiveDate) {
        service.addServicePricing(serviceTypeId, price, currency, effectiveDate);
    }

    public void deleteServiceType(int serviceTypeId) {
        service.deleteServiceType(serviceTypeId);
    }

    public void deleteServicePricing(int servicePricingId) {
        service.deleteServicePricing(servicePricingId);
    }

    public void addTransactionHistory(int serviceTypeId, int amount, String location, String provider, String deliveryDate, String notes) {
        service.addTransactionHistory(serviceTypeId, amount, location, provider, deliveryDate, notes);
    }

    public void updateTransactionHistory(TransactionHistory hist) {
        service.updateTransactionHistory(hist);
    }

    public void deleteTransactionHistory(int transactionId) {
        service.deleteTransactionHistory(transactionId);
    }
}
