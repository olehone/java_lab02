import task1.*;
import task2.*;
import task3.*;
import task3.Data.TicketType;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Main {
    public static void main(final String[] args) {
  /*      //Task 1
        final Stack<Integer> testIntStack = new Stack<>();
        final Stack<String> testStrStack = new Stack<>();
        //=================================================
        testIntStack.push(1);
        testIntStack.push(2);
        testIntStack.push(3);
        testStrStack.push("with for-each:");
        testStrStack.push("make stack");
        testStrStack.push("Task 1:");
        //=================================================
        for (final String string: testStrStack){
            System.out.print(string + " ");
        }
        for (final Integer i: testIntStack){
            System.out.print(i +" ");
        }
        System.out.println(testIntStack.pop());
        //=================================================
        //Task 2
        final Circle circle = new Circle(12.5);
        final Triangle triangle = new Triangle(4,3,5);
        final Square square = new Square(10);
        final Pyramid pyramid = new Pyramid(triangle, 10);
        final Cube cube = new Cube(6);
        final Sphere sphere = new Sphere(3);
        //=================================================
        System.out.println(circle.getArea() + " is circle area");
        System.out.println(pyramid.getArea() + " is pyramid area");
        System.out.println(cube.getArea() + " is cube area");
        System.out.println(square.getArea() + " is square area");
        System.out.println(triangle.getArea() +" is triangle area");
        System.out.println(sphere.getArea() + " is sphere area");
        System.out.println(cube.getVolume() + " is cube volume");
        System.out.println(pyramid.getVolume() + " is pyramid volume");
*/

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
        System.out.println("Task 3");
        final FlightService service = new FlightService();

        service.addPassenger("Olena", "Ivanko", false);
        service.addPassenger("Andriy", "Koval", true);
        service.addPassenger("Iryna", "Melnyk", false);
        service.addPassenger("Igor", "Petrov", true);
        service.addPassenger("Tetiana", "Sydorenko", false);
        service.addPassenger("Volodymyr", "Boiko", true);
        service.addPassenger("Maria", "Kozachuk", false);
        service.addPassenger("Katerina", "Savchuk", false);
        service.addPassenger("Artem", "Lytvyn", true);
        service.addPassenger("Olha", "Kravets", false);
        service.addPassenger("Roman", "Moroz", true);
        service.addPassenger("Natalia", "Popova", false);
        service.addPassenger("Yurii", "Semenov", true);
        service.addPassenger("Liudmyla", "Maksymenko", false);
        service.addPassenger("Vitalii", "Lysenko", true);
        service.addPassenger("Oleksandr", "Kravchuk", false);


        service.addAirport("KYIV", "Ukraine", "Kyiv", 50.4501, 30.5234, ZoneId.of("+2"));
        service.addAirport("NY","United States", "New York", 40.7128, -74.0060, ZoneId.of("America/New_York"));

        service.addAirCompany("Lufthansa", 950.0, 1150.0, 1400.0, 0.08, 0.2, 0.5, 0.01, 0.001, 0.5, 0.75, 0.90, 0.95, 1.08, 25.0, 120.0, 90.0, 14.5, 35.0, 150.0);
        service.addAirCompany("Delta Air Lines", 1200.0, 1700.0, 2000.0, 0.12, 0.2, 0.6, 0.005, 0.1, 0.70, 0.90, 0.95, 1, 1.12, 15.0, 80.0, 150.0, 26.5, 25.0, 100.0);

        service.addAircraft("Boeing", "747", 110, 30, 16);
        service.addAircraft("Airbus", "A340", 30, 50, 30);

        service.addFlight(ZonedDateTime.of(2023, 11, 2, 14, 30, 0, 0, ZoneId.of("Europe/Kiev")),
                ZonedDateTime.of(2023, 11, 3, 3, 20, 0, 0, ZoneId.of("Europe/Kiev")),
                20L, 21L, 24L, 22L);
        service.addFlight(ZonedDateTime.of(2023, 11, 4, 14, 30, 0, 0, ZoneId.of("Europe/Kiev")),
                ZonedDateTime.of(2023, 11, 5, 3, 20, 0, 0, ZoneId.of("Europe/Kiev")),
                21L, 20L, 24L, 22L);
        service.addFlight(ZonedDateTime.of(2023, 11, 7, 14, 30, 0, 0, ZoneId.of("Europe/Kiev")),
                ZonedDateTime.of(2023, 11, 8, 3, 20, 0, 0, ZoneId.of("Europe/Kiev")),
                20L, 21L, 24L, 23L);
        //Selling tickets
        service.sellTicket(26L, 4L, TicketType.Business);
        service.sellTicket(26L, 5L, TicketType.Economy);
        service.sellTicket(26L, 6L, TicketType.Economy);
        service.sellTicket(26L, 7L, TicketType.Economy);
        service.sellTicket(27L, 8L, TicketType.Business);
        service.sellTicket(27L, 9L, TicketType.Business);
        service.sellTicket(27L, 10L, TicketType.Economy);
        service.sellTicket(27L, 11L, TicketType.Business);
        service.sellTicket(28L, 12L, TicketType.Business);
        service.sellTicket(28L, 13L, TicketType.Economy);

        //=================================================

        System.out.println("Service after add passengers, airports, air companies, aircrafts, flights and sell tickets");
        System.out.println(service.toString());
        System.out.println("Search flights from Ukraine to United States");
        System.out.println(service.getFlightsByCountriesAndTimeToString("Ukraine", "United States",
                ZonedDateTime.of(2023, 10, 7, 0, 0, 0, 0, ZoneId.of("Europe/Kiev")),
                ZonedDateTime.of(2023, 11, 25, 0, 0, 0, 0, ZoneId.of("Europe/Kiev"))
        ));

        //=================================================
        //changes
        service.changeFlight(26L,
                ZonedDateTime.of(2023, 12, 2, 14, 30, 0, 0, ZoneId.of("Europe/Kiev")),
                ZonedDateTime.of(2023, 12, 3, 3, 20, 0, 0, ZoneId.of("Europe/Kiev"))
                , null, null, null, null);
        service.changeAirport(20L, "Change", "Airport", null, null, "+2");
        service.changePassenger(4L, "Change", "Passenger", false);
        service.changeAircraft(24L, "Change", "Aircraft", 100, 120, 12);
        System.out.println("Service after change passenger, airport, aircraft, flight");
        System.out.println(service);

        //=================================================
        //deletes
        service.deleteFlight(26L);
        service.deleteAirport(20L);
        service.deleteAircraft(4L);
        service.deletePassenger(24L);
        System.out.println("Service after delete passenger, airport, aircraft, flight");
        System.out.println(service);

        //Schedule for first and second airport
        System.out.println(service.getScheduleStringByAirportId(21L));

        //Create schedule
        System.out.print("Create new schedule\n");
        final FlightSchedule newSchedule = service.createSchedule(27L,28L);
        System.out.println(newSchedule.getAllFlightsToString());

        //Cancel tickets
        service.cancelTicket(9L, 27L);
        service.cancelTicket(31L);

        //Profit for last month
        System.out.println("Profit for last month");
        System.out.println(service.getProfitByTime(ZonedDateTime.now().minusMonths(1), ZonedDateTime.now()));
        System.out.println("Profit for next 3 month");
        System.out.println(service.getProfitByTime(ZonedDateTime.now(), ZonedDateTime.now().plusMonths(3)));
    }
}