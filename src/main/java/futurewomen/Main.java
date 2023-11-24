package futurewomen;

import futurewomen.booking.BookingDao;
import futurewomen.flight.Flight;
import futurewomen.flight.FlightDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Flight flight = new Flight(7, "Cebu Pacific Air",
                "CGY", "MNL",
                Timestamp.valueOf("2023-11-21 09:50:00"),
                Timestamp.valueOf("2023-11-21 11:35:00"),
                80.00f, 30);
        FlightDao flightDao = new FlightDao();
        flightDao.insertFlightToDB(flight);
        System.out.println("-------------------------------------");

        List<Flight> flightList = new ArrayList<>();
        flightList = flightDao.queryFlightByArriveDateAndDestination(
                Date.valueOf("2023-11-20"),
                "MNL");
        System.out.println(flightList.size());
        flightList.forEach(System.out::println);

        System.out.println("-------------------------------------");
        BookingDao bookingDao = new BookingDao();
        bookingDao.queryCustomersWithBookings();

        System.out.println("-------------------------------------");
        bookingDao.queryBookingsByCustomer(1);
        bookingDao.queryBookingsByCustomer(2);
        bookingDao.queryBookingsByCustomer(3);

        System.out.println("-------------------------------------");
        //Happy path:
        System.out.println("Is booking success: "+ bookingDao.insertBookingToDB(4, 3, Timestamp.valueOf("2023-01-02 12:00:00"), 10));

        //Simulating not enough seats on flight
        System.out.println("Is booking success: "+bookingDao.insertBookingToDB(4, 3, Timestamp.valueOf("2023-01-03 12:00:00"), 25));

        //simulating flight id doesn't exist
        System.out.println("Is booking success: "+ bookingDao.insertBookingToDB(20, 3, Timestamp.valueOf("2023-01-02 12:00:00"), 10));
        bookingDao.queryBookingsByCustomer(3);
    }
}