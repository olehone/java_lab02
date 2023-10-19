package task3;

import task3.Data.TicketClass;
import task3.Identified.Flight;
import task3.Identified.Ticket;
import task3.Data.FlownKilometers;
import task3.Rules.FlightRules;

import java.util.NoSuchElementException;

public class Calculations {
    public static double calculateDistanceByCoordinates(final double latitude1, final double longitude1, final double latitude2, final double longitude2) {
        final double radiusOfEarth = 6371;

        final double latitude1Rad = Math.toRadians(latitude1);
        final double longitude1Rad = Math.toRadians(longitude1);
        final double latitude2Rad = Math.toRadians(latitude2);
        final double longitude2Rad = Math.toRadians(longitude2);

        final double differenceLatitude = latitude2Rad - latitude1Rad;
        final double differenceLongitude = longitude2Rad - longitude1Rad;

        final double a = Math.pow(Math.sin(differenceLatitude / 2), 2) + Math.cos(latitude1Rad)
                * Math.cos(latitude2Rad) * Math.pow(Math.sin(differenceLongitude / 2), 2);
        final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return radiusOfEarth * c;
    }
    public static double calculatePrice(final Ticket ticket) {
        final FlightRules flightRules = ticket.getFlight().getAirCompany().getFlightPrices();
        return Calculations.calculatePrice(ticket.getTicketClass(), flightRules.getBaseEconomyCost(), flightRules.getBaseFirstCost(), flightRules.getBaseBusinessCost(), ticket.getFlight().getCountOfSeats(), ticket.getFlight().getCountOfCanceled(), flightRules.getAllowCancelPercentage(), ticket.getFlight().getCountOfLeftTickets(), flightRules.getPercentageMarkupForLastTicket(), flightRules.getPercentageDiscountIfAllCancel(), (int) ticket.getFlight().getDistance(), flightRules.getPricePerKm(), ticket.getPassenger().getFlownKilometers(), flightRules.getCoefficientOfFlownKilometers());
    }
    public static double calculatePrice(final TicketClass ticketClass, final Flight flight, final FlightRules flightRules) {
        return Calculations.calculatePrice(ticketClass, flightRules.getBaseEconomyCost(), flightRules.getBaseFirstCost(), flightRules.getBaseBusinessCost(), flight.getCountOfSeats(), flight.getCountOfCanceled(), flightRules.getAllowCancelPercentage(), flight.getCountOfLeftTickets(), flightRules.getPercentageMarkupForLastTicket(), flightRules.getPercentageDiscountIfAllCancel(), (int) flight.getDistance(), flightRules.getPricePerKm(), new FlownKilometers(0), flightRules.getCoefficientOfFlownKilometers());
    }
    public static double calculatePrice(final TicketClass ticketClass, final double baseEconomyCost, final double baseFirstCost, final double baseBusinessCost, final int countOfTickets, final int countOfCanceledTickets, final double allowCancelPercentage, final int countOfLeftTickets, final double percentageMarkupForLastTicket, final double percentageDiscountIfAllCancel, final int countOfKilometers, final double pricePerKm, final FlownKilometers flownKilometers, final double coefficientOfFlownKilometers) {
        double price = 0;
        if (flownKilometers.getValue() > 0) {
            if (flownKilometers.getValue() * coefficientOfFlownKilometers < countOfKilometers) {
                price += (countOfKilometers - flownKilometers.getValue() * coefficientOfFlownKilometers) * pricePerKm;
                flownKilometers.setValue(0);
            }
            else {
                flownKilometers.setValue((int) (flownKilometers.getValue() - countOfKilometers / coefficientOfFlownKilometers));
            }
        }
        //base price by class
        switch (ticketClass) {
            case Economy -> price += baseEconomyCost;
            case First -> price += baseFirstCost;
            case Business -> price += baseBusinessCost;
            default -> throw new NoSuchElementException("No type of ticket in calculating price");
        }
        //the fewer tickets, the higher the price only if not many cancels
        if ((double) countOfCanceledTickets / countOfTickets < allowCancelPercentage && countOfLeftTickets < countOfTickets) {
            price *= ((1.00 - (double) countOfLeftTickets / countOfTickets) * percentageMarkupForLastTicket)+1;
        }
        //the more canceled tickets, the bigger the discount
        if (countOfCanceledTickets > 0 && countOfCanceledTickets < countOfTickets) {
            price /= (1.00 - (double) countOfCanceledTickets / countOfTickets) * percentageDiscountIfAllCancel;
        }
        return (int) price;
    }
}
