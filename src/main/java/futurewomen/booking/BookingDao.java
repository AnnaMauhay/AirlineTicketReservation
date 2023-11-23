package futurewomen.booking;

import futurewomen.DBUtil;

import java.sql.*;


import static futurewomen.DBUtil.closeDB;

public class BookingDao {
    /**
     * A query that joins Customers and Bookings tables to display a list of
     * customers along with their bookings.
     */
    public void queryCustomersWithBookings() {
        try {
            PreparedStatement statement = null;
            ResultSet result = null;

            String query = "SELECT Name, Email, Phone,BookingDate, NumberOfPassengers, Status, FlightID " +
                    "FROM Customers " +
                    "INNER JOIN Bookings " +
                    "ON Customers.CustomerID = Bookings.CustomerID;";
            statement = DBUtil.getConnection().prepareStatement(query);

            result = statement.executeQuery();

            System.out.printf("| %-10s | %-30s | %-20s | %-30s | %-18s | %-14s | %-8s |%n",
                    "Name", "Email", "Phone","BookingDate","NumberOfPassengers","Status", "FlightID");
            while (result.next()) {
                String name = result.getString("Name");
                String email = result.getString("Email");
                String phone = result.getString("Phone");
                Timestamp bookingDate = result.getTimestamp("BookingDate");
                int numberOfPassengers = result.getInt("NumberOfPassengers");
                String status = result.getString("Status");
                int flightID = result.getInt("FlightID");

                System.out.printf("| %-10s | %-30s | %-20s | %-30s | %18s | %-14s | %8s |%n",
                        name, email, phone,bookingDate,numberOfPassengers,status, flightID);
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        closeDB();
    }

    /**
     * Query to Retrieve All Bookings for a Given Customer
     */
    public void queryBookingsByCustomer(int CustomerIDQuery) {
        try {
            PreparedStatement statement = null;
            ResultSet result = null;

            String query = "SELECT Customers.CustomerID, Name, Email, Phone, BookingDate, NumberOfPassengers, Status, FlightID\n" +
                    "FROM Customers " +
                    "INNER JOIN Bookings " +
                    "ON Customers.CustomerID = Bookings.CustomerID " +
                    "WHERE Customers.CustomerID = ?;";
            statement = DBUtil.getConnection().prepareStatement(query);
            statement.setInt(1, CustomerIDQuery);

            result = statement.executeQuery();

            System.out.printf("| %-10s | %-10s | %-30s | %-20s | %-30s | %-18s | %-14s | %-8s |%n",
                    "CustomerID","Name", "Email", "Phone","BookingDate","NumberOfPassengers","Status", "FlightID");
            while (result.next()) {
                int customerID = result.getInt("CustomerID");
                String name = result.getString("Name");
                String email = result.getString("Email");
                String phone = result.getString("Phone");
                Timestamp bookingDate = result.getTimestamp("BookingDate");
                int numberOfPassengers = result.getInt("NumberOfPassengers");
                String status = result.getString("Status");
                int flightID = result.getInt("FlightID");

                System.out.printf("| %-10s | %-10s | %-30s | %-20s | %-30s | %18s | %-14s | %8s |%n",
                        customerID,name, email, phone,bookingDate,numberOfPassengers,status, flightID);
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        closeDB();
    }
}
