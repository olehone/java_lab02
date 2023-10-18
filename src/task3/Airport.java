package task3;

//Створіть систему управління польотами авіакомпанії. Пропоновані
//        класи для ієрархії: літак, аеропорт, пасажир, рейс, розклад польотів,
//        квиток.
//        Функціональні вимоги:
//        1. Створення, видалення, редагування літака
//        2. Створення, видалення, редагування рейсу
//        3. Створення, видалення, редагування пасажирів
//        4. Створення, видалення, редагування аеропорта
//        5. Відображення розкладів польотів
//        6. Створення розкладу польотів
//        7. Продаж, скасування квитків
//        8. Розрахунок доходів за заданий період часу
public class Airport implements HasId {
    private final static IdGenerator idGenerator = new IdGenerator();
    private final Long id;
    private Location location;
    private FlightSchedule flightSchedule;

    public Airport(final Location location) {
        this.id = idGenerator.createId();
        this.location = location;
        this.flightSchedule = new FlightSchedule();
    }

    public void setFlightSchedule(final FlightSchedule flightSchedule) {
        if (flightSchedule != null)
            this.flightSchedule = flightSchedule;
    }

    public void addFlight(final Flight flight) {
        flightSchedule.addFlight(flight);
    }

    public void removeFlight(final Flight flight) {
        flightSchedule.removeFlight(flight);
    }

    public Long getId() {
        return id;
    }

    public FlightSchedule getFlightSchedule() {
        return flightSchedule;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(final Location location) {
        if (location != null) {
            this.location = location;
        }
    }

    public String schedule() {
        return flightSchedule.toString();
    }

    @Override
    public String toString() {
        return "Airport" +
                " id: " + id +
                "\n location: " + location.toString();
    }
}
