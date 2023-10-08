package task3;

public class FlightService {
    private static class calculateKilometers {
        private static double bonusToRealKilometersRate = 0.0;
        public static double getBonusToRealKilometersRate() {
            return bonusToRealKilometersRate;
        }
        public static void setBonusToRealKilometersRate(final double bonusToRealKilometersRate) {
            calculateKilometers.bonusToRealKilometersRate = bonusToRealKilometersRate;
        }
        public static int calculateBonusKilometers(final int countOfKilometers){
            return (int) Math.round(countOfKilometers* bonusToRealKilometersRate);
        }

    }
    public static void configuration(){
        //how many months to keep tickets per passenger
        Passenger.setHowLongSaveTickets(60);
        //how many days to keep flights in schedule
        FlightSchedule.setHowLongSaveFlights(10);
        //how many hours show flight in schedule
        FlightSchedule.setHowManyHoursShowFlight(5);
    }
    public static void main(){
        final Passenger passenger1 = new Passenger("Петро", "Вакарчук", true);
        final Passenger passenger2 = new Passenger("Олег", "Миколайчук", false);
        final Passenger passenger3 = new Passenger("Микола", "Степанчук", false);
        final Passenger passenger4 = new Passenger("Василь", "Денисюк", true);





        //========================================
        System.out.println(passenger1.toString());
        System.out.println(passenger2.toString());
        System.out.println(passenger3.toString());
        System.out.println(passenger4.toString());


    }
}

















/*
        private static int countEconomyTickets = 0;
        private static int countCloseEconomyTickets = 0;
        private static int countFirstTickets = 0;
        private static int countCloseFirstTickets = 0;
        private static int countBusinessTickets = 0;
        private static int countCloseBusinessTickets = 0;
        public static int getCountEconomyTickets() {
            return countEconomyTickets;
        }
        public static void addEconomyTicketToCount() {
            Coefficients.countEconomyTickets++;
        }
        public static void addCloseEconomyTicketToCount() {
            Coefficients.countCloseEconomyTickets++;
        }
        public static int getCountCloseEconomyTickets() {
            return countCloseEconomyTickets;
        }
        public static int getCountFirstTickets() {
            return countFirstTickets;
        }
        public static void addFirstTicketToCount() {
            Coefficients.countFirstTickets++;
        }
        public static void addCloseFirstTicketToCount() {
            Coefficients.countCloseFirstTickets++;
        }
        public static int getCountCloseFirstTickets() {
            return countCloseFirstTickets;
        }
        public static int getCountBusinessTickets() {
            return countBusinessTickets;
        }
        public static void addBusinessTicketToCount() {
            Coefficients.countBusinessTickets++;
        }
        public static void addCloseBusinessTicketToCount() {
            Coefficients.countCloseBusinessTickets++;
        }
        public static int getCountCloseBusinessTickets() {
            return countCloseBusinessTickets;
        }
*/