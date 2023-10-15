package task3;

public class AirCompany {
    private final static IdGenerator idGenerator = new IdGenerator();
    private final Long id;
    private String name;
    private FlightPrices flightPrices;
    private LuggageRules luggageRules;
    public AirCompany(final String name, final FlightPrices flightPrices, final LuggageRules luggageRules) {
        this.id = idGenerator.getId();
        this.name = name;
        this.flightPrices = flightPrices;
        this.luggageRules = luggageRules;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public FlightPrices getFlightPrices() {
        return flightPrices;
    }

    public void setFlightPrices(final FlightPrices flightPrices) {
        this.flightPrices = flightPrices;
    }

    public LuggageRules getLuggageRules() {
        return luggageRules;
    }

    public void setLuggageRules(final LuggageRules luggageRules) {
        this.luggageRules = luggageRules;
    }

    @Override
    public String toString() {
        return "AirCompany " +
                "id: " + id +
                ", " + name ;
    }
}

