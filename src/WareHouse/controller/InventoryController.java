package WareHouse.controller;

import WareHouse.service.InventoryService;
import WareHouse.domain.Company;
import WareHouse.domain.history;
import java.sql.SQLException;
import java.util.List;

public class InventoryController {
    private final InventoryService service;
    public InventoryController(InventoryService service) {
        this.service = service;
    }
    public List<Company> getAllCompanies() throws SQLException {
        return service.getAllCompanies();
    }
    public void addItem(int companyId, int amount, String itemName, String notes) throws SQLException {
        service.addItem(companyId, amount, itemName, notes);
    }
    public void deleteItem(int itemId) throws SQLException {
        service.deleteItem(itemId);
    }
    public void addHistory(int itemId, int amount, String location, String provider, String deliveryDate, String notes) throws SQLException {
        service.addHistory(itemId, amount, location, provider, deliveryDate, notes);
    }
    public void updateHistory(history hist) throws SQLException {
        service.updateHistory(hist);
    }
    public void deleteHistory(int historyId) throws SQLException {
        service.deleteHistory(historyId);
    }
}
