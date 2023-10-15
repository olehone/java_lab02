package task3;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

public class FlightService {
    final private List<Passenger> passengers;
    final private List<Aircraft> aircrafts;
    final private List<AirCompany> airCompanies;
    final private List<Airport> airports;
    final private List<Flight> flights;

    public FlightService() {
        this.passengers = new LinkedList<>();
        this.aircrafts = new LinkedList<>();
        this.airports = new LinkedList<>();
        this.airCompanies = new LinkedList<>();
        this.flights = new LinkedList<>();
    }
    public Long addTicket(final Flight flight, final Passenger passenger, final TicketClass ticketClass){
        if(flight.getCountOfLeftTicketsByClass(ticketClass)<=0)
            return null;
        final Ticket newTicket = new Ticket(passenger,ticketClass, flight);
        flight.addTicket(newTicket);
        passenger.addTicket(newTicket);
        return newTicket.getId();
    }
    public Long addTicket(final Long flightId, final Long passengerId, final TicketClass ticketClass){
        final Flight flight = getFlightById(flightId);
        final Passenger passenger = getPassengerById(passengerId);
        return addTicket(flight, passenger, ticketClass);
    }
    public Long addPassenger(final String firstName, final String lastName, final boolean isBonusEnable) {
        final Passenger newPassenger = new Passenger(firstName, lastName, isBonusEnable);
        passengers.add(newPassenger);
        return newPassenger.getId();
    }

    public Long addAircraft(final String manufacturer, final String model, final int economySeat, final int firstSeat, final int businessSeat) {
        final Aircraft newAircraft = new Aircraft(manufacturer, model, economySeat, firstSeat, businessSeat);
        aircrafts.add(newAircraft);
        return newAircraft.getId();
    }

    public Long addAirCompany(final String name, final double baseEconomyCost, final double baseFirstCost, final double baseBusinessCost, final double allowCancelPercentage, final double percentageMarkupForLastTicket, final double percentageDiscountIfAllCancel, final double pricePerKm, final double coefficientOfFlownKilometers, final double returnPercentageInLess3Day, final double returnPercentageInLess10Day, final double returnPercentageInLess30Day, final double baseReturnPercentage, final double returnPercentageIfFlightCanceled, final double maxUnpaidWeightInKg, final double maxUnpaidSideLengthInCm, final double priceForExtraWeightByKg, final double priceForExtraLengthByCm, final double maxWeightInKg, final double maxSideLengthInCm) {
        final FlightPrices newFlightPrices = new FlightPrices(baseEconomyCost, baseFirstCost, baseBusinessCost, allowCancelPercentage, percentageMarkupForLastTicket, percentageDiscountIfAllCancel, pricePerKm, coefficientOfFlownKilometers, returnPercentageInLess3Day, returnPercentageInLess10Day, returnPercentageInLess30Day, baseReturnPercentage, returnPercentageIfFlightCanceled);
        final LuggageRules newLuggageRules = new LuggageRules(maxUnpaidWeightInKg, maxUnpaidSideLengthInCm, priceForExtraWeightByKg, priceForExtraLengthByCm, maxWeightInKg, maxSideLengthInCm);
        return addAirCompany(name, newFlightPrices, newLuggageRules);
    }

    public Long addAirCompany(final String name, final FlightPrices flightPrices, final LuggageRules luggageRules) {
        final AirCompany newAirCompany = new AirCompany(name, flightPrices, luggageRules);
        airCompanies.add(newAirCompany);
        return newAirCompany.getId();
    }

    public Long addAirport(final String address, final String country, final String city, final double latitude, final double longitude, final ZoneId zoneId) {
        final Location newLocation = new Location(address, country, city, latitude, longitude, zoneId);
        final Airport newAirport = new Airport(newLocation);
        airports.add(newAirport);
        return newAirport.getId();
    }

    public Long addAirport(final String address, final String country, final String city, final double latitude, final double longitude, final String utsOffset) {
        final Location newLocation = new Location(address, country, city, latitude, longitude, utsOffset);
        final Airport newAirport = new Airport(newLocation);
        airports.add(newAirport);
        return newAirport.getId();
    }

    public Long addFlight(final ZonedDateTime departureTime, final ZonedDateTime arrivalTime, final Airport departureAirport, final Airport arrivalAirport, final Aircraft aircraft, final AirCompany airCompany) {
        if (departureAirport == null || arrivalAirport == null || airCompany == null || aircraft == null)
            return null;
        final Flight newFlight = new Flight(departureTime, arrivalTime, departureAirport, arrivalAirport, aircraft, airCompany);
        departureAirport.addFlight(newFlight);
        arrivalAirport.addFlight(newFlight);
        flights.add(newFlight);
        return newFlight.getId();
    }

    public Long addFlight(final ZonedDateTime departureTime, final ZonedDateTime arrivalTime, final Long departureAirportId, final Long arrivalAirportId, final Long aircraftId, final Long airCompanyId) {
        final Airport departureAirport = getAirportById(departureAirportId);
        final Airport arrivalAirport = getAirportById(arrivalAirportId);
        final AirCompany airCompany = getAirCompanyById(airCompanyId);
        final Aircraft aircraft = getAircraftById(aircraftId);
        return addFlight(departureTime, arrivalTime, departureAirport, arrivalAirport, aircraft, airCompany);
    }

    public Passenger getPassengerById(final Long passengerId) {
        if (passengerId == null)
            return null;

        return passengers.stream()
                .filter(passenger -> passengerId.equals(passenger.getId()))
                .findFirst()
                .orElse(null);
    }

    public AirCompany getAirCompanyById(final Long airCompanyId) {
        if (airCompanyId == null)
            return null;

        return airCompanies.stream()
                .filter(company -> airCompanyId.equals(company.getId()))
                .findFirst()
                .orElse(null);
    }
    public Aircraft getAircraftById(final Long aircraftId) {
        if (aircraftId == null)
            return null;

        return aircrafts.stream()
                .filter(aircraft -> aircraftId.equals(aircraft.getId()))
                .findFirst()
                .orElse(null);
    }

    public Airport getAirportById(final Long airportId) {
        if (airportId == null)
            return null;

        return airports.stream()
                .filter(airport -> airportId.equals(airport.getId()))
                .findFirst()
                .orElse(null);
    }

    public Flight getFlightById(final Long flightId) {
        if (flightId == null)
            return null;

        return flights.stream()
                .filter(flight -> flightId.equals(flight.getId()))
                .findFirst()
                .orElse(null);
}
    public String flightsToString(){
        final StringBuilder string = new StringBuilder(" ");
        for(final Flight flight: flights){
            string.append(flight.toString()).append("\n");
        }
        return string.toString();
    }
    public String passengersToString(){
        final StringBuilder string = new StringBuilder();
        for(final Passenger passenger: passengers){
            string.append(passenger.toString()).append("\n");
        }
        return string.toString();
    }
    public String aircraftsToString(){
        final StringBuilder string = new StringBuilder();
        for(final Aircraft aircraft: aircrafts){
            string.append(aircraft.toString()).append("\n");
        }
        return string.toString();
    }
    public String airportsToString(){
        final StringBuilder string = new StringBuilder();
        for(final Airport airport: airports){
            string.append(airport.toString()).append("\n");
        }
        return string.toString();
    }
    public String airCompaniesToString(){
        final StringBuilder string = new StringBuilder();
        for(final AirCompany airCompany: airCompanies){
            string.append(airCompany.toString()).append("\n");
        }
        return string.toString();
    }



}
/*
    public <T> boolean addObject(final List<T> list, final T object) {
        if (list.stream().noneMatch(object::equals)) {
            list.add(object);
            return true;
        }
        return false;
    }


    public boolean addPassenger(final Passenger passenger) {
        return addObject(this.passengers, passenger);
    }

    public boolean addAircraft(final Aircraft aircraft) {
        return addObject(this.aircrafts, aircraft);
    }

    public boolean addAirCompanies(final AirCompany airCompany) {
        return addObject(this.airCompanies, airCompany);
    }

    public boolean addAirport(final Airport airport) {
        return addObject(this.airports, airport);
    }

    public boolean addFlight(final Flight flight) {
        return addObject(this.flights, flight);
    }
    */
