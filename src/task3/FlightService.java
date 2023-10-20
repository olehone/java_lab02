package task3;

import task3.Data.Location;
import task3.Data.TicketType;
import task3.Identified.*;
import task3.Rules.FlightRules;
import task3.Rules.LuggageRules;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
public class FlightService implements FlightStrategy{
    final private List<Passenger> passengers;
    final private List<Aircraft> aircrafts;
    final private List<Airline> airlines;
    final private List<Airport> airports;
    final private List<Flight> flights;
    final private DefaultValuesService defaultValuesService;

    //make Lists and defaults object for deleting
    public FlightService() {
        this.passengers = new LinkedList<>();
        this.aircrafts = new LinkedList<>();
        this.airports = new LinkedList<>();
        this.airlines = new LinkedList<>();
        this.flights = new LinkedList<>();
        this.defaultValuesService = new DefaultValuesService();
    }

    public Long sellTicket(final Long flightId, final Long passengerId, final TicketType ticketType) {
        final Flight flight = getFlightById(flightId);
        final Passenger passenger = getPassengerById(passengerId);
        return sellTicket(flight, passenger, ticketType);
    }

    private Long sellTicket(final Flight flight, final Passenger passenger, final TicketType ticketType) {
        if (flight == null || ticketType == null || flight.getCountOfLeftTicketsByClass(ticketType) <= 0 || passenger == null)
            return null;
        final Ticket newTicket = new Ticket(passenger, ticketType, flight);
        flight.addTicket(newTicket);
        passenger.addTicket(newTicket);
        return newTicket.getId();
    }

    public Long addAirlines(final String name, final double baseEconomyCost, final double baseFirstCost, final double baseBusinessCost, final double allowCancelPercentage, final double percentageMarkupForLastTicket, final double percentageDiscountIfAllCancel, final double pricePerKm, final double coefficientOfFlownKilometers, final double returnPercentageInLess3Day, final double returnPercentageInLess10Day, final double returnPercentageInLess30Day, final double baseReturnPercentage, final double returnPercentageIfFlightCanceled, final double maxUnpaidWeightInKg, final double maxUnpaidSideLengthInCm, final double priceForExtraWeightByKg, final double priceForExtraLengthByCm, final double maxWeightInKg, final double maxSideLengthInCm) {
        final FlightRules newFlightRules = new FlightRules(baseEconomyCost, baseFirstCost, baseBusinessCost, allowCancelPercentage, percentageMarkupForLastTicket, percentageDiscountIfAllCancel, pricePerKm, coefficientOfFlownKilometers, returnPercentageInLess3Day, returnPercentageInLess10Day, returnPercentageInLess30Day, baseReturnPercentage, returnPercentageIfFlightCanceled);
        final LuggageRules newLuggageRules = new LuggageRules(maxUnpaidWeightInKg, maxUnpaidSideLengthInCm, priceForExtraWeightByKg, priceForExtraLengthByCm, maxWeightInKg, maxSideLengthInCm);
        return addAirlines(name, newFlightRules, newLuggageRules);
    }

    public Long addAirlines(final String name, final FlightRules flightRules, final LuggageRules luggageRules) {
        if (flightRules == null)
            return null;
        if (luggageRules == null)
            return null;
        final Airline newAirline = new Airline(name, flightRules, luggageRules);
        airlines.add(newAirline);
        return newAirline.getId();
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

    public Long addAirport(final String strCode, final String country, final String city, final double latitude, final double longitude, final ZoneId zoneId) {
        final Location newLocation = new Location(country, city, latitude, longitude, zoneId);
        final Airport newAirport = new Airport(strCode, newLocation);
        airports.add(newAirport);
        return newAirport.getId();
    }

    public Long addAirport(final String strCode, final String country, final String city, final double latitude, final double longitude, final String utsOffset) {
        final Location newLocation = new Location(country, city, latitude, longitude, utsOffset);
        final Airport newAirport = new Airport(strCode, newLocation);
        airports.add(newAirport);
        return newAirport.getId();
    }

    public Long addAirport(final String strCode, final Location location) {
        final Airport newAirport = new Airport(strCode, location);
        airports.add(newAirport);
        return newAirport.getId();
    }

    public Long addFlight(final ZonedDateTime departureTime, final ZonedDateTime arrivalTime, final Long departureAirportId, final Long arrivalAirportId, final Long aircraftId, final Long AirlinesId) {
        final Airport departureAirport = getAirportById(departureAirportId);
        final Airport arrivalAirport = getAirportById(arrivalAirportId);
        final Airline airline = getAirlinesById(AirlinesId);
        final Aircraft aircraft = getAircraftById(aircraftId);
        return addFlight(departureTime, arrivalTime, departureAirport, arrivalAirport, aircraft, airline);
    }

    private Long addFlight(final ZonedDateTime departureTime, final ZonedDateTime arrivalTime, final Airport departureAirport, final Airport arrivalAirport, final Aircraft aircraft, final Airline airline) {
        if (departureAirport == null || arrivalAirport == null || airline == null || aircraft == null)
            return null;
        final Flight newFlight = new Flight(departureTime, arrivalTime, departureAirport, arrivalAirport, aircraft, airline);
        departureAirport.addFlight(newFlight);
        arrivalAirport.addFlight(newFlight);
        flights.add(newFlight);
        return newFlight.getId();
    }

    public boolean changePassenger(final Long passengerId, final String firstName, final String lastName, final Boolean isBonusEnable) {
        final Passenger passenger = getPassengerById(passengerId);
        if (passenger == null)
            return false;
        if (firstName != null)
            passenger.setFirstName(firstName);
        if (lastName != null)
            passenger.setLastName(lastName);
        if (isBonusEnable != null)
            passenger.setBonusEnable(isBonusEnable);
        return true;
    }

    public String getSchedules() {
        return airports.stream().map(Airport::scheduleToString).collect(Collectors.joining("\n"));
    }

    public FlightSchedule createSchedule(final List<Flight> flights) {
        return new FlightSchedule(flights);
    }
    public FlightSchedule createSchedule(final Long... flightIds) {
        return createSchedule(Arrays.stream(flightIds)
                        .map(this::getFlightById)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
        );
    }

    public boolean changeFlight(final Long flightId, final ZonedDateTime departureTime, final ZonedDateTime arrivalTime, final Long departureAirportId, final Long arrivalAirportId, final Long aircraftId, final Long AirlinesId) {
        return changeFlight(getFlightById(flightId), departureTime, arrivalTime,
                getAirportById(departureAirportId), getAirportById(arrivalAirportId),
                getAircraftById(aircraftId), getAirlinesById(AirlinesId));
    }

    private boolean changeFlight(final Flight flight, final ZonedDateTime departureTime, final ZonedDateTime arrivalTime, final Airport departureAirport, final Airport arrivalAirport, final Aircraft aircraft, final Airline airline) {
        if (flight == null)
            return false;
        if (departureAirport != null)
            flight.setDepartureAirport(departureAirport);
        if (arrivalAirport != null)
            flight.setArrivalAirport(arrivalAirport);
        if (airline != null)
            flight.setAirlines(airline);
        if (aircraft != null)
            flight.setAircraft(aircraft);
        if (departureTime != null)
            flight.setDepartureTime(departureTime);
        if (arrivalTime != null)
            flight.setArrivalTime(arrivalTime);
        return true;
    }

    public boolean changeAircraft(final Long aircraftId, final String manufacturer, final String model, final Integer economySeat, final Integer firstSeat, final Integer businessSeat) {
        return changeAircraft(getAircraftById(aircraftId), manufacturer, model, economySeat, firstSeat, businessSeat);
    }

    private boolean changeAircraft(final Aircraft aircraft, final String manufacturer, final String model, final Integer economySeat, final Integer firstSeat, final Integer businessSeat) {
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

    public boolean changeAirlines(final Long AirlinesId, final String name, final double baseEconomyCost, final double baseFirstCost, final double baseBusinessCost, final double allowCancelPercentage, final double percentageMarkupForLastTicket, final double percentageDiscountIfAllCancel, final double pricePerKm, final double coefficientOfFlownKilometers, final double returnPercentageInLess3Day, final double returnPercentageInLess10Day, final double returnPercentageInLess30Day, final double baseReturnPercentage, final double returnPercentageIfFlightCanceled, final double maxUnpaidWeightInKg, final double maxUnpaidSideLengthInCm, final double priceForExtraWeightByKg, final double priceForExtraLengthByCm, final double maxWeightInKg, final double maxSideLengthInCm) {
        final FlightRules newFlightRules = new FlightRules(baseEconomyCost, baseFirstCost, baseBusinessCost, allowCancelPercentage, percentageMarkupForLastTicket, percentageDiscountIfAllCancel, pricePerKm, coefficientOfFlownKilometers, returnPercentageInLess3Day, returnPercentageInLess10Day, returnPercentageInLess30Day, baseReturnPercentage, returnPercentageIfFlightCanceled);
        final LuggageRules newLuggageRules = new LuggageRules(maxUnpaidWeightInKg, maxUnpaidSideLengthInCm, priceForExtraWeightByKg, priceForExtraLengthByCm, maxWeightInKg, maxSideLengthInCm);
        return changeAirlines(AirlinesId, name, newFlightRules, newLuggageRules);
    }

    public boolean changeAirlines(final Long AirlinesId, final String name, final FlightRules flightRules, final LuggageRules luggageRules) {
        return changeAirlines(getAirlinesById(AirlinesId), name, flightRules, luggageRules);
    }

    private boolean changeAirlines(final Airline airline, final String name, final FlightRules flightRules, final LuggageRules luggageRules) {
        if (airline == null)
            return false;
        if (flightRules != null)
            airline.setFlightPrices(flightRules);
        if (luggageRules != null)
            airline.setLuggageRules(luggageRules);
        if (name != null)
            airline.setName(name);
        return true;
    }

    public boolean changeAirport(final Long airportId, final String country, final String city, final Double latitude, final Double longitude, final String utsOffset) {
        return changeAirport(airportId, country, city, latitude, longitude, ZoneOffset.of(utsOffset));
    }

    private boolean changeAirport(final Airport airport, final String country, final String city, final Double latitude, final Double longitude, final ZoneId zoneId) {
        if (airport == null)
            return false;
        final Location location = airport.getLocation();
        if (country != null)
            location.setCountry(country);
        if (city != null)
            location.setCity(city);
        if (longitude != null)
            location.setLongitude(longitude);
        if (latitude != null)
            location.setLatitude(latitude);
        if (zoneId != null)
            location.setZoneId(zoneId);
        return true;
    }

    public boolean changeAirport(final Long airportId, final String country, final String city, final Double latitude, final Double longitude, final ZoneId zoneId) {
        return changeAirport(getAirportById(airportId), country, city, latitude, longitude, zoneId);

    }

    private double getProfitByAirlines(final Airline airline) {
        if (airline == null)
            return 0;
        double profit = 0;
        final List<Flight> airlinesFlights = flights.stream().filter(flight -> flight.getAirlines() == airline).toList();
        for (final Flight flight : airlinesFlights) {
            profit += flight.calculateProfit();
        }
        return (int) profit;
    }

    public double getProfitByAirlinesId(final Long AirlinesId) {
        final Airline airline = getAirlinesById(AirlinesId);
        return getProfitByAirlines(airline);
    }

    public double getProfitByTimeAndAirlinesId(final Long AirlinesId, final ZonedDateTime startTime, final ZonedDateTime finalTime) {
        final Airline airline = getAirlinesById(AirlinesId);
        return getProfitByTimeAndAirlines(airline, startTime, finalTime);
    }

    private double getProfitByTimeAndAirlines(final Airline airline, final ZonedDateTime startTime, final ZonedDateTime finalTime) {
        double profit = 0;
        List<Flight> airlinesFlights = flights.stream().filter(flight -> flight.getAirlines() == airline).toList();
        airlinesFlights = airlinesFlights.stream().filter(flight -> startTime.isBefore(flight.getDepartureTime())).toList();
        airlinesFlights = airlinesFlights.stream().filter(flight -> finalTime.isAfter(flight.getDepartureTime())).toList();
        for (final Flight flight : airlinesFlights) {
            profit += flight.calculateProfit();
        }
        return (int) profit;
    }

    public double getProfitByTime(final ZonedDateTime startTime, final ZonedDateTime finalTime) {
        double profit = 0;
        List<Flight> airlinesFlights = flights.stream().filter(flight -> startTime.isBefore(flight.getDepartureTime())).toList();
        airlinesFlights = airlinesFlights.stream().filter(flight -> finalTime.isAfter(flight.getDepartureTime())).toList();
        for (final Flight flight : airlinesFlights) {
            profit += flight.calculateProfit();
        }
        return (int) profit;
    }

    public String getFlightInfoById(final Long flightId) {
        final Flight flight = getFlightById(flightId);
        return getFlightInfo(flight);
    }

    public String getFlightInfo(final Flight flight) {
        if (flight == null)
            return "No info";
        return flight.toString();
    }

    public boolean cancelTicket(final Long ticketId) {
        final Ticket ticket = getTicketById(ticketId);
        if (ticket == null)
            return false;
        ticket.cancel();
        return true;
    }

    public boolean cancelTicket(final Long passengerId, final Long flightId) {
        final Ticket ticket = getTicketByFlightAndPassengerId(passengerId, flightId);
        if (ticket == null)
            return false;
        ticket.cancel();
        return true;
    }

    public Ticket getTicketById(final Long ticketId) {
        return flights.stream()
                .flatMap(flight -> flight.getTickets().stream())
                .filter(ticket -> ticketId.equals(ticket.getId()))
                .findFirst()
                .orElse(null);
    }

    public Ticket getTicketByFlightAndPassengerId(final Long passengerId, final Long flightId) {
        final Passenger passenger = getPassengerById(passengerId);
        if (passenger == null || passenger.getTickets().isEmpty())
            return null;
        return passenger.getTickets().stream()
                .filter(ticket -> flightId.equals(ticket.getFlight().getId()))
                .findFirst().orElse(null);
    }

    public boolean deletePassenger(final Long passengerId) {
        Passenger passenger = getPassengerById(passengerId);
        if (passenger == null)
            return false;
        passenger.deleteTickets(defaultValuesService.getDefaultPassenger());
        passengers.remove(passenger);
        passenger = defaultValuesService.getDefaultPassenger();
        return true;
    }

    public List<Ticket> getTicketsByFlightId(final Long flightId) {
        final Flight flight = getFlightById(flightId);
        if (flight == null)
            return null;
        return flight.getTickets();
    }

    public String getTicketsByFlightIdToString(final Long flightId) {
        final List<Ticket> tickets = getTicketsByFlightId(flightId);
        if (tickets == null || tickets.isEmpty())
            return "";
        return tickets.stream().map(Ticket::toString).collect(Collectors.joining("\n"));
    }

    public boolean deleteAircraft(final Long aircraftId) {
        Aircraft aircraft = getAircraftById(aircraftId);
        if (aircraft == null)
            return false;
        aircrafts.remove(aircraft);
        aircraft = defaultValuesService.getDefaultAircraft();
        return true;
    }


    public boolean deleteAirport(final Long airportId) {
        final Airport airport = getAirportById(airportId);
        if (airport == null)
            return false;
        flights.stream().filter(flight -> flight.getArrivalAirport() == airport)
                .forEach(flight -> flight.setArrivalAirport(defaultValuesService.getDefaultAirport()));
        flights.stream().filter(flight -> flight.getDepartureAirport() == airport)
                .forEach(flight -> flight.setDepartureAirport(defaultValuesService.getDefaultAirport()));
        return airports.remove(airport);
    }

    public boolean deleteFlight(final Long flightId) {
        final Flight flight = getFlightById(flightId);
        if (flight == null)
            return false;
        //delete from schedules
        if (flight.getDepartureAirport() != null)
            flight.getDepartureAirport().removeFlight(flight);
        if (flight.getArrivalAirport() != null)
            flight.getArrivalAirport().removeFlight(flight);
        // if someone buy ticket, we can`t just
        // delete flight, cancel them
        if (!flight.getTickets().isEmpty()) {
            flight.cancel();
        } else {
            flights.remove(flight);
        }
        return true;
    }

    public FlightSchedule getScheduleByAirportId(final Long airportId) {
        final Airport airport = getAirportById(airportId);
        if (airport == null)
            return null;
        return airport.getFlightSchedule();
    }

    public boolean setScheduleToAirportById(final Long airportId, final FlightSchedule schedule) {
        final Airport airport = getAirportById(airportId);
        if (airport == null || schedule == null)
            return false;
        airport.setFlightSchedule(schedule);
        return true;
    }

    public String getScheduleStringByAirportId(final Long airportId) {
        final FlightSchedule schedule = getScheduleByAirportId(airportId);
        if (schedule == null)
            return "No schedule";
        return schedule.toString();
    }

    public Passenger getPassengerById(final Long passengerId) {
        return passengers.stream()
                .filter(passenger -> passenger.getId().equals(passengerId))
                .findFirst()
                .orElse(null);
    }

    public Airline getAirlinesById(final Long AirlinesId) {
        return airlines.stream()
                .filter(line -> line.getId().equals(AirlinesId))
                .findFirst()
                .orElse(null);
    }

    public List<Flight> getFlightsByCities(final String departureCity, final String arrivalCity) {
        if (departureCity == null || arrivalCity == null)
            return null;
        return flights.stream()
                .filter(flight ->
                        (departureCity.equals(flight.getDepartureAirport().getLocation().getCity()) &&
                                arrivalCity.equals((flight.getArrivalAirport().getLocation().getCity()))))
                .sorted(Comparator.comparing(Flight::getDepartureTime))
                .toList();
    }

    public List<Flight> getFlightsByCountriesAndTime(final String departureCountry, final String arrivalCountry, final ZonedDateTime earliestTime, final ZonedDateTime latestTime) {
        if (departureCountry == null || arrivalCountry == null)
            return null;
        return flights.stream()
                .filter(flight ->
                        departureCountry.equals(flight.getDepartureAirport().getLocation().getCountry()) &&
                                arrivalCountry.equals(flight.getArrivalAirport().getLocation().getCountry()) &&
                                earliestTime.isBefore(flight.getDepartureTime()) &&
                                latestTime.isAfter(flight.getArrivalTime()))
                .sorted(Comparator.comparing(Flight::getDepartureTime))
                .toList();

    }

    public String getFlightsByCountriesAndTimeToString(final String departureCountry, final String arrivalCountry, final ZonedDateTime earliestTime, final ZonedDateTime latestTime) {
        return flightsToString(getFlightsByCountriesAndTime(departureCountry, arrivalCountry, earliestTime, latestTime));
    }

    public String getFlightsByCitiesToString(final String departureCity, final String arrivalCity) {
        return flightsToString(getFlightsByCities(departureCity, arrivalCity));
    }

    public Aircraft getAircraftById(final Long aircraftId) {
        return aircrafts.stream()
                .filter(aircraft -> aircraft.getId().equals(aircraftId))
                .findFirst()
                .orElse(null);
    }

    public Airport getAirportById(final Long airportId) {
        return airports.stream()
                .filter(airport -> airport.getId().equals((airportId)))
                .findFirst()
                .orElse(null);
    }

    public Flight getFlightById(final Long flightId) {
        return flights.stream()
                .filter(flight -> flight.getId().equals((flightId)))
                .findFirst()
                .orElse(null);
    }

    public String flightsToString() {
        return flightsToString(this.flights);
    }

    public String flightsToString(final List<Flight> flights) {
        if (flights == null || flights.isEmpty())
            return "Empty flights list";
        return flights.stream().map(Flight::toString).collect(Collectors.joining("\n--------------------\n"));
    }

    public String passengersToString() {
        return passengers.stream().map(Passenger::toString).collect(Collectors.joining("\n"));
    }

    public String aircraftsToString() {
        return aircrafts.stream().map(Aircraft::toString).collect(Collectors.joining("\n"));
    }

    public String airportsToString() {
        return airports.stream().map(Airport::withScheduleToString).collect(Collectors.joining("\n"));
    }

    public String airlinesToString() {
        return airlines.stream()
                .map(airline -> airline.toString() +
                        "\n Profit" +
                        "\n Last year: " + getProfitByTimeAndAirlines(airline, ZonedDateTime.now().minusMonths(12), ZonedDateTime.now()) +
                        "\n Last quarter: " + getProfitByTimeAndAirlines(airline, ZonedDateTime.now().minusMonths(3), ZonedDateTime.now()) +
                        "\n Total: " + getProfitByAirlines(airline) +
                        "\n")
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String toString() {
        return "\n********************\n"
                + "Passengers" +
                "\n====================\n" +
                passengersToString() +
                "\n********************\n" +

                "Airports" +
                "\n====================\n" +
                airportsToString() +
                "\n********************\n" +

                "Aircrafts" +
                "\n====================\n" +
                aircraftsToString() +
                "\n********************\n" +

                "Airlines" +
                "\n====================\n" +
                airlinesToString() +
                "\n********************\n" +

                "Flights" +
                "\n====================\n" +
                flightsToString() +
                "\n********************\n";
    }
}