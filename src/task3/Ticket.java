package task3;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;

public class Ticket {
    private static IdGenerator idGenerator;
    private final Long id;
    private HandLuggage handLuggage;
    private Passenger passenger;
    private double price;
    private int seatNumber;
    private boolean isCanceled;
    private TypeOfTicket typeOfTicket;
    private Flight flight;


    public Ticket(final Passenger passenger, final double price, final int setNumber, final TypeOfTicket typeOfTicket, final Flight flight) {
        this.passenger = passenger;
        this.flight = flight;
        this.id = idGenerator.getId();
        this.price = price;
        this.seatNumber = setNumber;
        this.typeOfTicket = typeOfTicket;
        this.isCanceled = false;
        this.handLuggage = new HandLuggage(this);
    }

    public ZonedDateTime getDepartureTime() {
        if (flight == null)
            throw new NullPointerException("Error. Try get ZonedDateTime of null flight from ticket");
        return flight.getDepartureTime();
    }
    public double cancel(){
        return cancel(flight.getAirCompany().getReturnPercentageInLess30Day(),
                flight.getAirCompany().getReturnPercentageInLess10Day(),
                flight.getAirCompany().getReturnPercentageInLess3Day(),
                flight.getAirCompany().getBaseReturnPercentage());
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
        handLuggage.changeSize(0,0,0,0,false, flight.getAirCompany());
        final long hoursTo = ChronoUnit.HOURS.between(ZonedDateTime.now(), departureDateTime);
        isCanceled = true;
        if (hoursTo > 30 * 24) {
            this.price = startPrice * (1-baseReturnPercentage);
            return startPrice*baseReturnPercentage;
        }
        if (hoursTo > 10 * 24) {
            this.price = startPrice * (1 - returnPercentageInLess30Day);
            return startPrice * returnPercentageInLess30Day;
        }
        if (hoursTo > 3 * 24) {
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
        return calculatePrice(ticket.getTypeOfTicket(), ticket.getFlight().getAirCompany().getBaseEconomyCost(), ticket.getFlight().getAirCompany().getBaseFirstCost(), ticket.getFlight().getAirCompany().getBaseBusinessCost(), ticket.getFlight().getCountOfSeats(), ticket.getFlight().getCountOfCanceled(), ticket.getFlight().getAirCompany().getAllowCancelPercentage(), ticket.getFlight().getCountOfLeftTickets(), ticket.getFlight().getAirCompany().getPercentageMarkupForLastTicket(), ticket.getFlight().getAirCompany().getPercentageDiscountIfAllCancel(), (int) ticket.getFlight().getDistance(), ticket.getFlight().getAirCompany().getPricePerKm(), ticket.getPassenger().getFlownKilometers(), ticket.getFlight().getAirCompany().getCoefficientOfFlownKilometers());
    }

    public static double calculatePrice(final Ticket ticket, final Flight flight, final FlownKilometers flownKilometers, final AirCompany airCompany) {
        return calculatePrice(ticket.getTypeOfTicket(), airCompany.getBaseEconomyCost(), airCompany.getBaseFirstCost(), airCompany.getBaseBusinessCost(), flight.getCountOfSeats(), flight.getCountOfCanceled(), airCompany.getAllowCancelPercentage(), flight.getCountOfLeftTickets(), airCompany.getPercentageMarkupForLastTicket(), airCompany.getPercentageDiscountIfAllCancel(), (int) flight.getDistance(), airCompany.getPricePerKm(), flownKilometers, airCompany.getCoefficientOfFlownKilometers());
    }

    public static double calculatePrice(final TypeOfTicket typeOfTicket, final int countOfTickets, final int countOfCanceledTickets, final int countOfLeftTickets, final int countOfKilometers, final FlownKilometers flownKilometers,final AirCompany airCompany) {
        return calculatePrice(typeOfTicket, airCompany.getBaseEconomyCost(), airCompany.getBaseFirstCost(), airCompany.getBaseBusinessCost(), countOfTickets, countOfCanceledTickets, airCompany.getAllowCancelPercentage(), countOfLeftTickets, airCompany.getPercentageMarkupForLastTicket(), airCompany.getPercentageDiscountIfAllCancel(), countOfKilometers, airCompany.getPricePerKm(), flownKilometers, airCompany.getCoefficientOfFlownKilometers());
    }

    public static double calculatePrice(final TypeOfTicket typeOfTicket, final double baseEconomyCost, final double baseFirstCost, final double baseBusinessCost, final int countOfTickets, final int countOfCanceledTickets, final double allowCancelPercentage, final int countOfLeftTickets, final double percentageMarkupForLastTicket, final double percentageDiscountIfAllCancel, final int countOfKilometers, final double pricePerKm, final FlownKilometers flownKilometers, final double coefficientOfFlownKilometers) {
        double price = 0;
        if (flownKilometers.getValue() * coefficientOfFlownKilometers < countOfKilometers)
            price += (countOfKilometers - flownKilometers.getValue() * coefficientOfFlownKilometers) * pricePerKm;
        else {
            flownKilometers.setValue((int) (flownKilometers.getValue() - countOfKilometers / coefficientOfFlownKilometers));
        }
        //base price by class
        switch (typeOfTicket) {
            case economyClass -> price += baseEconomyCost;
            case firstClass -> price += baseFirstCost;
            case businessClass -> price += baseBusinessCost;
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
