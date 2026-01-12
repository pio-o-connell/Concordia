
package concordia.controller;
import concordia.annotations.Controller;
import concordia.service.InventoryService;
import concordia.domain.Company;
import concordia.domain.ServiceType;
import concordia.domain.ServicePricing;
import concordia.domain.TransactionHistory;
import java.util.List;

@Controller
public class InventoryController {
    private final InventoryService service;
    public InventoryController(InventoryService service) {
        this.service = service;
    }

    // Retrieve all service types
    public List<ServiceType> getAllServiceTypes() {
        return service.getAllServiceTypes();
    }

    // Retrieve all service pricing
    public List<ServicePricing> getAllServicePricings() {
        return service.getAllServicePricings();
    }

    // Retrieve all transaction history
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
