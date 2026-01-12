//------------------------------------------------------------------//
package concordia;
import java.util.List;

//------------------------------------------------------------------//

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import concordia.domain.Company;
import concordia.domain.User;
// Removed obsolete Item/history imports

// Restores the database from backup - loads into memory
// The 'Restore' button functionality in main window
// ------------------------------------------------------------------//



//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.PreparedStatement;
import concordia.annotations.Configuration;

@Configuration
public class DatabaseRestore {

    Connection con;
    java.util.Set<User> userSet = new java.util.HashSet<>();

    public DatabaseRestore(Connection con) {
        this.con = con;
    }

    public void setup(Connection con1, ArrayList<Company> companies) throws SQLException {
        this.con = con1;
        userSet.clear();
        try {
            // Restore users
            PreparedStatement statement3 = (PreparedStatement) con.prepareStatement("select * from users ");
            ResultSet result3 = statement3.executeQuery();
            while (result3.next()) {
                System.out.println("userId\n" + result3.getInt(1) + " Username:" + result3.getString(2));
                System.out.println("user Password\n" + result3.getString(3) + " company id " + result3.getString(4));
                userSet.add(new User(result3.getInt(1), result3.getInt(4), result3.getString(2), result3.getString(3)));
            }

            // Restore companies
            PreparedStatement statement4 = (PreparedStatement) con.prepareStatement("select * from company");
            ResultSet result4 = statement4.executeQuery();
            while (result4.next()) {
                System.out.println("\n" + result4.getInt(1) + " name:" + result4.getString(2));
                // Company(int companyId, String companyTitle, String companyName, Set<Item> items, Set<User> users)
                companies.add(new Company(result4.getInt(1), result4.getString(2), result4.getString(3), null, userSet));
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }

}
// ...existing code...
