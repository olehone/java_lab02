package task3;

import java.time.ZonedDateTime;
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
public class Flight {
    private static IdGenerator idGenerator;
    private Long id;
    private ZonedDateTime departureTime;
    private ZonedDateTime arrivalTime;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private Aircraft aircraft;
    private boolean isCanceled;
    private List<Ticket> tickets;
    private AirCompany airCompany;


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

    public boolean isCanceled() {
        return isCanceled;
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
        if (getCountOfLeftTicketsByClass(ticketClass)<=0)
            return false;
        final Ticket newTicket = new Ticket(passenger,ticketClass,this);
        passenger.addTicket(newTicket);
        this.addTicket(newTicket);
        return true;
    }

    public String getSeatsCountToString() {
        final int leftEcoTickets = getCountOfLeftTicketsByClass(TicketClass.Economy);
        final int leftFirstTickets = getCountOfLeftTicketsByClass(TicketClass.First);
        final int leftBusinessTickets = getCountOfLeftTicketsByClass(TicketClass.Business);
        final int ecoSeats = aircraft.getEconomySeat();
        final int firstSeats = aircraft.getFirstSeat();
        final int businessSeats = aircraft.getBusinessSeat();
        return "Flight " + id +
                "\n Class | Free | Occupied | Total | Base price |" +
                "\n   Eco |   " + leftEcoTickets + " |       " + (ecoSeats - leftEcoTickets) + " |    " + ecoSeats + " | " + Ticket.calculatePrice(TicketClass.Economy, this, airCompany.getFlightPrices()) + " |" +
                "\n First |   " + leftFirstTickets + " |       " + (firstSeats - leftFirstTickets) + " |    " + firstSeats + " |" + Ticket.calculatePrice(TicketClass.First, this, airCompany.getFlightPrices()) + " |" +
                "\n  Buss |   " + leftBusinessTickets + " |       " + (businessSeats - leftBusinessTickets) + " |    " + businessSeats + " |" + Ticket.calculatePrice(TicketClass.Business, this, airCompany.getFlightPrices()) + " |\n" +
                "\n Total |   " + getCountOfLeftTickets() + " |       " + (ecoSeats - leftEcoTickets + firstSeats - leftFirstTickets + businessSeats - leftBusinessTickets) + " |    " + (ecoSeats + firstSeats + businessSeats) + " |\n";
    }

    public void cancel() {
        for (final Ticket ticket : tickets) {
            if (ticket != null)
                ticket.flightCancel();
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
        return aircraft.getEconomySeat() + aircraft.getFirstSeat() + aircraft.getBusinessSeat();
    }

    public int getCountOfSeatsByClass(final TicketClass ticketClass) {
        switch (ticketClass) {
            case Economy -> {
                return aircraft.getEconomySeat();
            }
            case First -> {
                return aircraft.getFirstSeat();
            }
            case Business ->{
                return aircraft.getBusinessSeat();}
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

    public double getNowTicketPrice(final AirCompany airCompany, final Ticket ticket) {
        return 0;
    }
}
