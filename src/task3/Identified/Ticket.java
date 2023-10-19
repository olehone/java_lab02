package task3.Identified;

import task3.IdGenerator;
import task3.Data.TicketType;
import task3.IdService;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class Ticket{
    private final Long id;
    private HandLuggage handLuggage;
    private Passenger passenger;
    private double price;
    private boolean isCanceled;
    private TicketType ticketType;
    private Flight flight;


    public Ticket(final Passenger passenger, final TicketType ticketType, final Flight flight) {
        this.id = IdService.createId();
        this.passenger = passenger;
        this.flight = flight;
        this.ticketType = ticketType;
        this.isCanceled = false;
        this.handLuggage = new HandLuggage(this);
        this.price = calculatePrice();
    }

    public ZonedDateTime getDepartureTime() {
        if (flight == null)
            throw new NullPointerException("Error. Try get ZonedDateTime of null flight from ticket");
        return flight.getDepartureTime();
    }

    public void flightCancel() {
        passenger.notifyIfCancelFlight(this);
        price = 0;
        handLuggage.cancel();
    }

    public void flightCancel(final double returnPercentageIfFlightCanceled) {
        double returnPrice = this.price * returnPercentageIfFlightCanceled;
        returnPrice += handLuggage.cancel();
        returnPrice =(int) returnPrice;
        passenger.notifyIfCancelFlight(this, returnPrice);
        this.price -= returnPrice;
    }

    public double cancel() {
        return cancel(flight.getAirCompany().getFlightPrices().getReturnPercentageInLess30Day(),
                flight.getAirCompany().getFlightPrices().getReturnPercentageInLess10Day(),
                flight.getAirCompany().getFlightPrices().getReturnPercentageInLess3Day(),
                flight.getAirCompany().getFlightPrices().getBaseReturnPercentage());
    }

    //outputs how much to return to passenger
    public double cancel(final double returnPercentageInLess3Day, final double returnPercentageInLess10Day, final double returnPercentageInLess30Day, final double baseReturnPercentage) {
        final ZonedDateTime departureDateTime = flight.getDepartureTime();
        final double startPrice = this.price;
        if (isCanceled)
            return 0;
        if (departureDateTime.isAfter(ZonedDateTime.now())) {
            return 0;
        }
        handLuggage.changeSize(0, 0, 0, 0, false, flight.getAirCompany());
        final long hoursTo = ChronoUnit.DAYS.between(ZonedDateTime.now(), departureDateTime);
        isCanceled = true;
        if (hoursTo > 30) {
            this.price = startPrice * (1 - baseReturnPercentage);
            return startPrice * baseReturnPercentage;
        }
        if (hoursTo > 10) {
            this.price = startPrice * (1 - returnPercentageInLess30Day);
            return startPrice * returnPercentageInLess30Day;
        }
        if (hoursTo > 3) {
            this.price = startPrice * (1 - returnPercentageInLess10Day);
            return startPrice * returnPercentageInLess10Day;
        }
        this.price = startPrice * (1 - returnPercentageInLess3Day);
        return startPrice * returnPercentageInLess3Day;
    }

    public double calculatePrice() {
        return flight.calculatePrice(ticketType);
    }



    public TicketType getTicketClass() {
        return ticketType;
    }

    public void setTicketClass(final TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public double getPrice() {
        return price;
    }

    public double getFullPrice() {
        return price + handLuggage.getAdditionalPrice();
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

    public void setHandLuggage(final double weight, final int length, final int width, final int height) {
        this.handLuggage = new HandLuggage(weight, length, width, height, this);
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public String getClassToString() {
        switch (ticketType) {
            case Economy -> {
                return "economy class";
            }
            case First -> {
                return "first class";
            }
            case Business -> {
                return "business class";
            }
            default -> {
                return "Unknown";
            }
        }
    }

    @Override
    public String toString() {
        return "Ticket" +
                " Id: " + id +
                "\n Hand luggage id:" + handLuggage.getId() +
                "\n " + passenger.toString() +
                "\n Price: " + price +
                "\n Class: " + getClassToString();
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(final Flight flight) {
        this.flight = flight;
    }
}
