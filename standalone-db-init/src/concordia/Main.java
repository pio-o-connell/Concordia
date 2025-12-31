package concordia;

import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/concordia";
        String user = "postgres";
        String password = "password";
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            Databases db = new Databases(con);
            ArrayList<Object> dummyList = new ArrayList<>(); // Replace with actual Company/User/Item classes if needed
            // Setup tables and populate data
            db.init(con, dummyList); // Creates tables and populates test data
            System.out.println("Database initialized and populated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
