package futurewomen.flight;

import java.sql.Timestamp;
import java.util.Date;

public class Flight {
    private int flightID;

    public int getFlightID() {
        return this.flightID;
    }

    public void setFlightID(int value) {
        this.flightID = value;
    }

    private String airline;

    public String getAirline() {
        return this.airline;
    }

    public void setAirline(String value) {
        this.airline = value;
    }

    private String origin;

    public String getOrigin() {
        return this.origin;
    }

    public void setOrigin(String value) {
        this.origin = value;
    }

    private String destination;

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String value) {
        this.destination = value;
    }

    private int seatsAvailable;

    public int getSeatsAvailable() {
        return this.seatsAvailable;
    }

    public void setSeatsAvailable(int value) {
        this.seatsAvailable = value;
    }

    private Timestamp departureTime;
    private Timestamp arrivalTime;
    private float price;

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Timestamp arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Flight(int flightID, String airline, String origin,
                  String destination, Timestamp departureTime, Timestamp arrivalTime,
                  float price, int seatsAvailable) {
        this.flightID = flightID;
        this.airline = airline;
        this.origin = origin;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.destination = destination;
        this.seatsAvailable = seatsAvailable;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightID=" + flightID +
                ", airline='" + airline + '\'' +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", seatsAvailable=" + seatsAvailable +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", price=" + price +
                '}';
    }
}