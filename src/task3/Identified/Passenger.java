package task3.Identified;

import task3.IdGenerator;
import task3.IdService;

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
public class Passenger{
    private final Long id;
    private String firstName;
    private String lastName;
    private boolean isBonusEnable;
    private final List<Ticket> tickets = new LinkedList<Ticket>();
    public Passenger(final String firstName, final String lastName, final boolean isBonusEnable, final int flownKilometers) {
        this.id = IdService.createId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.isBonusEnable = isBonusEnable;
    }
    public Passenger(final String firstName, final String lastName, final boolean isBonusEnable) {
        this.id = IdService.createId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.isBonusEnable = isBonusEnable;
    }
    public boolean removeOldTickets(final int howLongSaveTickets){
        final ZonedDateTime currentTime = ZonedDateTime.now();
        return tickets.removeIf(ticket ->
                (ChronoUnit.MONTHS.between(currentTime, ticket.getDepartureTime())>howLongSaveTickets));
    }
    public void deleteTickets(final Passenger defaultPassenger)
    {
        for(final Ticket ticket:tickets)
        {
            ticket.setPassenger(defaultPassenger);
        }
        tickets.clear();
    }
    public void addTicket(final Ticket ticket){
        tickets.add(ticket);
    }

    public List<Ticket> getTickets()
    {
        return this.tickets;
    }
    public Long getId(){
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
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
    public void notifyIfCancelFlight(final Ticket ticket)
    {
        System.out.println("Sorry, we cancel flight on ticket" + ticket.toString());
    }
    public void notifyIfCancelFlight(final Ticket ticket, final double returnPrice)
    {
        System.out.println("Sorry, we cancel flight on ticket \n"
                + ticket.toString() + "\n" +
                " We return: " + returnPrice);
    }
    @Override
    public String toString() {
        return "id: " + this.id +" "+lastName +" " + firstName;
    }
}
