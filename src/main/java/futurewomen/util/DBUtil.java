package futurewomen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static Connection connection = null;
    public static Connection getConnection() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/TicketReservation";
        String user = "ticketuser";
        String password = "ticketuser";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(jdbcUrl, user, password);
        } catch (SQLException e) {
            System.out.println("Error connecting to database. " + e.getMessage());
            try {
                    System.out.println("Closing connection... ");
                    connection.close();
            } catch (SQLException ex) {
                System.out.println("Failed to close connection. " + ex.getMessage());
            }
        }
        return connection;
    }

    public static void closeDB(){
        if (connection!=null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Failed to close connection. " + e.getMessage());
            }
        }
    }

}
