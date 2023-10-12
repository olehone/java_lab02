package task3;

public class ScheduleRules {
    private int howManyHoursShowFlight;
    private int howManyDaysSaveFlights;
    //default
    public ScheduleRules() {
        this(6, 30);
    }
    public ScheduleRules(final int howManyHoursShowFlight, final int howManyDaysSaveFlights) {
        this.howManyHoursShowFlight = howManyHoursShowFlight;
        this.howManyDaysSaveFlights = howManyDaysSaveFlights;
    }
    public void setHowLongSaveFlights(final int howManyDaysSaveFlights) {
        this.howManyDaysSaveFlights = howManyDaysSaveFlights;
    }
    public void setHowManyHoursShowFlight(final int howManyHoursShowFlight) {
        this.howManyHoursShowFlight = howManyHoursShowFlight;
    }

    public int getHowManyHoursShowFlight() {
        return howManyHoursShowFlight;
    }

    public int getHowManyDaysSaveFlights() {
        return howManyDaysSaveFlights;
    }
}
