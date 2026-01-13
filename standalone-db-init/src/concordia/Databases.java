// Standalone version: Remove dependencies on other project classes for demo
package concordia;

import java.sql.*;
import java.util.*;

public final class Databases {
    public void resetAndPopulateGardeningTestData(Connection con) throws SQLException {
        java.util.Random rand = new java.util.Random();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        PreparedStatement delHist = con.prepareStatement("DELETE FROM history");
        delHist.executeUpdate();
        PreparedStatement delService = con.prepareStatement("DELETE FROM service");
        delService.executeUpdate();
        PreparedStatement delComp = con.prepareStatement("DELETE FROM company");
        delComp.executeUpdate();
        String[] companyNames = {"Kanturk-Services", "GreenThumb Supplies"};
        int[] companyIds = {44008177, 77008177};
        for (int i = 0; i < companyNames.length; i++) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO company (company_id, company_name) VALUES (?, ?)");
            ps.setInt(1, companyIds[i]);
            ps.setString(2, companyNames[i]);
            ps.executeUpdate();
            ps.close();
        }
        String[] kanturkServices = {"Shovel", "Rake", "Lawn Mower", "Hose"};
        int kanturkCompanyId = 44008177;
        int kanturkServiceIdBase = 44020000;
        for (int j = 0; j < kanturkServices.length; j++) {
            int serviceId = kanturkServiceIdBase + j;
            String serviceNote = "Kanturk service: " + kanturkServices[j] + " - essential for every garden.";
            PreparedStatement ps = con.prepareStatement("INSERT INTO service (service_id, service_name, company_id, quantity, notes) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, serviceId);
            ps.setString(2, kanturkServices[j]);
            ps.setInt(3, kanturkCompanyId);
            ps.setInt(4, 10 + rand.nextInt(90));
            ps.setString(5, serviceNote);
            ps.executeUpdate();
            ps.close();
            String[] locations = {"Greenhouse", "Garden Shed", "Nursery", "Compost Area"};
            String[] providers = {"GardenWorld", "PlantDepot", "SeedMasters", "ToolTown"};
            String[] notes = {
                "Planted in spring for best results",
                "Keep soil moist and well-drained",
                "Fertilize every two weeks"
            };
            for (int h = 0; h < 4; h++) {
                int historyId = 21000000 + j * 10 + h;
                int amount = 5 + rand.nextInt(20);
                String location = locations[rand.nextInt(locations.length)];
                String provider = providers[rand.nextInt(providers.length)];
                String deliveryDate = sdf.format(new java.util.Date(System.currentTimeMillis() - rand.nextInt(1000 * 60 * 60 * 24 * 365)));
                String note = notes[h % notes.length] + " (" + kanturkServices[j] + ")";
                PreparedStatement psHist = con.prepareStatement(
                    "INSERT INTO history (history_id, service_id, amount, location, provider, delivery_date, notes) VALUES (?, ?, ?, ?, ?, ?, ?)"
                );
                psHist.setInt(1, historyId);
                psHist.setInt(2, serviceId);
                psHist.setInt(3, amount);
                psHist.setString(4, location);
                psHist.setString(5, provider);
                psHist.setString(6, deliveryDate);
                psHist.setString(7, note);
                psHist.executeUpdate();
                psHist.close();
            }
        }
        String[] gardeningServices = {"Compost Bin", "Garden Hoe", "Tomato Seeds", "Watering Can"};
        int gardeningCompanyId = 77008177;
        int serviceIdBase = 44010000;
        for (int j = 0; j < gardeningServices.length; j++) {
            int serviceId = serviceIdBase + j;
            String serviceNote = "Gardening service: " + gardeningServices[j] + " - essential for every garden.";
            PreparedStatement ps = con.prepareStatement("INSERT INTO service (service_id, service_name, company_id, quantity, notes) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, serviceId);
            ps.setString(2, gardeningServices[j]);
            ps.setInt(3, gardeningCompanyId);
            ps.setInt(4, 10 + rand.nextInt(90));
            ps.setString(5, serviceNote);
            ps.executeUpdate();
            ps.close();
            String[] locations = {"Greenhouse", "Garden Shed", "Nursery", "Compost Area"};
            String[] providers = {"GardenWorld", "PlantDepot", "SeedMasters", "ToolTown"};
            String[] notes = {
                "Planted in spring for best results",
                "Keep soil moist and well-drained",
                "Fertilize every two weeks"
            };
            for (int h = 0; h < 4; h++) {
                int historyId = 20000000 + j * 10 + h;
                int amount = 5 + rand.nextInt(20);
                String location = locations[rand.nextInt(locations.length)];
                String provider = providers[rand.nextInt(providers.length)];
                String deliveryDate = sdf.format(new java.util.Date(System.currentTimeMillis() - rand.nextInt(1000 * 60 * 60 * 24 * 365)));
                String note = notes[h % notes.length] + " (" + gardeningServices[j] + ")";
                PreparedStatement psHist = con.prepareStatement(
                    "INSERT INTO history (history_id, service_id, amount, location, provider, delivery_date, notes) VALUES (?, ?, ?, ?, ?, ?, ?)"
                );
                psHist.setInt(1, historyId);
                psHist.setInt(2, serviceId);
                psHist.setInt(3, amount);
                psHist.setString(4, location);
                psHist.setString(5, provider);
                psHist.setString(6, deliveryDate);
                psHist.setString(7, note);
                psHist.executeUpdate();
                psHist.close();
            }
        }
    }

    public Databases(Connection con) throws Exception {
        this.con = con;
    }

    Connection con;

    public boolean init(Connection con, ArrayList<Object> companyList) {
        this.con = con;
        try {
            String[] dropTables = {
                "DROP TABLE IF EXISTS history",
                "DROP TABLE IF EXISTS service",
                "DROP TABLE IF EXISTS users",
                "DROP TABLE IF EXISTS company"
            };
            for (String sql : dropTables) {
                try {
                    PreparedStatement stmt = con.prepareStatement(sql);
                    stmt.executeUpdate();
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            String createCompany = "CREATE TABLE company (Company_ID SERIAL PRIMARY KEY, Company_title VARCHAR(25), company_name VARCHAR(255));";
            String createUsers = "CREATE TABLE users (User_ID SERIAL PRIMARY KEY, User_Name VARCHAR(25), User_Password VARCHAR(25) NOT NULL, Company_ID INT NOT NULL);";
            String createService = "CREATE TABLE service (Service_ID SERIAL PRIMARY KEY, Company_ID INT NOT NULL, quantity INT, service_name VARCHAR(25), Location VARCHAR(25), Notes VARCHAR(200));";
            String createHistory = "CREATE TABLE history (history_id SERIAL PRIMARY KEY, service_ID INT NOT NULL, amount INT, location VARCHAR(25), provider VARCHAR(25), delivery_date VARCHAR(25), notes VARCHAR(200));";
            String[] createTables = {createCompany, createUsers, createService, createHistory};
            for (String sql : createTables) {
                try {
                    PreparedStatement stmt = con.prepareStatement(sql);
                    stmt.executeUpdate();
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            resetAndPopulateGardeningTestData(con);
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
            return false;
        }
        return true;
    }
}
