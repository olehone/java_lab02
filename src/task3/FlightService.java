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

    //make Lists with firsts defaults object for deleting
    public FlightService() {
        this.passengers = new LinkedList<>();
        this.aircrafts = new LinkedList<>();
        this.airports = new LinkedList<>();
        this.airCompanies = new LinkedList<>();
        this.flights = new LinkedList<>();
        addDefaultObjects();
    }

    //if remove any object, references go to default objects
    private void addDefaultObjects() {
        final Passenger defaultPassenger = new Passenger("REMOVED", "PASSENGER", false, 0);
        final Aircraft defaultAircraft = new Aircraft("REMOVED", "AIRCRAFT", 0, 0, 0);
        final Location defaultLocation = new Location("REMOVED", "LOCATION", "REMOVED", 0., 0., "+0");
        final Airport defaultAirport = new Airport(defaultLocation);
        final FlightPrices defaultFlightPrices = new FlightPrices(0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.);
        final LuggageRules defaultLuggageRules = new LuggageRules(0., 0., 0., 0., 0., 0.0);
        final AirCompany defaultAirCompany = new AirCompany("REMOVED", defaultFlightPrices, defaultLuggageRules);
        final ZonedDateTime defaultTime = ZonedDateTime.now();
        final Flight defaultFlight = new Flight(defaultTime, defaultTime, defaultAirport, defaultAirport, defaultAircraft, defaultAirCompany);
        defaultFlight.cancel();
        passengers.add(defaultPassenger);
        aircrafts.add(defaultAircraft);
        airports.add(defaultAirport);
        airCompanies.add(defaultAirCompany);
        flights.add(defaultFlight);
    }

    public double getProfitByTimeAndCompany(final AirCompany airCompany, final ZonedDateTime startTime, final ZonedDateTime finalTime) {
        double profit = 0;
        List<Flight> companyFlights = flights.stream().filter(flight -> flight.getAirCompany() == airCompany).toList();
        companyFlights = companyFlights.stream().filter(flight -> startTime.isBefore(flight.getDepartureTime())).toList();
        companyFlights = companyFlights.stream().filter(flight -> finalTime.isAfter(flight.getDepartureTime())).toList();
        companyFlights.remove(getFlightById(0L));
        for (final Flight flight : companyFlights) {
            profit += flight.calculateProfit();
        }
        return profit;
    }

    public double getProfitByTime(final ZonedDateTime startTime, final ZonedDateTime finalTime) {
        double profit = 0;
        List<Flight> companyFlights = flights.stream().filter(flight -> startTime.isBefore(flight.getDepartureTime())).toList();
        companyFlights = companyFlights.stream().filter(flight -> finalTime.isAfter(flight.getDepartureTime())).toList();
        for (final Flight flight : companyFlights) {
            profit += flight.calculateProfit();
        }
        return profit;
    }

    public Long addTicket(final Flight flight, final Passenger passenger, final TicketClass ticketClass) {
        if (flight == null || flight.getCountOfLeftTicketsByClass(ticketClass) <= 0)
            return null;
        if (passenger == null)
            return null;
        if (ticketClass == null)
            return null;
        final Ticket newTicket = new Ticket(passenger, ticketClass, flight);
        flight.addTicket(newTicket);
        passenger.addTicket(newTicket);
        return newTicket.getId();
    }

    public Long addTicket(final Long flightId, final Long passengerId, final TicketClass ticketClass) {
        final Flight flight = getFlightById(flightId);
        final Passenger passenger = getPassengerById(passengerId);
        return addTicket(flight, passenger, ticketClass);
    }

    public String getFlightInfoById(final Long flightId) {
        final Flight flight = getFlightById(flightId);
        return getFlightInfo(flight);
    }

    public String getFlightInfo(final Flight flight) {
        if (flight == null)
            return "No info";
        return flight.getFlightInfoToString();
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
    public <T extends HasId> boolean notNullOrDefault(final T object) {
        if(object==null)
            return false;
        if (object.getId() <= 0L)
            return false;
        return true;
    }







    public void changePassenger(final Long passengerId, final String firstName, final String lastName, final Boolean isBonusEnable) {
        if (passengerId <= 1L)
            return;
        final Passenger passenger = getPassengerById(passengerId);
        if (passenger == null)
            return;
        if (firstName != null)
            passenger.setFirstName(firstName);
        if (lastName != null)
            passenger.setLastName(lastName);
        if (isBonusEnable != null)
            passenger.setBonusEnable(isBonusEnable);
    }

    public boolean changeFlight(final Long flightId, final ZonedDateTime departureTime, final ZonedDateTime arrivalTime, final Airport departureAirport, final Airport arrivalAirport, final Aircraft aircraft, final AirCompany airCompany) {
        if (flightId <= 0L)
            return false;
        final Flight flight = getFlightById(flightId);
        if (flight == null)
            return false;
        if (departureAirport != null)
            flight.setDepartureAirport(departureAirport);
        if (arrivalAirport != null)
            flight.setArrivalAirport(arrivalAirport);
        if (airCompany != null)
            flight.setAirCompany(airCompany);
        if (aircraft != null)
            flight.setAircraft(aircraft);
        if (departureTime != null)
            flight.setDepartureTime(departureTime);
        if (arrivalTime != null)
            flight.setArrivalTime(arrivalTime);
    }

    public boolean changeAircraft(final Long aircraftId, final String manufacturer, final String model, final Integer economySeat, final Integer firstSeat, final Integer businessSeat) {
        final Aircraft aircraft = getAircraftById(aircraftId);
        if (aircraft == null)
            return false;
        if (businessSeat != null)
            aircraft.setBusinessSeat(businessSeat);
        if (firstSeat != null)
            aircraft.setFirstSeat(firstSeat);
        if (economySeat != null)
            aircraft.setEconomySeat(economySeat);
        if (model != null)
            aircraft.setModel(model);
        if (manufacturer != null)
            aircraft.setManufacturer(manufacturer);
        return true;
    }

    public boolean changeAirCompany(final Long airCompanyId, final String name, final double baseEconomyCost, final double baseFirstCost, final double baseBusinessCost, final double allowCancelPercentage, final double percentageMarkupForLastTicket, final double percentageDiscountIfAllCancel, final double pricePerKm, final double coefficientOfFlownKilometers, final double returnPercentageInLess3Day, final double returnPercentageInLess10Day, final double returnPercentageInLess30Day, final double baseReturnPercentage, final double returnPercentageIfFlightCanceled, final double maxUnpaidWeightInKg, final double maxUnpaidSideLengthInCm, final double priceForExtraWeightByKg, final double priceForExtraLengthByCm, final double maxWeightInKg, final double maxSideLengthInCm) {
        final FlightPrices newFlightPrices = new FlightPrices(baseEconomyCost, baseFirstCost, baseBusinessCost, allowCancelPercentage, percentageMarkupForLastTicket, percentageDiscountIfAllCancel, pricePerKm, coefficientOfFlownKilometers, returnPercentageInLess3Day, returnPercentageInLess10Day, returnPercentageInLess30Day, baseReturnPercentage, returnPercentageIfFlightCanceled);
        final LuggageRules newLuggageRules = new LuggageRules(maxUnpaidWeightInKg, maxUnpaidSideLengthInCm, priceForExtraWeightByKg, priceForExtraLengthByCm, maxWeightInKg, maxSideLengthInCm);
        return changeAirCompany(airCompanyId, name, newFlightPrices, newLuggageRules);
    }

    public boolean changeAirCompany(final Long airCompanyId, final String name, final FlightPrices flightPrices, final LuggageRules luggageRules) {
        final AirCompany airCompany = getAirCompanyById(airCompanyId);
        if (airCompany == null)
            return false;
        if (flightPrices != null)
            airCompany.setFlightPrices(flightPrices);
        if (luggageRules != null)
            airCompany.setLuggageRules(luggageRules);
        if (name != null)
            airCompany.setName(name);
        return true;
    }

    public boolean changeAirport(final Long airportId, final String address, final String country, final String city, final double latitude, final double longitude, final String utsOffset) {
        final Location newLocation = new Location(address, country, city, latitude, longitude, utsOffset);
        final Airport airport = getAirportById(airportId);
        if (airport == null)
            return false;
        airport.setLocation(newLocation);
        return true;
    }

    public boolean changeAirport(final Long airportId, final String address, final String country, final String city, final double latitude, final double longitude, final ZoneId zoneId) {
        final Location newLocation = new Location(address, country, city, latitude, longitude, zoneId);
        final Airport airport = getAirportById(airportId);
        if (airport == null)
            return false;
        airport.setLocation(newLocation);
        return true;
    }

    public boolean deletePassenger(final Long passengerId) {
        final Passenger passenger = getPassengerById(passengerId);
        if (passenger == null)
            return false;
        passenger.deleteTickets();
        return passengers.remove(passenger);

    }

    public boolean deleteAircraft(final Long aircraftId) {
        final Aircraft aircraft = getAircraftById(aircraftId);
        if (aircraft == null)
            return false;
        flights.stream().filter(flight -> flight.getAircraft() == aircraft)
                .forEach(flight -> flight.setAircraft(null));
        return aircrafts.remove(aircraft);

    }

    public boolean deleteAirCompany(final Long airCompanyId) {
        final AirCompany airCompany = getAirCompanyById(airCompanyId);
        if (airCompany == null)
            return false;
        flights.stream().filter(flight -> flight.getAirCompany() == airCompany)
                .forEach(flight -> flight.setAirCompany(null));
        return airCompanies.remove(airCompany);
    }

    public boolean deleteAirport(final Long airportId) {
        final Airport airport = getAirportById(airportId);
        if (airport == null)
            return false;
        flights.stream().filter(flight -> flight.getArrivalAirport() == airport)
                .forEach(flight -> flight.setArrivalAirport(null));
        flights.stream().filter(flight -> flight.getDepartureAirport() == airport)
                .forEach(flight -> flight.setDepartureAirport(null));
        return airports.remove(airport);
    }

    public boolean deleteFlight(final Long flightId) {
        final Flight flight = getFlightById(flightId);
        if (flight == null)
            return false;
        final List<Ticket> tickets = flight.getTickets();
        //if someone buy ticket, we can`t just delete flight
        if ((long) tickets.size() > 0) {
            flight.cancel();
            return true;
        }
        if (flight.getDepartureAirport() != null)
            flight.getDepartureAirport().removeFlight(flight);
        if (flight.getArrivalAirport() != null)
            flight.getArrivalAirport().removeFlight(flight);
        flights.remove(flight);
        return true;
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

    public String flightsToString() {
        final StringBuilder string = new StringBuilder(" ");
        for (final Flight flight : flights) {
            if (flight.getId() != 0L)
                string.append(flight.toString()).append("\n");
        }
        return string.toString();
    }

    public String passengersToString() {
        final StringBuilder string = new StringBuilder();
        for (final Passenger passenger : passengers) {
            if (passenger.getId() != 0L)
                string.append(passenger.toString()).append("\n");
        }
        return string.toString();
    }

    public String aircraftsToString() {
        final StringBuilder string = new StringBuilder();
        for (final Aircraft aircraft : aircrafts) {
            if (aircraft.getId() != 0L)
                string.append(aircraft.toString()).append("\n");
        }
        return string.toString();
    }

    public String airportsToString() {
        final StringBuilder string = new StringBuilder();
        for (final Airport airport : airports) {
            if (airport.getId() != 0L)
                string.append(airport.toString()).append("\n");
        }
        return string.toString();
    }

    public String airCompaniesToString() {
        final StringBuilder string = new StringBuilder();
        for (final AirCompany airCompany : airCompanies) {
            if (airCompany.getId() != 0L)
                string.append(airCompany.toString()).append("\n");
        }
        return string.toString();
    }

    public List<Passenger> getPassengers() {
        List<Passenger> passengers = passengers.remove()
        return passengers;
    }

    public List<Aircraft> getAircrafts() {
        if(aircrafts.get(0).getId()==0L)
            return
        return aircrafts;
    }

    public List<AirCompany> getAirCompanies() {
        return airCompanies;
    }

    public List<Airport> getAirports() {
        return airports;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    @Override
    public String toString() {
        return "Passengers" +
                "\n--------------------\n" +
                passengersToString() +
                "\n====================\n" +

                "Airports" +
                "\n--------------------\n" +
                airportsToString() +
                "\n====================\n" +

                "Aircrafts" +
                "\n--------------------\n" +
                aircraftsToString() +
                "\n====================\n" +

                "Air Companies" +
                "\n--------------------\n" +
                airCompaniesToString() +
                "\n====================\n" +

                "Flights" +
                "\n--------------------\n" +
                flightsToString() +
                "\n====================\n";
    }
}