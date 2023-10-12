package task3;

public class AirCompany {
    private String name;
    private FlightPrices flightPrices;
    private LuggageRules luggageRules;
    public AirCompany(final String name, final FlightPrices flightPrices, final LuggageRules luggageRules) {
        this.name = name;
        this.flightPrices = flightPrices;
        this.luggageRules = luggageRules;
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
}

