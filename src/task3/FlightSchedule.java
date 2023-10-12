package task3;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
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
    private ScheduleRules scheduleRules;
    private final List<Flight> flights;
    public FlightSchedule() {
        this.scheduleRules = new ScheduleRules();
        this.flights = new LinkedList<>();
    }
    public FlightSchedule(final ScheduleRules scheduleRules) {
        this.scheduleRules = scheduleRules;
        this.flights = new LinkedList<>();
    }
    public boolean removeOldFlights() {
        final ZonedDateTime currentTime = ZonedDateTime.now();
        final int howLongSaveFlights = scheduleRules.getHowManyDaysSaveFlights();
        return flights.removeIf(flight ->
                (ChronoUnit.DAYS.between(currentTime, flight.getDepartureTime()) > howLongSaveFlights));
    }
    public List<Flight> getFreshFlights() {
        final ZonedDateTime currentTime = ZonedDateTime.now();
        return flights.stream()
                .filter(flight -> flight.getDepartureTime().isAfter(currentTime.minusHours(scheduleRules.getHowManyHoursShowFlight())))
                .toList();
    }
    public List<Flight> getFreshDeparturesFlights(){return getFreshFlights().stream().filter(flight -> flight.getDepartureAirport().getFlightSchedule() ==this).toList();
    }
    public List<Flight> getFreshArrivalFlights(){
        return getFreshFlights().stream().filter(flight -> flight.getArrivalAirport().getFlightSchedule() ==this).toList();
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
    public ScheduleRules getScheduleRules() {
        return scheduleRules;
    }
    public void setScheduleRules(final ScheduleRules scheduleRules) {
        this.scheduleRules = scheduleRules;
    }
}