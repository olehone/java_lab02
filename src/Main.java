import task3.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Main {
    public static void main(final String[] args) {
//        //Task 1
//        final Stack<Integer> testIntStack = new Stack<>();
//        final Stack<String> testStrStack = new Stack<>();
//        //=================================================
//        testIntStack.push(1);
//        testIntStack.push(2);
//        testIntStack.push(3);
//        testStrStack.push("with for-each:");
//        testStrStack.push("make stack");
//        testStrStack.push("Task 1:");
//        //=================================================
//        for (final String string: testStrStack){
//            System.out.print(string + " ");
//        }
//        for (final Integer i: testIntStack){
//            System.out.print(i +" ");
//        }
//        System.out.println(testIntStack.pop());
//        //=================================================
//        //Task 2
//        final Circle circle = new Circle(12.5);
//        final Triangle triangle = new Triangle(4,3,5);
//        final Square square = new Square(10);
//        final Pyramid pyramid = new Pyramid(triangle, 10);
//        final Cube cube = new Cube(6);
//        final Sphere sphere = new Sphere(3);
//        //=================================================
//        System.out.println(circle.getArea() + " is circle area");
//        System.out.println(pyramid.getArea() + " is pyramid area");
//        System.out.println(cube.getArea() + " is cube area");
//        System.out.println(square.getArea() + " is square area");
//        System.out.println(triangle.getArea() +" is triangle area");
//        System.out.println(sphere.getArea() + " is sphere area");
//        System.out.println(cube.getVolume() + " is cube volume");
//        System.out.println(pyramid.getVolume() + " is pyramid volume");
        final FlightService service = new FlightService();
        service.addPassenger("Олена", "Іваненко", false);
        service.addPassenger("Андрій", "Коваль", true);
        service.addPassenger("Ірина", "Мельник", false);
        service.addPassenger("Максим", "Сидоренко", true);
        service.addPassenger("Наталія", "Петренко", false);
        service.addPassenger("Олег", "Григоренко", true);
        service.addPassenger("Катерина", "Лисенко", false);
        service.addPassenger("Сергій", "Бондаренко", true);
        service.addPassenger("Вікторія", "Даниленко", false);
        service.addPassenger("Євген", "Кравченко", true);
        service.addPassenger("Анастасія", "Савченко", false);
        service.addPassenger("Михайло", "Кузьмін", true);
        service.addPassenger("Тетяна", "Мороз", false);
        service.addPassenger("Денис", "Гончаренко", true);
        service.addPassenger("Оксана", "Коваленко", false);

        service.addAirport("123 Main St", "Ukraine", "Kyiv", 50.4501, 30.5234, ZoneId.of("Europe/Kiev"));
        service.addAirport("456 Elm St", "United States", "New York", 40.7128, -74.0060, ZoneId.of("America/New_York"));
        service.addAirport("654 Birch St", "Australia", "Sydney", -33.8688, 151.2093, "+11:00");
        service.addAirport("321 Cedar St", "Brazil", "Rio de Janeiro", -22.9083, -43.1964, "-03:00");

        service.addAirCompany("Lufthansa", 950.0, 1100.0, 1400.0, 0.08, 0.22, 0.3, 0.01, 0.0, 0.5, 0.75, 0.90, 0.95, 1.08, 25.0, 120.0, 90.0, 14.5, 35.0, 150.0);
        service.addAirCompany("Delta Air Lines", 1200.0, 1700.0, 2000.0, 0.12, 0.2, 0.6, 0.005, 0.1, 0.70, 0.90, 0.95, 1, 1.12, 15.0, 80.0, 150.0, 26.5, 25.0, 100.0);

        service.addAircraft("Boeing", "737", 150, 20, 10);
        service.addAircraft("Boeing", "747", 110, 30, 16);
        service.addAircraft("Airbus", "A340", 30, 50, 30);

        System.out.println(service.flightsToString());
        System.out.println(service.passengersToString());
        System.out.println(service.aircraftsToString());
        System.out.println(service.airportsToString());
        System.out.println(service.airCompaniesToString());

        final ZonedDateTime arrival1 = ZonedDateTime.of(2023, 10, 15, 8, 30, 0, 0, ZoneId.of("Europe/Kiev"));
        final ZonedDateTime departure1 = ZonedDateTime.of(2023, 10, 15, 12, 45, 0, 0, ZoneId.of("America/New_York"));


//        final Flight flightNewYorkToKyiv = new Flight(departure1, arrival1, newYorkAirport, kyivAirport, boeing737, delta);

        final ZonedDateTime departure2 = ZonedDateTime.of(2023, 10, 15, 11, 15, 0, 0, ZoneId.of("Asia/Tokyo"));
        final ZonedDateTime arrival2 = ZonedDateTime.of(2023, 10, 15, 9, 45, 0, 0, ZoneId.of("America/Toronto"));





    }
}