package task3.Identified;

import task3.IdGenerator;
import task3.Interfaces.HasId;
import task3.Rules.LuggageRules;

public class HandLuggage implements HasId {
    private static final IdGenerator idGenerator = new IdGenerator();
    private final Long id;
    private Ticket ticket;
    private double weight;
    private int lengthInCm;
    private int widthInCm;
    private int heightInCm;
    private double additionalPrice;

    public HandLuggage() {
        this.id = idGenerator.createId();
    }

    public HandLuggage(final Ticket ticket) {
        this.id = idGenerator.createId();
        this.ticket = ticket;
        additionalPrice = 0;
    }

    public HandLuggage(final double weight, final int length, final int width, final int height, final Ticket ticket, final double maxUnpaidSideLengthInCm, final double maxUnpaidWeightInKg, final double priceForExtraLengthByCm, final double priceForExtraWeightByKg) {
        this.ticket = ticket;
        this.id = idGenerator.createId();
        if (!isAllowed(weight, length, width, height, maxUnpaidWeightInKg, maxUnpaidSideLengthInCm)) {
            this.additionalPrice = 0;
            return;
        }
        this.weight = weight;
        this.lengthInCm = length;
        this.widthInCm = width;
        this.heightInCm = height;
        this.additionalPrice = calculatePrice(weight, length, width, height, maxUnpaidWeightInKg, maxUnpaidSideLengthInCm, priceForExtraWeightByKg, priceForExtraLengthByCm);
    }

    public HandLuggage(final double weight, final int length, final int width, final int height, final Ticket ticket, final LuggageRules luggageRules) {
        this.ticket = ticket;
        this.id = idGenerator.createId();
        if (!isAllowed(weight, length, width, height, luggageRules)) {
            this.additionalPrice = 0;
            return;
        }
        this.weight = weight;
        this.lengthInCm = length;
        this.widthInCm = width;
        this.heightInCm = height;
        this.additionalPrice = calculatePrice(weight, length, width, height, luggageRules);
    }
    public HandLuggage(final double weight, final int length, final int width, final int height, final Ticket ticket) {
        this(weight, length, width, height, ticket, ticket.getFlight().getAirCompany().getLuggageRules());
    }
    public double cancel() {
        final double returnAdditionalPrice = additionalPrice;
        this.weight = 0;
        this.heightInCm = 0;
        this.lengthInCm = 0;
        this.widthInCm = 0;
        this.additionalPrice = 0;
        return returnAdditionalPrice;
    }
    public boolean changeSize(final double weight, final int length, final int width, final int height, final boolean canPayMore, final AirCompany airCompany) {
        if (airCompany == null) {
            throw new NullPointerException("Can`t change price! Air company is null");
        }
        return changeSize(weight, length, width, height, canPayMore, airCompany.getLuggageRules());
    }
    public boolean changeSize(final double weight, final int length, final int width, final int height, final boolean canPayMore, final LuggageRules luggageRules) {
        if (luggageRules == null) {
            throw new NullPointerException("Can`t change price! Luggage rules is null");
        }
        final double maxUnpaidSideLengthInCm = luggageRules.getMaxUnpaidSideLengthInCm();
        final double maxUnpaidWeightInKg = luggageRules.getMaxUnpaidWeightInKg();
        final double priceForExtraLengthByCm = luggageRules.getPriceForExtraLengthByCm();
        final double priceForExtraWeightByKg = luggageRules.getPriceForExtraWeightByKg();
        return changeSize(weight, length, width, height, canPayMore, maxUnpaidWeightInKg, maxUnpaidSideLengthInCm, priceForExtraWeightByKg, priceForExtraLengthByCm);
    }


    public boolean changeSize(final double weight, final int length, final int width, final int height, final boolean canPayMore, final double maxUnpaidWeightInKg, final double maxUnpaidSideLengthInCm, final double priceForExtraWeightByKg, final double priceForExtraLengthByCm) {
        if (!isAllowed(weight, length, width, height, maxUnpaidWeightInKg, maxUnpaidSideLengthInCm)) {
            additionalPrice = 0;
            return false;
        }
        if (isUnpaid(weight, length, width, height, maxUnpaidWeightInKg, maxUnpaidSideLengthInCm)) {
            additionalPrice = 0;
            return true;
        }
        if (!isUnpaid(weight, length, width, height, maxUnpaidWeightInKg, maxUnpaidSideLengthInCm) && !canPayMore)
            return false;
        final Double calculatePrice = calculatePrice(weight, length, width, height, maxUnpaidWeightInKg, maxUnpaidSideLengthInCm, priceForExtraWeightByKg, priceForExtraLengthByCm);
        if (calculatePrice == null) {
            additionalPrice = 0;
            return false;
        }
        additionalPrice = calculatePrice;
        return true;
    }

    public Double calculatePrice(final double weight, final int length, final int width, final int height, final AirCompany airCompany) {

        if (airCompany == null) {
            throw new NullPointerException("Can`t calculate price! Air company is null");
        }

        return calculatePrice(weight, length, width, height, airCompany.getLuggageRules());
    }
    public Double calculatePrice(final double weight, final int length, final int width, final int height, final LuggageRules luggageRules) {

        if (luggageRules == null) {
            throw new NullPointerException("Can`t calculate price! Luggage rules is null");
        }
        final double maxUnpaidWeightInKg = luggageRules.getMaxUnpaidWeightInKg();
        final double maxUnpaidSideLengthInCm = luggageRules.getMaxUnpaidSideLengthInCm();
        final double priceForExtraWeightByKg = luggageRules.getPriceForExtraWeightByKg();
        final double priceForExtraLengthByCm = luggageRules.getPriceForExtraLengthByCm();
        return calculatePrice(weight, length, width, height, maxUnpaidWeightInKg, maxUnpaidSideLengthInCm, priceForExtraWeightByKg, priceForExtraLengthByCm);
    }

    public Double calculatePrice(final double weight, final int length, final int width, final int height, final double maxUnpaidWeightInKg, final double maxUnpaidSideLengthInCm, final double priceForExtraWeightByKg, final double priceForExtraLengthByCm) {
        double price = 0.;
        if (isAllowed(weight, length, width, height, maxUnpaidWeightInKg, maxUnpaidSideLengthInCm)) {
            System.out.println("It`s luggage not allowed!");
            return null;
        }
        if (isUnpaid(weight, length, width, height, maxUnpaidWeightInKg, maxUnpaidSideLengthInCm))
            return 0.;
        else {
            if (weight > maxUnpaidWeightInKg)
                price += (weight - maxUnpaidWeightInKg) * priceForExtraWeightByKg;
            if (length > maxUnpaidSideLengthInCm)
                price += (length - maxUnpaidWeightInKg) * priceForExtraLengthByCm;
            if (width > maxUnpaidSideLengthInCm)
                price += (width - maxUnpaidWeightInKg) * priceForExtraLengthByCm;
            if (height > maxUnpaidSideLengthInCm)
                price += (height - maxUnpaidWeightInKg) * priceForExtraLengthByCm;
        }
        return price;
    }

    public boolean isUnpaid(final double weight, final int length, final int width, final int height, final double maxUnpaidWeightInKg, final double maxUnpaidSideLengthInCm) {
        if (weight > maxUnpaidWeightInKg)
            return false;
        if (length > maxUnpaidSideLengthInCm || width > maxUnpaidSideLengthInCm || height > maxUnpaidSideLengthInCm)
            return false;
        return true;
    }

    public boolean isUnpaid(final double weight, final int length, final int width, final int height, final AirCompany airCompany) {
        if (airCompany == null) {
            throw new NullPointerException("Can`t calculate price! Air company is null");
        }
        return isUnpaid(weight, length, width, height, airCompany.getLuggageRules());
    }
    public boolean isUnpaid(final double weight, final int length, final int width, final int height, final LuggageRules luggageRules) {
        if (luggageRules == null) {
            throw new NullPointerException("Can`t calculate price! Luggage rules is null");
        }
        final double maxUnpaidWeightInKg = luggageRules.getMaxUnpaidWeightInKg();
        final double maxUnpaidSideLengthInCm = luggageRules.getMaxUnpaidSideLengthInCm();
        return isUnpaid(weight, length, width, height, maxUnpaidWeightInKg, maxUnpaidSideLengthInCm);
    }

    //with additional pay
    public boolean isAllowed(final double weight, final int length, final int width, final int height, final double maxWeightInKg, final double maxSideLengthInCm) {
        if (weight > maxWeightInKg)
            return false;
        if (length > maxSideLengthInCm || width > maxSideLengthInCm || height > maxSideLengthInCm)
            return false;
        return true;
    }

    public boolean isAllowed(final double weight, final int length, final int width, final int height, final AirCompany airCompany) {
        if(airCompany == null)
            throw new NullPointerException("Can`t calculate price! Air company is null");
        return isAllowed(weight, length, width, height, airCompany.getLuggageRules());
    }
    public boolean isAllowed(final double weight, final int length, final int width, final int height, final LuggageRules luggageRules) {
        if (luggageRules == null) {
            throw new NullPointerException("Can`t calculate price! Luggage rules is null");
        }
        final double maxWeightInKg = luggageRules.getMaxWeightInKg();
        final double maxSideLengthInCm = luggageRules.getMaxSideLengthInCm();
        return isAllowed(weight, length, width, height, maxWeightInKg, maxSideLengthInCm);
    }
    public Long getId() {
        return id;
    }

    public double getWeight() {
        return weight;
    }

    public int getLengthInCm() {
        return lengthInCm;
    }

    public int getWidthInCm() {
        return widthInCm;
    }

    public int getHeightInCm() {
        return heightInCm;
    }

    public double getAdditionalPrice() {
        return additionalPrice;
    }

    public void setAdditionalPrice(final double additionalPrice) {
        this.additionalPrice = additionalPrice;
    }

    @Override
    public String toString() {
        String departureCity;
        String arrivalCity;
        String arrivalTime;
        String passenger;
        String ticketId;
        try {
            departureCity = ticket.getFlight().getDepartureAirport().getLocation().getCity();
        } catch (final NullPointerException e) {
            departureCity = "Unknown";
            System.out.println("Failed to retrieve city information\n" + e.getMessage());
        }
        try {
            arrivalCity = ticket.getFlight().getArrivalAirport().getLocation().getCity();
        } catch (final NullPointerException e) {
            arrivalCity = "Unknown";
            System.out.println("Failed to retrieve city information\n" + e.getMessage());
        }
        try {
            arrivalTime = ticket.getFlight().getArrivalTime().toString();
        } catch (final NullPointerException e) {
            arrivalTime = "Unknown";
            System.out.println("Failed to retrieve time information\n" + e.getMessage());
        }
        try {
            passenger = ticket.getPassenger().toString();
        } catch (final NullPointerException e) {
            passenger = "Unknown";
            System.out.println("Failed to retrieve passenger information\n" + e.getMessage());
        }
        try {
            ticketId = ticket.getId().toString();
        } catch (final NullPointerException e) {
            ticketId = "Unknown";
            System.out.println("Failed to retrieve ticket id information\n" + e.getMessage());
        }
        return " Hand luggage " + " " +
                "\n id: " + id +
                "\n " + departureCity + "-" + arrivalCity +
                "\n arrival time: " + arrivalTime +
                "\n" + passenger +
                "\n ticket id: " + ticketId + "\n";
    }
}

