package task3;

import java.time.ZonedDateTime;

public class Ticket {
    private static IdGenerator idGenerator;
    private final Long id;
    private HandLuggage handLuggage;
    private Passenger passenger;
    private double price;
    private int seatNumber;
    private TypeOfTicket typeOfTicket;
    private Flight flight;
    public Ticket(final Passenger passenger, final double price, final int setNumber, final TypeOfTicket typeOfTicket, final Flight flight) {
        this.passenger = passenger;
        this.flight = flight;
        this.id = idGenerator.getId();
        this.price = price;
        this.seatNumber = setNumber;
        this.typeOfTicket = typeOfTicket;
        this.handLuggage = new HandLuggage(this);
    }
    public ZonedDateTime getDepartureTime() {
        if (flight == null)
            throw new NullPointerException("Error. Try get ZonedDateTime of null flight from ticket");
        return flight.getDepartureTime();
    }
    public TypeOfTicket getTypeOfTicket() {
        return typeOfTicket;
    }

    public void setTypeOfTicket(final TypeOfTicket typeOfTicket) {
        this.typeOfTicket = typeOfTicket;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(final int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public void addPrice(final double price) {
        this.price += price;
    }

    public Long getId() {
        return id;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(final Passenger passenger) {
        this.passenger = passenger;
    }

    public HandLuggage getHandLuggage() {
        return handLuggage;
    }

    public void setHandLuggage(final HandLuggage handLuggage) {
        this.handLuggage = handLuggage;
    }

    public String getTypeToString() {
        switch (typeOfTicket) {
            case economyClass -> {
                return "economy class";
            }
            case firstClass -> {
                return "first class";
            }
            case businessClass -> {
                return "business class";
            }
        }
        //FIXME make exception?
        return "no type";
    }

    @Override
    public String toString() {
        return "Ticket" +
                " Id: " + id +
                "\n Hand luggage id:" + handLuggage.getId() +
                "\n " + passenger.toString() +
                "\n Price: " + price +
                "\n Seat number: " + seatNumber +
                "\n Type of ticket: " + getTypeToString();
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(final Flight flight) {
        this.flight = flight;
    }
}
