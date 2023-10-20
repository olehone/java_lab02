package task3;

import task3.Data.Location;
import task3.Data.TicketType;
import task3.Identified.Flight;
import java.time.ZonedDateTime;
import java.util.List;

//Створіть систему управління польотами авіакомпанії. Пропоновані
//класи для ієрархії: літак, аеропорт, пасажир, рейс, розклад польотів,
//квиток.
//Функціональні вимоги:
//1. Створення, видалення, редагування літака
//2. Створення, видалення, редагування рейсу
//3. Створення, видалення, редагування пасажирів
//4. Створення, видалення, редагування айропорта
//5. Відображення розкладів польотів
//6. Створення розкладу польотів
//7. Продаж, скасування квитків
//8. Розрахунок доходів за заданий період часу
public interface FlightStrategy {
    Long addAircraft(String manufacturer, String model, int economySeat, int firstSeat, int businessSeat);
    boolean changeAircraft(Long aircraftId, String manufacturer, String model, Integer economySeat, Integer firstSeat, Integer businessSeat);
    boolean deleteAircraft(Long aircraftId);

    Long addFlight(ZonedDateTime departureTime, ZonedDateTime arrivalTime, Long departureAirportId, Long arrivalAirportId, Long aircraftId, Long AirlinesId);
    boolean changeFlight(Long flightId, ZonedDateTime departureTime, ZonedDateTime arrivalTime, Long departureAirportId, Long arrivalAirportId, Long aircraftId, Long AirlinesId);
    boolean deleteFlight(Long flightId);

    Long addPassenger(String firstName, String lastName, boolean isBonusEnable);
    boolean changePassenger(Long passengerId, String firstName, String lastName, Boolean isBonusEnable);
    boolean deletePassenger(Long passengerId);

    Long addAirport(String strCode, Location location);
    boolean changeAirport(Long airportId, String country, String city, Double latitude, Double longitude, String utsOffset);
    boolean deleteAirport(Long airportId);

    String getSchedules();

    FlightSchedule createSchedule(List<Flight> flights);

    Long sellTicket(Long flightId, Long passengerId, TicketType ticketType);
    boolean cancelTicket(Long ticketId);
    boolean cancelTicket(Long passengerId, Long flightId);

    double getProfitByTime(ZonedDateTime startTime, ZonedDateTime finalTime);
}
