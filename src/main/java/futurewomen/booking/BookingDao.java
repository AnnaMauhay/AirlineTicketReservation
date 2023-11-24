package futurewomen.booking;

import futurewomen.flight.Flight;
import futurewomen.util.DBUtil;

import java.sql.*;


import static futurewomen.util.DBUtil.closeDB;

public class BookingDao {
    /**
     * A query that joins Customers and Bookings tables to display a list of
     * customers along with their bookings.
     */
    public void queryCustomersWithBookings() {
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = null;
            ResultSet result = null;

            String query = "SELECT Name, Email, Phone,BookingDate, NumberOfPassengers, Status, FlightID " +
                    "FROM Customers " +
                    "INNER JOIN Bookings " +
                    "ON Customers.CustomerID = Bookings.CustomerID;";
            statement = connection.prepareStatement(query);

            result = statement.executeQuery();

            System.out.printf("| %-10s | %-30s | %-20s | %-30s | %-18s | %-14s | %-8s |%n",
                    "Name", "Email", "Phone", "BookingDate", "NumberOfPassengers", "Status", "FlightID");
            while (result.next()) {
                String name = result.getString("Name");
                String email = result.getString("Email");
                String phone = result.getString("Phone");
                Timestamp bookingDate = result.getTimestamp("BookingDate");
                int numberOfPassengers = result.getInt("NumberOfPassengers");
                String status = result.getString("Status");
                int flightID = result.getInt("FlightID");

                System.out.printf("| %-10s | %-30s | %-20s | %-30s | %18s | %-14s | %8s |%n",
                        name, email, phone, bookingDate, numberOfPassengers, status, flightID);
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeDB();
        }
    }

    /**
     * Query to Retrieve All Bookings for a Given Customer
     */
    public void queryBookingsByCustomer(int CustomerIDQuery) {
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = null;
            ResultSet result = null;

            String query = "SELECT Customers.CustomerID, Name, Email, Phone, BookingDate, NumberOfPassengers, Status, FlightID\n" +
                    "FROM Customers " +
                    "INNER JOIN Bookings " +
                    "ON Customers.CustomerID = Bookings.CustomerID " +
                    "WHERE Customers.CustomerID = ?;";
            statement = connection.prepareStatement(query);
            statement.setInt(1, CustomerIDQuery);

            result = statement.executeQuery();

            System.out.printf("| %-10s | %-10s | %-30s | %-20s | %-30s | %-18s | %-14s | %-8s |%n",
                    "CustomerID", "Name", "Email", "Phone", "BookingDate", "NumberOfPassengers", "Status", "FlightID");
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
                        customerID, name, email, phone, bookingDate, numberOfPassengers, status, flightID);
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeDB();
        }

    }

    public boolean insertBookingToDB(int flightID, int customerID, Timestamp bookingDate, int numOfPassengers) {
        boolean returnValue = false;
        Connection connection = DBUtil.getConnection();
        ResultSet result = null;

        try {
            if (connection.isValid(0)) {
                connection.setAutoCommit(false);
                connection.setSavepoint();

                String querySeatsAvailable = "SELECT SeatsAvailable FROM Flight WHERE FlightID=?";
                PreparedStatement statementSeat = connection.prepareStatement(querySeatsAvailable);
                statementSeat.setInt(1, flightID);

                result = statementSeat.executeQuery();
                boolean isEnoughSeat = false;
                int seatsAvailable = 0;
                if (result.next()) {
                    seatsAvailable = result.getInt("SeatsAvailable");
                    isEnoughSeat = seatsAvailable >= numOfPassengers;


                    if (isEnoughSeat) {
                        int rowsAffectedInsertBooking = 0;
                        String queryInsertBooking = "INSERT INTO Bookings (CustomerID, FlightID, BookingDate, NumberOfPassengers) " +
                                "VALUES (?, ?, ?, ?);";
                        PreparedStatement statementInsertBooking = connection.prepareStatement(queryInsertBooking);
                        statementInsertBooking.setInt(1, customerID);
                        statementInsertBooking.setInt(2, flightID);
                        statementInsertBooking.setTimestamp(3, bookingDate);
                        statementInsertBooking.setInt(4, numOfPassengers);

                        rowsAffectedInsertBooking = statementInsertBooking.executeUpdate();

                        int rowsAffectedUpdateSeats = 0;
                        String queryUpdateSeat = "UPDATE Flight SET SeatsAvailable = ? " +
                                "WHERE FlightID = ? ;";
                        PreparedStatement statementUpdateSeat = connection.prepareStatement(queryUpdateSeat);
                        statementUpdateSeat.setInt(1, seatsAvailable - numOfPassengers);
                        statementUpdateSeat.setInt(2, flightID);

                        rowsAffectedUpdateSeats = statementUpdateSeat.executeUpdate();

                        if (rowsAffectedInsertBooking == 0 || rowsAffectedUpdateSeats == 0) {
                            connection.rollback();
                            System.out.println("Rolling back: One of the transactions failed.");
                        } else {
                            connection.commit();
                            returnValue = true;
                        }

                        statementUpdateSeat.close();
                        statementInsertBooking.close();
                    } else System.out.println("Not enough seats available.");

                } else System.out.println("Please check if input FlightID and CustomerID are valid.");

                statementSeat.close();
            } else System.out.println("No valid connection");
        } catch (SQLException e) {
            System.out.println("Failed to insert booking: " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Failed to rollback: " + e.getMessage());
            }
        } finally {
            closeDB();
        }
        return returnValue;
    }
}
