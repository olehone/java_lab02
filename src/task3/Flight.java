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

    public void setCanceled(final boolean canceled) {
        isCanceled = canceled;
    }
    public double getNowTicketPrice(final AirCompany airCompany, final Ticket ticket )
    {

    }

}
