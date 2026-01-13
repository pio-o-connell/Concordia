
package concordia.service;
import concordia.annotations.Services;

import concordia.repository.BackupRepository;
import concordia.domain.Company;
import java.sql.SQLException;
import java.util.List;

@Services
public class BackupService {
    private final BackupRepository repo;
    public BackupService(BackupRepository repo) {
        this.repo = repo;
    }
    public void backup(List<Company> companies) throws SQLException {
        repo.backup(new java.util.ArrayList<>(companies));
    }
}
