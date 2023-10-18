package task3;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;

public class Ticket implements HasId {
    private static final IdGenerator idGenerator = new IdGenerator();
    private final Long id;
    private HandLuggage handLuggage;
    private Passenger passenger;
    private double price;
    private boolean isCanceled;
    private TicketClass ticketClass;
    private Flight flight;


    public Ticket(final Passenger passenger, final TicketClass ticketClass, final Flight flight) {
        this.id = idGenerator.createId();
        this.passenger = passenger;
        this.flight = flight;
        this.ticketClass = ticketClass;
        this.isCanceled = false;
        this.handLuggage = new HandLuggage(this);
        this.price = calculatePrice();
    }

    public ZonedDateTime getDepartureTime() {
        if (flight == null)
            throw new NullPointerException("Error. Try get ZonedDateTime of null flight from ticket");
        return flight.getDepartureTime();
    }
    public void flightCancel(){
        passenger.notifyIfCancelFlight(this);
        price = 0;
        handLuggage.cancel();
    }
    public void flightCancel(final double returnPercentageIfFlightCanceled){
        double returnPrice = this.price * returnPercentageIfFlightCanceled;
        returnPrice+=  handLuggage.cancel();
        passenger.notifyIfCancelFlight(this, returnPrice);
        this.price -= returnPrice ;
    }
    public double cancel(){
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
        return calculatePrice(this);
    }

    public static double calculatePrice(final Ticket ticket) {
        final FlightPrices flightPrices= ticket.getFlight().getAirCompany().getFlightPrices();
        return calculatePrice(ticket.getTicketClass(), flightPrices.getBaseEconomyCost(), flightPrices.getBaseFirstCost(), flightPrices.getBaseBusinessCost(), ticket.getFlight().getCountOfSeats(), ticket.getFlight().getCountOfCanceled(), flightPrices.getAllowCancelPercentage(), ticket.getFlight().getCountOfLeftTickets(), flightPrices.getPercentageMarkupForLastTicket(), flightPrices.getPercentageDiscountIfAllCancel(), (int) ticket.getFlight().getDistance(), flightPrices.getPricePerKm(), ticket.getPassenger().getFlownKilometers(), flightPrices.getCoefficientOfFlownKilometers());
    }

    public static double calculatePrice(final Ticket ticket, final Flight flight, final FlownKilometers flownKilometers, final FlightPrices flightPrices ) {
        return calculatePrice(ticket.getTicketClass(),flightPrices.getBaseEconomyCost(), flightPrices.getBaseFirstCost(), flightPrices.getBaseBusinessCost(), flight.getCountOfSeats(), flight.getCountOfCanceled(), flightPrices.getAllowCancelPercentage(), flight.getCountOfLeftTickets(), flightPrices.getPercentageMarkupForLastTicket(), flightPrices.getPercentageDiscountIfAllCancel(), (int) flight.getDistance(), flightPrices.getPricePerKm(), flownKilometers, flightPrices.getCoefficientOfFlownKilometers());
    }
    public static double calculatePrice(final TicketClass ticketClass, final Flight flight, final FlightPrices flightPrices ) {
        return calculatePrice(ticketClass,flightPrices.getBaseEconomyCost(), flightPrices.getBaseFirstCost(), flightPrices.getBaseBusinessCost(), flight.getCountOfSeats(), flight.getCountOfCanceled(), flightPrices.getAllowCancelPercentage(), flight.getCountOfLeftTickets(), flightPrices.getPercentageMarkupForLastTicket(), flightPrices.getPercentageDiscountIfAllCancel(), (int) flight.getDistance(), flightPrices.getPricePerKm(), new FlownKilometers(0), flightPrices.getCoefficientOfFlownKilometers());
    }
    public static double calculatePrice(final TicketClass ticketClass, final double baseEconomyCost, final double baseFirstCost, final double baseBusinessCost, final int countOfTickets, final int countOfCanceledTickets, final double allowCancelPercentage, final int countOfLeftTickets, final double percentageMarkupForLastTicket, final double percentageDiscountIfAllCancel, final int countOfKilometers, final double pricePerKm, final FlownKilometers flownKilometers, final double coefficientOfFlownKilometers) {
        double price = 0;
        if (flownKilometers.getValue() * coefficientOfFlownKilometers < countOfKilometers)
            price += (countOfKilometers - flownKilometers.getValue() * coefficientOfFlownKilometers) * pricePerKm;
        else {
            flownKilometers.setValue((int) (flownKilometers.getValue() - countOfKilometers / coefficientOfFlownKilometers));
        }
        //base price by class
        switch (ticketClass) {
            case Economy -> price += baseEconomyCost;
            case First -> price += baseFirstCost;
            case Business -> price += baseBusinessCost;
            default -> throw new NoSuchElementException("No type of ticket in calculating price");
        }
        //the fewer tickets, the higher the price only if not many cancels
        if ((double) countOfCanceledTickets / countOfTickets < allowCancelPercentage && countOfLeftTickets < countOfTickets) {
            price *= (1.00 - (double) countOfLeftTickets / countOfTickets) * percentageMarkupForLastTicket;
        }
        //the more canceled tickets, the bigger the discount
        if (countOfCanceledTickets > 0 && countOfCanceledTickets < countOfTickets)
            price /= (1.00 - (double) countOfCanceledTickets / countOfTickets) * percentageDiscountIfAllCancel;
        return price;
    }

    public TicketClass getTicketClass() {
        return ticketClass;
    }

    public void setTicketClass(final TicketClass ticketClass) {
        this.ticketClass = ticketClass;
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
        switch (ticketClass) {
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
