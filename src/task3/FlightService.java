package task3;

import task3.Data.Location;
import task3.Data.TicketClass;
import task3.Identified.*;
import task3.Rules.FlightRules;
import task3.Rules.LuggageRules;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
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
public class FlightService {
    final private List<Passenger> passengers;
    final private List<Aircraft> aircrafts;
    final private List<AirCompany> airCompanies;
    final private List<Airport> airports;
    final private List<Flight> flights;
    final private Location defaultLocation;
    final private Passenger defaultPassenger;
    final private Aircraft defaultAircraft;
    final private AirCompany defaultAirCompany;
    final private Airport defaultAirport;
    final private Flight defaultFlight;
    final private FlightRules defaultFlightRules;
    final private LuggageRules defaultLuggageRules;
    final private ZonedDateTime defaultTime;


    //make Lists with firsts defaults object for deleting
    public FlightService() {
        this.passengers = new LinkedList<>();
        this.aircrafts = new LinkedList<>();
        this.airports = new LinkedList<>();
        this.airCompanies = new LinkedList<>();
        this.flights = new LinkedList<>();

        //if remove any object, references go to default objects
        this.defaultTime = ZonedDateTime.now();
        this.defaultFlightRules = new FlightRules(0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.);
        this.defaultLuggageRules = new LuggageRules(0., 0., 0., 0., 0., 0.0);
        this.defaultLocation = new Location("REMOVED", "LOCATION", "REMOVED", 0., 0., "+0");
        this.defaultPassenger = new Passenger("REMOVED", "PASSENGER", false, 0);
        this.defaultAircraft = new Aircraft("REMOVED", "AIRCRAFT", 1, 0, 0);
        this.defaultAirCompany = new AirCompany("REMOVED", defaultFlightRules, defaultLuggageRules);
        this.defaultAirport = new Airport("REMOVED", defaultLocation);
        this.defaultFlight = new Flight(defaultTime, defaultTime, defaultAirport, defaultAirport, defaultAircraft, defaultAirCompany);
        this.defaultFlight.cancel();
    }

    public double getProfitByCompany(final AirCompany airCompany) {
        double profit = 0;
        final List<Flight> companyFlights = flights.stream().filter(flight -> flight.getAirCompany() == airCompany).toList();
        for (final Flight flight : companyFlights) {
            profit += flight.calculateProfit();
        }
        return (int) profit;
    }

    public double getProfitByTimeAndCompany(final AirCompany airCompany, final ZonedDateTime startTime, final ZonedDateTime finalTime) {
        double profit = 0;
        List<Flight> companyFlights = flights.stream().filter(flight -> flight.getAirCompany() == airCompany).toList();
        companyFlights = companyFlights.stream().filter(flight -> startTime.isBefore(flight.getDepartureTime())).toList();
        companyFlights = companyFlights.stream().filter(flight -> finalTime.isAfter(flight.getDepartureTime())).toList();
        for (final Flight flight : companyFlights) {
            profit += flight.calculateProfit();
        }
        return (int) profit;
    }

    public double getProfitByTime(final ZonedDateTime startTime, final ZonedDateTime finalTime) {
        double profit = 0;
        List<Flight> companyFlights = flights.stream().filter(flight -> startTime.isBefore(flight.getDepartureTime())).toList();
        companyFlights = companyFlights.stream().filter(flight -> finalTime.isAfter(flight.getDepartureTime())).toList();
        for (final Flight flight : companyFlights) {
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
        if(ticket==null)
            return false;
        ticket.cancel();
        return true;
    }
    public boolean cancelTicket(final Long passengerId, final Long flightId) {
        final Ticket ticket = getTicketByFlightAndPassengerId(passengerId, flightId);
        if(ticket==null)
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
        if(passenger==null||passenger.getTickets().size()<1)
            return null;
        return passenger.getTickets().stream()
                .filter(ticket -> flightId.equals(ticket.getFlight().getId()))
                .findFirst().orElse(null);
    }
    public Long sellTicket(final Long flightId, final Long passengerId, final TicketClass ticketClass) {
        final Flight flight = getFlightById(flightId);
        final Passenger passenger = getPassengerById(passengerId);
        return sellTicket(flight, passenger, ticketClass);
    }

    private Long sellTicket(final Flight flight, final Passenger passenger, final TicketClass ticketClass) {
        if (flight == null || ticketClass == null || flight.getCountOfLeftTicketsByClass(ticketClass) <= 0 || passenger == null)
            return null;
        final Ticket newTicket = new Ticket(passenger, ticketClass, flight);
        flight.addTicket(newTicket);
        passenger.addTicket(newTicket);
        return newTicket.getId();
    }

    public Long addAirCompany(final String name, final double baseEconomyCost, final double baseFirstCost, final double baseBusinessCost, final double allowCancelPercentage, final double percentageMarkupForLastTicket, final double percentageDiscountIfAllCancel, final double pricePerKm, final double coefficientOfFlownKilometers, final double returnPercentageInLess3Day, final double returnPercentageInLess10Day, final double returnPercentageInLess30Day, final double baseReturnPercentage, final double returnPercentageIfFlightCanceled, final double maxUnpaidWeightInKg, final double maxUnpaidSideLengthInCm, final double priceForExtraWeightByKg, final double priceForExtraLengthByCm, final double maxWeightInKg, final double maxSideLengthInCm) {
        final FlightRules newFlightRules = new FlightRules(baseEconomyCost, baseFirstCost, baseBusinessCost, allowCancelPercentage, percentageMarkupForLastTicket, percentageDiscountIfAllCancel, pricePerKm, coefficientOfFlownKilometers, returnPercentageInLess3Day, returnPercentageInLess10Day, returnPercentageInLess30Day, baseReturnPercentage, returnPercentageIfFlightCanceled);
        final LuggageRules newLuggageRules = new LuggageRules(maxUnpaidWeightInKg, maxUnpaidSideLengthInCm, priceForExtraWeightByKg, priceForExtraLengthByCm, maxWeightInKg, maxSideLengthInCm);
        return addAirCompany(name, newFlightRules, newLuggageRules);
    }

    public Long addAirCompany(final String name, final FlightRules flightRules, final LuggageRules luggageRules) {
        if (flightRules == null)
            return null;
        if (luggageRules == null)
            return null;
        final AirCompany newAirCompany = new AirCompany(name, flightRules, luggageRules);
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

    public Long addAirport(final String strCode, final String address, final String country, final String city, final double latitude, final double longitude, final ZoneId zoneId) {
        final Location newLocation = new Location(address, country, city, latitude, longitude, zoneId);
        final Airport newAirport = new Airport(strCode, newLocation);
        airports.add(newAirport);
        return newAirport.getId();
    }

    public Long addAirport(final String strCode, final String address, final String country, final String city, final double latitude, final double longitude, final String utsOffset) {
        final Location newLocation = new Location(address, country, city, latitude, longitude, utsOffset);
        final Airport newAirport = new Airport(strCode, newLocation);
        airports.add(newAirport);
        return newAirport.getId();
    }

    public Long addAirport(final String strCode, final Location location) {
        final Airport newAirport = new Airport(strCode, location);
        airports.add(newAirport);
        return newAirport.getId();
    }

    public Long addFlight(final ZonedDateTime departureTime, final ZonedDateTime arrivalTime, final Long departureAirportId, final Long arrivalAirportId, final Long aircraftId, final Long airCompanyId) {
        final Airport departureAirport = getAirportById(departureAirportId);
        final Airport arrivalAirport = getAirportById(arrivalAirportId);
        final AirCompany airCompany = getAirCompanyById(airCompanyId);
        final Aircraft aircraft = getAircraftById(aircraftId);
        return addFlight(departureTime, arrivalTime, departureAirport, arrivalAirport, aircraft, airCompany);
    }

    private Long addFlight(final ZonedDateTime departureTime, final ZonedDateTime arrivalTime, final Airport departureAirport, final Airport arrivalAirport, final Aircraft aircraft, final AirCompany airCompany) {
        if (departureAirport == null || arrivalAirport == null || airCompany == null || aircraft == null)
            return null;
        final Flight newFlight = new Flight(departureTime, arrivalTime, departureAirport, arrivalAirport, aircraft, airCompany);
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

    public boolean changeFlight(final Long flightId, final ZonedDateTime departureTime, final ZonedDateTime arrivalTime, final Long departureAirportId, final Long arrivalAirportId, final Long aircraftId, final Long airCompanyId) {
        return changeFlight(getFlightById(flightId), departureTime, arrivalTime,
                getAirportById(departureAirportId), getAirportById(arrivalAirportId),
                getAircraftById(aircraftId), getAirCompanyById(airCompanyId));
    }

    private boolean changeFlight(final Flight flight, final ZonedDateTime departureTime, final ZonedDateTime arrivalTime, final Airport departureAirport, final Airport arrivalAirport, final Aircraft aircraft, final AirCompany airCompany) {
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

    public boolean changeAirCompany(final Long airCompanyId, final String name, final double baseEconomyCost, final double baseFirstCost, final double baseBusinessCost, final double allowCancelPercentage, final double percentageMarkupForLastTicket, final double percentageDiscountIfAllCancel, final double pricePerKm, final double coefficientOfFlownKilometers, final double returnPercentageInLess3Day, final double returnPercentageInLess10Day, final double returnPercentageInLess30Day, final double baseReturnPercentage, final double returnPercentageIfFlightCanceled, final double maxUnpaidWeightInKg, final double maxUnpaidSideLengthInCm, final double priceForExtraWeightByKg, final double priceForExtraLengthByCm, final double maxWeightInKg, final double maxSideLengthInCm) {
        final FlightRules newFlightRules = new FlightRules(baseEconomyCost, baseFirstCost, baseBusinessCost, allowCancelPercentage, percentageMarkupForLastTicket, percentageDiscountIfAllCancel, pricePerKm, coefficientOfFlownKilometers, returnPercentageInLess3Day, returnPercentageInLess10Day, returnPercentageInLess30Day, baseReturnPercentage, returnPercentageIfFlightCanceled);
        final LuggageRules newLuggageRules = new LuggageRules(maxUnpaidWeightInKg, maxUnpaidSideLengthInCm, priceForExtraWeightByKg, priceForExtraLengthByCm, maxWeightInKg, maxSideLengthInCm);
        return changeAirCompany(airCompanyId, name, newFlightRules, newLuggageRules);
    }

    public boolean changeAirCompany(final Long airCompanyId, final String name, final FlightRules flightRules, final LuggageRules luggageRules) {
        return changeAirCompany(getAirCompanyById(airCompanyId), name, flightRules, luggageRules);
    }

    private boolean changeAirCompany(final AirCompany airCompany, final String name, final FlightRules flightRules, final LuggageRules luggageRules) {
        if (airCompany == null)
            return false;
        if (flightRules != null)
            airCompany.setFlightPrices(flightRules);
        if (luggageRules != null)
            airCompany.setLuggageRules(luggageRules);
        if (name != null)
            airCompany.setName(name);
        return true;
    }

    public boolean changeAirport(final Long airportId, final String address, final String country, final String city, final Double latitude, final Double longitude, final String utsOffset) {
        return changeAirport(airportId, address, country, city, latitude, longitude, ZoneOffset.of(utsOffset));
    }

    public boolean changeAirport(final Long airportId, final String address, final String country, final String city, final Double latitude, final Double longitude, final ZoneId zoneId) {
        return changeAirport(getAirportById(airportId), address, country, city, latitude, longitude, zoneId);

    }

    private boolean changeAirport(final Airport airport, final String address, final String country, final String city, final Double latitude, final Double longitude, final ZoneId zoneId) {
        if (airport == null)
            return false;
        final Location location = airport.getLocation();
        if (address != null)
            location.setAddress(address);
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

    public boolean deletePassenger(final Long passengerId) {
        Passenger passenger = getPassengerById(passengerId);
        if (passenger == null)
            return false;
        passenger.deleteTickets(defaultPassenger);
        passengers.remove(passenger);
        passenger = defaultPassenger;
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
        if (tickets == null || tickets.size() == 0)
            return "";
        final StringBuilder string = new StringBuilder();
        for (final Ticket ticket : tickets) {
            string.append(ticket.toString()).append("\n");
        }
        return string.toString();
    }

    public boolean deleteAircraft(final Long aircraftId) {
        Aircraft aircraft = getAircraftById(aircraftId);
        if (aircraft == null)
            return false;
        aircrafts.remove(aircraft);
        aircraft = defaultAircraft;
        return true;
    }


    public boolean deleteAirport(final Long airportId) {
        final Airport airport = getAirportById(airportId);
        if (airport == null)
            return false;
        flights.stream().filter(flight -> flight.getArrivalAirport() == airport)
                .forEach(flight -> flight.setArrivalAirport(defaultAirport));
        flights.stream().filter(flight -> flight.getDepartureAirport() == airport)
                .forEach(flight -> flight.setDepartureAirport(defaultAirport));
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
        if (flight.getTickets().size() > 0) {
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
        return flightsToString(this.flights);
    }

    public String flightsToString(final List<Flight> flights) {
        if (flights == null || flights.size() < 1)
            return "Empty flights list";
        final StringBuilder string = new StringBuilder();
        for (final Flight flight : flights) {
            string.append(flight.toString());
            string.append("\n--------------------\n\n");
        }
        return string.substring(0, string.length() - 21);
    }

    public String passengersToString() {
        final StringBuilder string = new StringBuilder();
        for (final Passenger passenger : passengers) {
            string.append(passenger.toString()).append("\n");
        }
        return string.toString();
    }

    public String aircraftsToString() {
        final StringBuilder string = new StringBuilder();
        for (final Aircraft aircraft : aircrafts) {
            string.append(aircraft.toString()).append("\n");
        }
        return string.toString();
    }

    public String airportsToString() {
        final StringBuilder string = new StringBuilder();
        for (final Airport airport : airports) {
            string.append(airport.toString());
            string.append("\n");
            string.append(airport.schedule());
            string.append("\n--------------------\n");
        }
        return string.toString();
    }

    public String airCompaniesToString() {
        final StringBuilder string = new StringBuilder();
        for (final AirCompany airCompany : airCompanies) {
            string.append(airCompany.toString());
            string.append("\n Profit");
            string.append("\n Last year: ");
            string.append(getProfitByTimeAndCompany(airCompany, ZonedDateTime.now().minusMonths(12), ZonedDateTime.now()));
            string.append("\n Last quarter: ");
            string.append(getProfitByTimeAndCompany(airCompany, ZonedDateTime.now().minusMonths(3), ZonedDateTime.now()));
            string.append("\n Total: ");
            string.append(getProfitByCompany(airCompany));
            string.append("\n");

        }
        return string.toString();
    }

    @Override
    public String toString() {
        final String strongDivider = "\n********************\n";
        final String mediumDivider = "\n====================\n";
        return strongDivider
                + "Passengers" +
                mediumDivider +
                passengersToString() +
                strongDivider +

                "Airports" +
                mediumDivider +
                airportsToString() +
                strongDivider +

                "Aircrafts" +
                mediumDivider +
                aircraftsToString() +
                strongDivider +

                "Air Companies" +
                mediumDivider +
                airCompaniesToString() +
                strongDivider +

                "Flights" +
                mediumDivider +
                flightsToString() +
                strongDivider;
    }
}