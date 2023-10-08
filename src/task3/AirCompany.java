package task3;

public class AirCompany {
    private String name;
    //rules
    private double baseEconomyCost;
    private double baseFirstCost;
    private double baseBusinessCost;
    private double percentageMarkupForLastTicket;
    private double pricePerKm;
    private double returnPercentageInLess3Day;
    private double returnPercentageInLess10Day;
    private double returnPercentageInLess20Day;
    private double baseReturnPercentage;
    private double returnPercentageIfFlightCanceled;
    private double maxUnpaidWeightInKg;
    private double maxUnpaidSideLengthInCm;
    private double priceForExtraWeightByKg;
    private double priceForExtraLengthByCm;
    private double maxWeightInKg;
    private double maxSideLengthInCm;
    public AirCompany(final String name, final double baseEconomyCost, final double baseFirstCost, final double baseBusinessCost, final double percentageMarkupForLastTicket, final double pricePerKm, final double returnPercentageInLess3Day, final double returnPercentageInLess10Day, final double returnPercentageInLess20Day, final double baseReturnPercentage) {
        this.name = name;
        this.baseEconomyCost = baseEconomyCost;
        this.baseFirstCost = baseFirstCost;
        this.baseBusinessCost = baseBusinessCost;
        this.percentageMarkupForLastTicket = percentageMarkupForLastTicket;
        this.pricePerKm = pricePerKm;
        this.returnPercentageInLess3Day = returnPercentageInLess3Day;
        this.returnPercentageInLess10Day = returnPercentageInLess10Day;
        this.returnPercentageInLess20Day = returnPercentageInLess20Day;
        this.baseReturnPercentage = baseReturnPercentage;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public double getBaseEconomyCost() {
        return baseEconomyCost;
    }

    public void setBaseEconomyCost(final double baseEconomyCost) {
        this.baseEconomyCost = baseEconomyCost;
    }

    public double getBaseFirstCost() {
        return baseFirstCost;
    }

    public void setBaseFirstCost(final double baseFirstCost) {
        this.baseFirstCost = baseFirstCost;
    }

    public double getBaseBusinessCost() {
        return baseBusinessCost;
    }

    public void setBaseBusinessCost(final double baseBusinessCost) {
        this.baseBusinessCost = baseBusinessCost;
    }

    public double getPercentageMarkupForLastTicket() {
        return percentageMarkupForLastTicket;
    }

    public void setPercentageMarkupForLastTicket(final double percentageMarkupForLastTicket) {
        this.percentageMarkupForLastTicket = percentageMarkupForLastTicket;
    }

    public double getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(final double pricePerKm) {
        this.pricePerKm = pricePerKm;
    }

    public double getReturnPercentageInLess3Day() {
        return returnPercentageInLess3Day;
    }

    public void setReturnPercentageInLess3Day(final double returnPercentageInLess3Day) {
        this.returnPercentageInLess3Day = returnPercentageInLess3Day;
    }

    public double getReturnPercentageInLess10Day() {
        return returnPercentageInLess10Day;
    }

    public void setReturnPercentageInLess10Day(final double returnPercentageInLess10Day) {
        this.returnPercentageInLess10Day = returnPercentageInLess10Day;
    }

    public double getReturnPercentageInLess20Day() {
        return returnPercentageInLess20Day;
    }

    public void setReturnPercentageInLess20Day(final double returnPercentageInLess20Day) {
        this.returnPercentageInLess20Day = returnPercentageInLess20Day;
    }

    public double getBaseReturnPercentage() {
        return baseReturnPercentage;
    }

    public void setBaseReturnPercentage(final double baseReturnPercentage) {
        this.baseReturnPercentage = baseReturnPercentage;
    }

    public double getReturnPercentageIfFlightCanceled() {
        return returnPercentageIfFlightCanceled;
    }

    public void setReturnPercentageIfFlightCanceled(final double returnPercentageIfFlightCanceled) {
        this.returnPercentageIfFlightCanceled = returnPercentageIfFlightCanceled;
    }

    public double getMaxUnpaidWeightInKg() {
        return maxUnpaidWeightInKg;
    }

    public void setMaxUnpaidWeightInKg(final double maxUnpaidWeightInKg) {
        this.maxUnpaidWeightInKg = maxUnpaidWeightInKg;
    }

    public double getMaxUnpaidSideLengthInCm() {
        return maxUnpaidSideLengthInCm;
    }

    public void setMaxUnpaidSideLengthInCm(final double maxUnpaidSideLengthInCm) {
        this.maxUnpaidSideLengthInCm = maxUnpaidSideLengthInCm;
    }

    public double getPriceForExtraWeightByKg() {
        return priceForExtraWeightByKg;
    }

    public void setPriceForExtraWeightByKg(final double priceForExtraWeightByKg) {
        this.priceForExtraWeightByKg = priceForExtraWeightByKg;
    }

    public double getPriceForExtraLengthByCm() {
        return priceForExtraLengthByCm;
    }

    public void setPriceForExtraLengthByCm(final double priceForExtraLengthByCm) {
        this.priceForExtraLengthByCm = priceForExtraLengthByCm;
    }

    public double getMaxWeightInKg() {
        return maxWeightInKg;
    }

    public void setMaxWeightInKg(final double maxWeightInKg) {
        this.maxWeightInKg = maxWeightInKg;
    }

    public double getMaxSideLengthInCm() {
        return maxSideLengthInCm;
    }

    public void setMaxSideLengthInCm(final double maxSideLengthInCm) {
        this.maxSideLengthInCm = maxSideLengthInCm;
    }
}
