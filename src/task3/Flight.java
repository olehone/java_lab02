package task3;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

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
public class Flight implements HasId {
    final private static IdGenerator idGenerator = new IdGenerator();
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
    private AirCompany airCompany;

    public Flight(final ZonedDateTime departureTime, final ZonedDateTime arrivalTime, final Airport departureAirport, final Airport arrivalAirport, final Aircraft aircraft, final AirCompany airCompany) {
        this.id = idGenerator.createId();
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.aircraft = aircraft;
        this.economySeat = aircraft.getEconomySeat();
        this.firstSeat = aircraft.getFirstSeat();
        this.businessSeat = aircraft.getBusinessSeat();
        this.airCompany = airCompany;
        this.tickets = new LinkedList<>();
    }

    public Flight(final ZonedDateTime departureTime, final ZonedDateTime arrivalTime, final Airport departureAirport, final Airport arrivalAirport, final Aircraft aircraft, final int economySeat, final int firstSeat, final int businessSeat, final boolean isCanceled, final AirCompany airCompany) {
        this.id = idGenerator.createId();
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.aircraft = aircraft;
        this.economySeat = economySeat;
        this.firstSeat = firstSeat;
        this.businessSeat = businessSeat;
        this.isCanceled = isCanceled;
        this.airCompany = airCompany;
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

    public boolean addPassenger(final TicketClass ticketClass, final Passenger passenger) {
        if (!canAddPassenger(ticketClass, passenger))
            return false;
        final Ticket newTicket = new Ticket(passenger, ticketClass, this);
        passenger.addTicket(newTicket);
        this.addTicket(newTicket);
        return true;
    }

    //only if one passenger can have one seat
    public boolean canAddPassenger(final TicketClass ticketClass, final Passenger passenger) {
        if (getCountOfLeftTicketsByClass(ticketClass) <= 0)
            return false;
        if (tickets.stream().anyMatch(ticket ->
                passenger.equals(ticket.getPassenger())
        ))
            return false;
        return true;
    }

    public String getSeatsInfo() {
        final int leftEcoTickets = getCountOfLeftTicketsByClass(TicketClass.Economy);
        final int leftFirstTickets = getCountOfLeftTicketsByClass(TicketClass.First);
        final int leftBusinessTickets = getCountOfLeftTicketsByClass(TicketClass.Business);

        final String format = "%-8s| %5s| %9s| %6s| %12s| %9s |\n";
        final String header = String.format(format, "Class", "Free", "Occupied", "Total", "Price", "Profit");
        final String divider = "-----------------------------------------------------------\n";
        final String ecoRow = String.format(format, "Economy", leftEcoTickets, (economySeat - leftEcoTickets), economySeat, Ticket.calculatePrice(TicketClass.Economy, this, airCompany.getFlightPrices()), calculateProfitByClass(TicketClass.Economy));
        final String firstRow = String.format(format, "First", leftFirstTickets, (firstSeat - leftFirstTickets), firstSeat, Ticket.calculatePrice(TicketClass.First, this, airCompany.getFlightPrices()), calculateProfitByClass(TicketClass.First));
        final String businessRow = String.format(format, "Business", leftBusinessTickets, (businessSeat - leftBusinessTickets), businessSeat, Ticket.calculatePrice(TicketClass.Business, this, airCompany.getFlightPrices()), calculateProfitByClass(TicketClass.Business));
        final String totalRow = String.format(format, "Total", getCountOfLeftTickets(), (economySeat - leftEcoTickets + firstSeat - leftFirstTickets + businessSeat - leftBusinessTickets), (economySeat + firstSeat + businessSeat), "", calculateProfit());

        return header + divider + ecoRow + firstRow + businessRow + divider + totalRow;
    }


    public double calculateProfit() {
        double profit = 0;
        for (final Ticket ticket : tickets) {
            profit += ticket.getFullPrice();
        }
        return (int) profit;
    }

    public double calculateProfitByClass(final TicketClass ticketClass) {
        double profit = 0;
        for (final Ticket ticket : tickets) {
            if (ticket.getTicketClass() == ticketClass)
                profit += ticket.getFullPrice();
        }
        return (int) profit;
    }

    public void cancel() {
        final double returnPercentageIfFlightCanceled = airCompany.getFlightPrices().getReturnPercentageIfFlightCanceled();
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

    public int getCountOfSeatsByClass(final TicketClass ticketClass) {
        switch (ticketClass) {
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

    public int getCountOfLeftTicketsByClass(final TicketClass ticketClass) {
        int count = 0;
        for (final Ticket ticket : tickets) {
            if (ticket.isCanceled())
                continue;
            if (ticket.getTicketClass() == ticketClass)
                count++;
        }
        return this.getCountOfSeatsByClass(ticketClass) - count;
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
        return Location.calculateDistanceByCoordinates(arrivalAirport.getLocation().getLatitude(),
                arrivalAirport.getLocation().getLongitude(),
                departureAirport.getLocation().getLatitude(),
                departureAirport.getLocation().getLongitude());
    }

    public AirCompany getAirCompany() {
        return airCompany;
    }

    public void setAirCompany(final AirCompany airCompany) {
        this.airCompany = airCompany;
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
                " " + airCompany.toShortString() +
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
                "\n Air company " + airCompany.toString() +
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
