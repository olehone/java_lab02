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
public class Passenger {
    public static int howLongSaveTickets = 60; //month

    private final static IdGenerator idGenerator = new IdGenerator();
    private final Long id;
    private String name;
    private String lastName;
    private boolean isBonusEnable;
    private FlownKilometers flownKilometers;
    private final List<Ticket> tickets = new LinkedList<Ticket>();
    public void addTicket(final Ticket ticket){
        tickets.add(ticket);
    }
    public List<Ticket> getTickets()
    {
        return this.tickets;
    }
    public boolean removeOldTickets(){
        final ZonedDateTime currentTime = ZonedDateTime.now();
        return tickets.removeIf(ticket ->
                (ChronoUnit.MONTHS.between(currentTime, ticket.getDepartureTime())>howLongSaveTickets));
    }
    public Passenger(final String name, final String lastName, final boolean isBonusEnable, final int flownKilometers) {
        this.id = idGenerator.getId();
        this.name = name;
        this.lastName = lastName;
        this.isBonusEnable = isBonusEnable;
        this.flownKilometers = new FlownKilometers(flownKilometers);
    }

    public Passenger(final String name, final String lastName, final boolean isBonusEnable) {
        this.id = idGenerator.getId();
        this.name = name;
        this.lastName = lastName;
        this.isBonusEnable = isBonusEnable;
        this.flownKilometers = new FlownKilometers(0);
    }
    public Long getId(){
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(final String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public boolean isBonusEnable() {
        return isBonusEnable;
    }

    public void setBonusEnable(final boolean isBonusEnable) {
        this.isBonusEnable = isBonusEnable;
    }

    public FlownKilometers getFlownKilometers() {
        return flownKilometers;
    }
    public int getCountOfFlownKilometers() {
        return flownKilometers.getValue();
    }
    public void addKilometers(final int flownKilometers){
        if(isBonusEnable)
           this.flownKilometers.addKilometers(flownKilometers);
    }
    public void setFlownKilometers(final int flownKilometers) {
        this.flownKilometers.setValue(flownKilometers);
    }

    @Override
    public String toString() {
        return "Passenger" +
                "\n id: " + this.id +
                "\n initials:  " + lastName +" " + name;
    }

    //in month
    public static void setHowLongSaveTickets(final int howLongSaveTickets) {
        Passenger.howLongSaveTickets = howLongSaveTickets;
    }
}
