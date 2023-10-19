package task3;

import task3.Identified.Flight;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Flight> getFlights() {
        return flights;
    }

    public void addFlight(final Flight flight) {
        this.flights.add(flight);
    }

    public void removeFlight(final Flight flight) {
        this.flights.remove(flight);
    }

    public List<Flight> getFreshFlights() {
        final LocalDate today = LocalDate.now();
        return flights.stream()
                .filter(flight -> flight.getDepartureTime().toLocalDate().isEqual(today)
                        || flight.getDepartureTime().toLocalDate().isAfter(today))
                .sorted(Comparator.comparing(Flight::getDepartureTime))
                .toList();
    }

    public List<Flight> getFreshDeparturesFlights() {
        return getFreshFlights().stream()
                .filter(flight -> flight.getDepartureAirport().getFlightSchedule() == this && flight.isNotCanceled())
                .toList();
    }

    public List<Flight> getFreshArrivalFlights() {
        return getFreshFlights().stream()
                .filter(flight -> flight.getArrivalAirport().getFlightSchedule() == this && flight.isNotCanceled())
                .toList();
    }
    public String getAllFlightsToString(){
        return flights.stream().map(Flight::toShortString).collect(Collectors.joining("\n"));
    }
    @Override
    public String toString() {
        final List<Flight> departuresFlights = getFreshDeparturesFlights();
        final List<Flight> arrivalFlights = getFreshArrivalFlights();
        final String departuresString = departuresFlights.stream().map(Flight::toShortString).collect(Collectors.joining("\n"));
        final String arrivalString = arrivalFlights.stream().map(Flight::toShortString).collect(Collectors.joining("\n"));
        return "Departures \n" +
                departuresString +
                "\nArrivals\n" +
                arrivalString;
    }
}