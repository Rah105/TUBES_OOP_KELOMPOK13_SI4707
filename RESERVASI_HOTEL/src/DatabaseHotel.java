import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHotel {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/reservasi_hotel?connectTimeout=5000&socketTimeout=5000";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // Removed static connection variable

    public DatabaseHotel() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Removed closeConnection method as connections are now managed in the calling
    // methods
}