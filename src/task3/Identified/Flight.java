package task3.Identified;

import task3.*;
import task3.Data.TicketType;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

//Створіть систему управління польотами авіакомпанії. Пропоновані
//        класи для ієрархії: літак, аеропорт, пасажир, рейс, розклад польотів,
//        квиток.
//        Функціональні вимоги:
//        1. Створення, видалення, редагування літака
//        2. Створення, видалення, редагування рейсу
//        3. Створення, видалення, редагування пасажирів
//        4. Створення, видалення, редагування айропорта
//        5. Відображення розкладів польотів
//        6. Створення розкладу польотів
//        7. Продаж, скасування квитків
//        8. Розрахунок доходів за заданий період часу
public class Flight{
    private final Long id;
    private ZonedDateTime departureTime;
    private ZonedDateTime arrivalTime;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private Aircraft aircraft;
    private int economySeat;
    private int firstSeat;
    private int businessSeat;
    private boolean isCanceled;
    final private List<Ticket> tickets;
    private Airline airline;

    public Flight(final ZonedDateTime departureTime, final ZonedDateTime arrivalTime, final Airport departureAirport, final Airport arrivalAirport, final Aircraft aircraft, final Airline airline) {
        this.id = IdService.createId();
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.aircraft = aircraft;
        this.economySeat = aircraft.getEconomySeat();
        this.firstSeat = aircraft.getFirstSeat();
        this.businessSeat = aircraft.getBusinessSeat();
        this.airline = airline;
        this.tickets = new LinkedList<>();
    }

    public Flight(final ZonedDateTime departureTime, final ZonedDateTime arrivalTime, final Airport departureAirport, final Airport arrivalAirport, final Aircraft aircraft, final int economySeat, final int firstSeat, final int businessSeat, final boolean isCanceled, final Airline airline) {
        this.id = IdService.createId();
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.aircraft = aircraft;
        this.economySeat = economySeat;
        this.firstSeat = firstSeat;
        this.businessSeat = businessSeat;
        this.isCanceled = isCanceled;
        this.airline = airline;
        this.tickets = new LinkedList<>();
    }

    public int getEconomySeat() {
        return economySeat;
    }

    public void setEconomySeat(final int economySeat) {
        this.economySeat = economySeat;
    }

    public int getFirstSeat() {
        return firstSeat;
    }

    public void setFirstSeat(final int firstSeat) {
        this.firstSeat = firstSeat;
    }

    public int getBusinessSeat() {
        return businessSeat;
    }

    public void setBusinessSeat(final int businessSeat) {
        this.businessSeat = businessSeat;
    }

    public Long getId() {
        return id;
    }

    public ZonedDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(final ZonedDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public ZonedDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(final ZonedDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(final Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(final Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public boolean isNotCanceled() {
        return !isCanceled;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(final Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void addTicket(final Ticket ticket) {
        tickets.add(ticket);
    }

    public boolean addPassenger(final TicketType ticketType, final Passenger passenger) {
        if (!canAddPassenger(ticketType, passenger))
            return false;
        final Ticket newTicket = new Ticket(passenger, ticketType, this);
        passenger.addTicket(newTicket);
        this.addTicket(newTicket);
        return true;
    }

    //only if one passenger can have one seat
    public boolean canAddPassenger(final TicketType ticketType, final Passenger passenger) {
        if (getCountOfLeftTicketsByClass(ticketType) <= 0)
            return false;
        if (tickets.stream().anyMatch(ticket ->
                passenger.equals(ticket.getPassenger())
        ))
            return false;
        return true;
    }

    public String getSeatsInfo() {
        final int leftEcoTickets = getCountOfLeftTicketsByClass(TicketType.Economy);
        final int leftFirstTickets = getCountOfLeftTicketsByClass(TicketType.First);
        final int leftBusinessTickets = getCountOfLeftTicketsByClass(TicketType.Business);

        final String format = "%-8s| %5s| %9s| %6s| %12s| %9s |\n";
        final String header = String.format(format, "Class", "Free", "Occupied", "Total", "Price to buy", "Profit");
        final String divider = "-----------------------------------------------------------\n";
        final String ecoRow = String.format(format, "Economy", leftEcoTickets, (economySeat - leftEcoTickets), economySeat, calculatePrice(TicketType.Economy), calculateProfitByClass(TicketType.Economy));
        final String firstRow = String.format(format, "First", leftFirstTickets, (firstSeat - leftFirstTickets), firstSeat, calculatePrice(TicketType.First), calculateProfitByClass(TicketType.First));
        final String businessRow = String.format(format, "Business", leftBusinessTickets, (businessSeat - leftBusinessTickets), businessSeat, calculatePrice(TicketType.Business), calculateProfitByClass(TicketType.Business));
        final String totalRow = String.format(format, "Total", getCountOfLeftTickets(), (economySeat - leftEcoTickets + firstSeat - leftFirstTickets + businessSeat - leftBusinessTickets), (economySeat + firstSeat + businessSeat), "", calculateProfit());

        return header + divider + ecoRow + firstRow + businessRow + divider + totalRow;
    }

    public double calculatePrice(final TicketType ticketType) {
        double price = 0;
        final double baseEconomyCost = airline.getFlightPrices().getBaseEconomyCost();
        final double baseFirstCost = airline.getFlightPrices().getBaseFirstCost();
        final double baseBusinessCost = airline.getFlightPrices().getBaseBusinessCost();
        final double countOfCanceledTickets = getCountOfCanceled();
        final double countOfSeats =getCountOfSeats();
        final double allowCancelPercentage = airline.getFlightPrices().getAllowCancelPercentage();
        final double countOfLeftTickets = getCountOfLeftTickets();
        final double percentageMarkupForLastTicket = airline.getFlightPrices().getPercentageMarkupForLastTicket();
        final double percentageDiscountIfAllCancel = airline.getFlightPrices().getPercentageDiscountIfAllCancel();
        //base price by class
        switch (ticketType) {
            case Economy -> price += baseEconomyCost;
            case First -> price += baseFirstCost;
            case Business -> price += baseBusinessCost;
            default -> throw new NoSuchElementException("No type of ticket in calculating price");
        }
        //the fewer tickets, the higher the price only if not many cancels
        if ((double) countOfCanceledTickets / countOfSeats < allowCancelPercentage && countOfLeftTickets < countOfSeats) {
            price *= ((1.00 - (double) countOfLeftTickets / countOfSeats) * percentageMarkupForLastTicket)+1;
        }
        //the more canceled tickets, the bigger the discount
        if (countOfCanceledTickets > 0 && countOfCanceledTickets < countOfSeats) {
            price /= (1.00 - (double) countOfCanceledTickets / countOfSeats) * percentageDiscountIfAllCancel;
        }
        return (int) price;
    }
    public double calculateProfit() {
        double profit = 0;
        for (final Ticket ticket : tickets) {
            profit += ticket.getFullPrice();
        }
        return (int) profit;
    }

    public double calculateProfitByClass(final TicketType ticketType) {
        double profit = 0;
        for (final Ticket ticket : tickets) {
            if (ticket.getTicketClass() == ticketType)
                profit += ticket.getFullPrice();
        }
        return (int) profit;
    }

    public void cancel() {
        final double returnPercentageIfFlightCanceled = airline.getFlightPrices().getReturnPercentageIfFlightCanceled();
        for (final Ticket ticket : tickets) {
            if (ticket != null)
                ticket.flightCancel(returnPercentageIfFlightCanceled);
        }
        isCanceled = true;
    }

    public int getCountOfCanceled() {
        int count = 0;
        for (final Ticket ticket : tickets) {
            if (ticket.isCanceled())
                count++;
        }
        return count;
    }

    public int getCountOfSeats() {
        return economySeat + firstSeat + businessSeat;
    }

    public int getCountOfSeatsByClass(final TicketType ticketType) {
        switch (ticketType) {
            case Economy -> {
                return economySeat;
            }
            case First -> {
                return firstSeat;
            }
            case Business -> {
                return businessSeat;
            }
            default -> {
                return 0;
            }
        }
    }

    public int getCountOfLeftTickets() {
        int count = 0;
        for (final Ticket ticket : tickets) {
            if (!ticket.isCanceled())
                count++;
        }
        return this.getCountOfSeats() - count;
    }

    public int getCountOfLeftTicketsByClass(final TicketType ticketType) {
        int count = 0;
        for (final Ticket ticket : tickets) {
            if (ticket.isCanceled())
                continue;
            if (ticket.getTicketClass() == ticketType)
                count++;
        }
        return this.getCountOfSeatsByClass(ticketType) - count;
    }

    public int getCountOfOccupiedTickets() {
        int count = 0;
        for (final Ticket ticket : tickets) {
            if (!ticket.isCanceled())
                count++;
        }
        return getCountOfCanceled() - count;
    }

    public double getDistance() {
        if (arrivalAirport == null || departureAirport == null)
            return 0;
        return CalculateDistance.calculateDistanceByCoordinates(arrivalAirport.getLocation().getLatitude(),
                arrivalAirport.getLocation().getLongitude(),
                departureAirport.getLocation().getLatitude(),
                departureAirport.getLocation().getLongitude());
    }

    public Airline getAirlines() {
        return airline;
    }

    public void setAirlines(final Airline airline) {
        this.airline = airline;
    }

    public String toShortString() {
        String str = "";
        if (isCanceled) {
            str += "CANCELED ";
        }
        str += id +
                " " + departureTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")) +
                " " + departureAirport.toShortString() +
                "-" + arrivalAirport.toShortString() +
                " " + arrivalTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")) +
                " " + airline.toShortString() +
                " " + aircraft.toShortString();

        return str;
    }

    @Override
    public String toString() {
        String str = "";
        if (isCanceled) {
            str += "CANCELED ";
        }
        str += "flight id: " + id +
                "\n Airline " + airline.toString() +
                "\n Aircraft " + aircraft.toString() +
                "\n\n DEPARTURE  " +
                "\n Airport: " + departureAirport.toString() +
                "\n Time: " + departureTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")) +
                "\n\n ARRIVAL " +
                "\n Airport: " + arrivalAirport.toString() +
                "\n Time: " + arrivalTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"))+
                "\n \n"+ getSeatsInfo();

        return str;
    }
}
