

package concordia.repository;
import concordia.annotations.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import concordia.domain.Company;
import concordia.domain.User;

@Repository
public class BackupRepository {
    private Connection con;
    public BackupRepository(Connection con) {
        this.con = con;
    }

    public void backup(ArrayList<Company> Concordia) throws SQLException {
        try {
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("DROP DATABASE if exists BackupConcordia");
            statement.executeUpdate();

            statement = (PreparedStatement) con.prepareStatement("CREATE DATABASE BackupConcordia");
            statement.executeUpdate();

            statement = (PreparedStatement) con.prepareStatement("USE BackupConcordia");
            statement.executeUpdate();

            String query = "CREATE TABLE Company(" +
                    "Company_ID INT NOT NULL," +
                    "Company_title CHAR(25) NULL," +
                    "PRIMARY KEY(Company_ID)" +
                    ")" +
                    "ENGINE = InnoDB  ";
            statement = (PreparedStatement) con.prepareStatement(query);
            statement.executeUpdate();

                // Only backup Company and User tables as per new schema
                query = "CREATE TABLE Users(" +
                    "User_ID INT NOT NULL," +
                    "User_Name CHAR(25) NULL," +
                    "User_Password CHAR(25) NOT NULL," +
                    "Company_ID INT NOT NULL," +
                    "PRIMARY KEY(User_ID)," +
                    "CONSTRAINT fk_companies1 FOREIGN KEY(Company_ID)" +
                    "REFERENCES Company(Company_ID)" +
                    " )" +
                    " ENGINE = InnoDB";
                statement = (PreparedStatement) con.prepareStatement(query);
                statement.executeUpdate();

                // Add ServiceType, ServicePricing, TransactionHistory tables as needed
                query = "CREATE TABLE ServiceType(" +
                    "service_type_id INT NOT NULL AUTO_INCREMENT," +
                    "type_name CHAR(50) NOT NULL," +
                    "PRIMARY KEY(service_type_id)" +
                    ") ENGINE = InnoDB";
                statement = (PreparedStatement) con.prepareStatement(query);
                statement.executeUpdate();

                query = "CREATE TABLE ServicePricing(" +
                    "service_pricing_id INT NOT NULL AUTO_INCREMENT," +
                    "service_type_id INT NOT NULL," +
                    "price DOUBLE NOT NULL," +
                    "PRIMARY KEY(service_pricing_id)," +
                    "CONSTRAINT fk_service_type FOREIGN KEY(service_type_id) REFERENCES ServiceType(service_type_id)" +
                    ") ENGINE = InnoDB";
                statement = (PreparedStatement) con.prepareStatement(query);
                statement.executeUpdate();

                query = "CREATE TABLE TransactionHistory(" +
                    "transaction_history_id INT NOT NULL AUTO_INCREMENT," +
                    "service_type_id INT NOT NULL," +
                    "amount INT NULL," +
                    "location CHAR(50) NULL," +
                    "provider CHAR(50) NULL," +
                    "delivery_date CHAR(25) NULL," +
                    "notes TEXT NULL," +
                    "PRIMARY KEY(transaction_history_id)," +
                    "CONSTRAINT fk_service_type_th FOREIGN KEY(service_type_id) REFERENCES ServiceType(service_type_id)" +
                    ") ENGINE = InnoDB";
                statement = (PreparedStatement) con.prepareStatement(query);
                statement.executeUpdate();

                // for Company
                System.out.println(Concordia.get(0).getCompanyId());
                System.out.println("Sizeof Company Array" + Concordia.size());

                // backup the data to the backup
                for (int i = 0; i < Concordia.size(); i++) { // will be only one company here
                statement = (PreparedStatement) con.prepareStatement("INSERT INTO COMPANY(Company_ID,Company_title)  VALUES  (?,?)");
                statement.setInt(1, Concordia.get(i).getCompanyId());
                statement.setString(2, Concordia.get(i).getCompanyName());
                statement.executeUpdate();
                // backup users if needed
                /* for (User user : Concordia.get(i).getUsers()) {
                    statement = (PreparedStatement) con.prepareStatement("INSERT INTO Users(USER_ID,USER_NAME,USER_PASSWORD,COMPANY_ID)  VALUES  (?,?,?,?)");
                    statement.setInt(1, user.getUserId());
                    statement.setString(2, user.getUsername());
                    statement.setString(3, user.getPassword());
                    statement.setInt(4, Concordia.get(i).getCompanyId());
                    statement.executeUpdate();
                } */
                }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }
}
