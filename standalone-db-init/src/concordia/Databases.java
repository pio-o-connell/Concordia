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
        PreparedStatement delItem = con.prepareStatement("DELETE FROM item");
        delItem.executeUpdate();
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
        String[] kanturkItems = {"Shovel", "Rake", "Lawn Mower", "Hose"};
        int kanturkCompanyId = 44008177;
        int kanturkItemIdBase = 44020000;
        for (int j = 0; j < kanturkItems.length; j++) {
            int itemId = kanturkItemIdBase + j;
            String itemNote = "Kanturk item: " + kanturkItems[j] + " - essential for every garden.";
            PreparedStatement ps = con.prepareStatement("INSERT INTO item (item_id, item_name, company_id, quantity, notes) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, itemId);
            ps.setString(2, kanturkItems[j]);
            ps.setInt(3, kanturkCompanyId);
            ps.setInt(4, 10 + rand.nextInt(90));
            ps.setString(5, itemNote);
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
                String note = notes[h % notes.length] + " (" + kanturkItems[j] + ")";
                PreparedStatement psHist = con.prepareStatement(
                    "INSERT INTO history (history_id, item_id, amount, location, provider, delivery_date, notes) VALUES (?, ?, ?, ?, ?, ?, ?)"
                );
                psHist.setInt(1, historyId);
                psHist.setInt(2, itemId);
                psHist.setInt(3, amount);
                psHist.setString(4, location);
                psHist.setString(5, provider);
                psHist.setString(6, deliveryDate);
                psHist.setString(7, note);
                psHist.executeUpdate();
                psHist.close();
            }
        }
        String[] gardeningItems = {"Compost Bin", "Garden Hoe", "Tomato Seeds", "Watering Can"};
        int gardeningCompanyId = 77008177;
        int itemIdBase = 44010000;
        for (int j = 0; j < gardeningItems.length; j++) {
            int itemId = itemIdBase + j;
            String itemNote = "Gardening item: " + gardeningItems[j] + " - essential for every garden.";
            PreparedStatement ps = con.prepareStatement("INSERT INTO item (item_id, item_name, company_id, quantity, notes) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, itemId);
            ps.setString(2, gardeningItems[j]);
            ps.setInt(3, gardeningCompanyId);
            ps.setInt(4, 10 + rand.nextInt(90));
            ps.setString(5, itemNote);
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
                String note = notes[h % notes.length] + " (" + gardeningItems[j] + ")";
                PreparedStatement psHist = con.prepareStatement(
                    "INSERT INTO history (history_id, item_id, amount, location, provider, delivery_date, notes) VALUES (?, ?, ?, ?, ?, ?, ?)"
                );
                psHist.setInt(1, historyId);
                psHist.setInt(2, itemId);
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
                "DROP TABLE IF EXISTS item",
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
            String createItem = "CREATE TABLE item (Item_ID SERIAL PRIMARY KEY, Company_ID INT NOT NULL, quantity INT, item_name VARCHAR(25), Location VARCHAR(25), Notes VARCHAR(200));";
            String createHistory = "CREATE TABLE history (history_id SERIAL PRIMARY KEY, item_ID INT NOT NULL, amount INT, location VARCHAR(25), provider VARCHAR(25), delivery_date VARCHAR(25), notes VARCHAR(200));";
            String[] createTables = {createCompany, createUsers, createItem, createHistory};
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
