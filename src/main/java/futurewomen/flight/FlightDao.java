package futurewomen.flight;

import futurewomen.util.DBUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static futurewomen.util.DBUtil.closeDB;

public class FlightDao {

    public int insertFlightToDB(Flight flight) {
        int result =0;
        try (Connection connection = DBUtil.getConnection()) {
            if (connection.isValid(0)){
                System.out.println("A connection is valid");
                String query = "INSERT INTO Flight (FlightID, Airline, Origin, Destination, " +
                        "DepartureTime, ArrivalTime, Price, SeatsAvailable) " +
                        "VALUES (?,?,?,?,?,?,?,?)";
                PreparedStatement statement = connection.prepareStatement(query);

                statement.setInt(1, flight.getFlightID());
                statement.setString(2, flight.getAirline());
                statement.setString(3, flight.getOrigin());
                statement.setString(4, flight.getDestination());
                statement.setTimestamp(5, flight.getDepartureTime());
                statement.setTimestamp(6, flight.getArrivalTime());
                statement.setFloat(7, flight.getPrice());
                statement.setInt(8, flight.getSeatsAvailable());

                result = statement.executeUpdate();

                statement.close();
            }else System.out.println();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public List<Flight> queryFlightByArriveDateAndDestination(Date arriveDateQuery, String destinationQuery) {
        List<Flight> flightList = new ArrayList<>();
        try {
            PreparedStatement statement = null;
            ResultSet result = null;

            String query = "SELECT * FROM Flight WHERE DATE(ArrivalTime) =  ?  AND Destination = ? ";
            statement = DBUtil.getConnection().prepareStatement(query);

            statement.setDate(1, arriveDateQuery);
            statement.setString(2, destinationQuery);
            result = statement.executeQuery();

            Flight flightResult = null;
            while (result.next()) {
                int flightID = result.getInt("FlightID");
                String airline = result.getString("Airline");
                String origin = result.getString("Origin");
                String destination = result.getString("Destination");
                Timestamp departureTime = result.getTimestamp("DepartureTime");
                Timestamp arrivalTime = result.getTimestamp("ArrivalTime");
                float price = result.getFloat("Price");
                int seatsAvailable = result.getInt("SeatsAvailable");
                flightResult = new Flight(flightID, airline, origin, destination, departureTime, arrivalTime, price, seatsAvailable);
                flightList.add(flightResult);
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        closeDB();
        return flightList;
    }

    /*
    public void queryFlight(Flight flight) {
        Connection connection = null;

        try {
            connection = DBUtil.getConnection();

            PreparedStatement statement = null;
            ResultSet result = null;

            //String query = "SELECT * FROM Employee WHERE Employee.name = ?";
            String query = "SELECT * FROM Flight WHERE DATE(ArrivalTime) = DATE( ? ) AND Destination = ? ";
            statement = connection.prepareStatement(query);

            statement.setString(1, "2023-11-20");
            statement.setString(2, "MUC");
            result = statement.executeQuery();

            Flight flight1 = null;
            while (result.next()) {
                int flightID = result.getInt("FlightID");
                String airline = result.getString("Airline");
                String origin = result.getString("Origin");
                String destination = result.getString("Destination");
                Timestamp departureTime = result.getTimestamp("DepartureTime");
                Timestamp arrivalTime = result.getTimestamp("ArrivalTime");
                float price = result.getFloat("Price");
                int seatsAvailable = result.getInt("SeatsAvailable");
                flight1 = new Flight(flightID, airline, origin, destination, departureTime, arrivalTime, price, seatsAvailable);
                System.out.println(flight1);
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    */
}
