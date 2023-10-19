package task3;

import task3.Identified.Flight;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
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
public class FlightSchedule {
    private final List<Flight> flights;

    public FlightSchedule() {
        this.flights = new LinkedList<>();
    }

    public FlightSchedule(final List<Flight> flights) {
        this.flights = flights;
    }

    public boolean removeOldFlights(final int howManyDaysSaveFlights) {
        final ZonedDateTime currentTime = ZonedDateTime.now();
        return flights.removeIf(flight ->
                (ChronoUnit.DAYS.between(currentTime, flight.getDepartureTime()) > howManyDaysSaveFlights));
    }

    public List<Flight> getFreshFlights() {
        return getFreshFlights(3);
    }

    public List<Flight> getFreshFlights(final int howManyHoursShowFlight) {
        final ZonedDateTime currentTime = ZonedDateTime.now();
        return flights.stream()
                .filter(flight -> flight.getDepartureTime().isAfter(currentTime.minusHours(howManyHoursShowFlight)))
                .toList();
    }

    public List<Flight> getFreshDeparturesFlights() {
        return getFreshDeparturesFlights(3);
    }

    public List<Flight> getFreshArrivalFlights() {
        return getFreshArrivalFlights(3);
    }

    public List<Flight> getFreshDeparturesFlights(final int howManyHoursShowFlight) {
        return getFreshFlights(howManyHoursShowFlight).stream()
                .filter(flight -> flight.getDepartureAirport().getFlightSchedule() == this && flight.isNotCanceled())
                .sorted(Comparator.comparing(Flight::getDepartureTime))
                .toList();
    }

    public List<Flight> getFreshArrivalFlights(final int howManyHoursShowFlight) {
        return getFreshFlights(howManyHoursShowFlight).stream()
                .filter(flight -> flight.getArrivalAirport().getFlightSchedule() == this && flight.isNotCanceled())
                .sorted(Comparator.comparing(Flight::getDepartureTime))
                .toList();
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void addFlight(final Flight flight) {
        this.flights.add(flight);
    }

    public void removeFlight(final Flight flight) {
        this.flights.remove(flight);
    }

    @Override
    public String toString() {
        return toString(3);
    }

    public String toString(final int howManyHoursShowFlight) {
        final StringBuilder str = new StringBuilder();
        str.append("Departures: \n");
        for (final Flight flight : getFreshDeparturesFlights(howManyHoursShowFlight)) {
            str.append(flight.toShortString()).append("\n");
        }
        str.append("Arrivals: \n");
        for (final Flight flight : getFreshArrivalFlights(howManyHoursShowFlight)) {
            str.append(flight.toShortString()).append("\n");
        }
        return str.toString();
    }
}