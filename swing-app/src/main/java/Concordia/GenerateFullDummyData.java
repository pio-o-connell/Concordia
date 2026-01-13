package concordia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Random;
import java.util.Date;
import java.text.SimpleDateFormat;

import concordia.annotations.Configuration;

@Configuration
public class GenerateFullDummyData {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://127.0.0.1:3306/concordia?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "root";
        String password = "ROOT";
        Connection con = DriverManager.getConnection(url, user, password);
        Statement stmt = con.createStatement();
        Random rand = new Random();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Optional: Clear existing data
        stmt.executeUpdate("DELETE FROM history");
        stmt.executeUpdate("DELETE FROM services");
        stmt.executeUpdate("DELETE FROM company");

        // Insert companies
        String[] companyNames = {"Kanturk-Services", "Mallow-Services", "Cork-Enterprises"};
        int[] companyIds = {44008177, 55008177, 66008177};
        for (int i = 0; i < companyNames.length; i++) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO company (company_id, company_name) VALUES (?, ?)");
            ps.setInt(1, companyIds[i]);
            ps.setString(2, companyNames[i]);
            ps.executeUpdate();
            ps.close();
        }

        // Insert services for each company
        String[] serviceNames = {"Widget", "Gadget", "Thingamajig"};
        int serviceIdBase = 33008177;
        int serviceCount = 3;
        for (int i = 0; i < companyIds.length; i++) {
            for (int j = 0; j < serviceCount; j++) {
                int serviceId = serviceIdBase + i * 10 + j;
                PreparedStatement ps = con.prepareStatement("INSERT INTO services (service_id, service_name, company_id, quantity) VALUES (?, ?, ?, ?)");
                ps.setInt(1, serviceId);
                ps.setString(2, serviceNames[j]);
                ps.setInt(3, companyIds[i]);
                ps.setInt(4, 10 + rand.nextInt(90));
                ps.executeUpdate();
                ps.close();
            }
        }

        // Insert history for each services
        String[] locations = {"Dublin", "Cork", "Limerick", "Galway", "Waterford"};
        String[] providers = {"Sony", "Samsung", "LG", "Panasonic", "Philips"};
        int historyIdBase = 10000000;
        for (int i = 0; i < companyIds.length; i++) {
            for (int j = 0; j < serviceCount; j++) {
                int serviceId = serviceIdBase + i * 10 + j;
                for (int h = 0; h < 5; h++) { // 5 history records per services
                    int historyId = historyIdBase + i * 100 + j * 10 + h;
                    int amount = 1 + rand.nextInt(100);
                    String location = locations[rand.nextInt(locations.length)];
                    String provider = providers[rand.nextInt(providers.length)];
                    String deliveryDate = sdf.format(new Date(System.currentTimeMillis() - rand.nextInt(1000 * 60 * 60 * 24 * 365)));
                    PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO history (history_id, service_id, amount, location, provider, delivery_date) VALUES (?, ?, ?, ?, ?, ?)"
                    );
                    ps.setInt(1, historyId);
                    ps.setInt(2, serviceId);
                    ps.setInt(3, amount);
                    ps.setString(4, location);
                    ps.setString(5, provider);
                    ps.setString(6, deliveryDate);
                    ps.executeUpdate();
                    ps.close();
                }
            }
        }
        con.close();
        System.out.println("Dummy company, services, and history data generated.");
    }
}
