package Employee_Management;

import java.sql.*;
import javax.swing.JOptionPane;

public class ConnectionClass {
    public Connection con;
    public Statement stm;

    public ConnectionClass() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            String[] possiblePasswords = {"cicada3301", "Sou@2211", ""};
            String selectedPassword = null;

            // Try connecting to MySQL server to ensure database exists and find working credentials
            for (String pass : possiblePasswords) {
                try (Connection tempCon = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/?useSSL=false&allowPublicKeyRetrieval=true", "root", pass);
                     Statement tempStm = tempCon.createStatement()) {
                    tempStm.executeUpdate("CREATE DATABASE IF NOT EXISTS employee_management");
                    selectedPassword = pass;
                    break;
                } catch (Exception ignored) {
                }
            }

            if (selectedPassword == null) {
                selectedPassword = "cicada3301"; // default if connection test could not establish
            }

            // Connect to employee_management database
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/employee_management?useSSL=false&allowPublicKeyRetrieval=true", 
                    "root", selectedPassword);
            stm = con.createStatement();

            // Initialize required tables if they don't exist
            initDatabase();

        } catch (Exception ex) {
            System.err.println("Database Connection Failure: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void initDatabase() {
        try {
            // 1. logindata table
            stm.executeUpdate("CREATE TABLE IF NOT EXISTS logindata ("
                    + "username VARCHAR(100) PRIMARY KEY, "
                    + "password VARCHAR(100) NOT NULL)");

            // 2. employee table
            stm.executeUpdate("CREATE TABLE IF NOT EXISTS employee ("
                    + "Eid VARCHAR(50) PRIMARY KEY, "
                    + "name VARCHAR(100), "
                    + "Address VARCHAR(255), "
                    + "Dob VARCHAR(50), "
                    + "Email VARCHAR(100), "
                    + "Phone VARCHAR(50), "
                    + "Post VARCHAR(100), "
                    + "Aadhar VARCHAR(50))");

            // 3. attendance table
            stm.executeUpdate("CREATE TABLE IF NOT EXISTS attendance ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "Eid VARCHAR(50), "
                    + "name VARCHAR(100), "
                    + "email VARCHAR(100), "
                    + "first_half VARCHAR(20), "
                    + "second_half VARCHAR(20), "
                    + "date VARCHAR(50))");

            // Check if logindata has any users; if empty, insert default admin
            ResultSet rs = stm.executeQuery("SELECT COUNT(*) FROM logindata");
            if (rs.next() && rs.getInt(1) == 0) {
                stm.executeUpdate("INSERT INTO logindata (username, password) VALUES ('admin', 'admin123')");
            }
            rs.close();
        } catch (Exception e) {
            System.err.println("Error initializing database tables: " + e.getMessage());
        }
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        if (con == null || con.isClosed()) {
            throw new SQLException("Database connection is not open.");
        }
        return con.prepareStatement(sql);
    }

    public static void main(String[] args) {
        new ConnectionClass();
    }
}

