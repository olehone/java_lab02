import task3.FlownKilometers;
import task3.Ticket;
import task3.TicketClass;

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


        task3.FlightService.main();
        final FlownKilometers test = new FlownKilometers(12000);
        final TicketClass type = TicketClass.Economy;
        System.out.println(test);
        System.out.println(test.getValue());
        final double price = Ticket.calculatePrice(type, 1000, 1300, 1500,
                50, 2, 0.01, 10, 0.01, 0.02,
                2412, 0.0023, test, 0.5);
        System.out.println(price);
        System.out.println(test.getValue());


    }
}