package WareHouse.controller;

import WareHouse.service.BackupService;

public class BackupController {
    private final BackupService service;
    public BackupController(BackupService service) {
        this.service = service;
    }
    public void backupPressed(java.util.List<WareHouse.domain.Company> companies) throws java.sql.SQLException {
        service.backup(companies);
    }
}
