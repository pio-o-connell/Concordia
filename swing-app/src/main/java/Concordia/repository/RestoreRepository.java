package concordia.repository;
import java.util.List;
import concordia.annotations.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import concordia.domain.Company;
import concordia.domain.ServiceType;
import concordia.domain.ServicePricing;
import concordia.domain.TransactionHistory;
import concordia.domain.User;

@Repository
public class RestoreRepository {
    private Connection con;
    public RestoreRepository(Connection con) {
        this.con = con;
    }

    public void setup(ArrayList<Company> companies) throws SQLException {
        try {
            // Example: fetch all service types and transaction histories for companies
            PreparedStatement statement = con.prepareStatement("SELECT * FROM SERVICE_TYPE");
            ResultSet serviceTypeResult = statement.executeQuery();
            java.util.Set<ServiceType> serviceTypes = new java.util.HashSet<>();
            while (serviceTypeResult.next()) {
                ServiceType serviceType = new ServiceType();
                serviceType.setServiceTypeId(serviceTypeResult.getInt("service_type_id"));
                serviceType.setTypeName(serviceTypeResult.getString("type_name"));
                serviceTypes.add(serviceType);
            }
            // Example: fetch all transaction histories
            PreparedStatement historyStmt = con.prepareStatement("SELECT * FROM TRANSACTION_HISTORY");
            ResultSet historyResult = historyStmt.executeQuery();
            List<TransactionHistory> transactionHistories = new java.util.ArrayList<>();
            while (historyResult.next()) {
                TransactionHistory history = new TransactionHistory();
                history.setTransactionHistoryId(historyResult.getInt("transaction_history_id"));
                history.setAmount(historyResult.getInt("amount"));
                history.setLocation(historyResult.getString("location"));
                history.setProvider(historyResult.getString("provider"));
                history.setDeliveryDate(historyResult.getString("delivery_date"));
                history.setNotes(historyResult.getString("notes"));
                // Optionally set ServiceType if you have a mapping
                transactionHistories.add(history);
            }
            // Example: assign serviceTypes to companies as needed
            for (Company company : companies) {
                company.setServiceTypes(serviceTypes);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }
}
// ...existing code...
