package task3.Rules;

public class FlightRules {
    private double baseEconomyCost;
    private double baseFirstCost;
    private double baseBusinessCost;
    private double allowCancelPercentage;
    private double percentageMarkupForLastTicket;
    private double percentageDiscountIfAllCancel;
    private double pricePerKm;
    private double coefficientOfFlownKilometers;
    private double returnPercentageInLess3Day;
    private double returnPercentageInLess10Day;
    private double returnPercentageInLess30Day;
    private double baseReturnPercentage;
    private double returnPercentageIfFlightCanceled;
    public FlightRules(final double baseEconomyCost, final double baseFirstCost, final double baseBusinessCost, final double allowCancelPercentage, final double percentageMarkupForLastTicket, final double percentageDiscountIfAllCancel, final double pricePerKm, final double coefficientOfFlownKilometers, final double returnPercentageInLess3Day, final double returnPercentageInLess10Day, final double returnPercentageInLess30Day, final double baseReturnPercentage, final double returnPercentageIfFlightCanceled) {
        this.baseEconomyCost = baseEconomyCost;
        this.baseFirstCost = baseFirstCost;
        this.baseBusinessCost = baseBusinessCost;
        this.allowCancelPercentage = allowCancelPercentage;
        this.percentageMarkupForLastTicket = percentageMarkupForLastTicket;
        this.percentageDiscountIfAllCancel = percentageDiscountIfAllCancel;
        this.pricePerKm = pricePerKm;
        this.coefficientOfFlownKilometers = coefficientOfFlownKilometers;
        this.returnPercentageInLess3Day = returnPercentageInLess3Day;
        this.returnPercentageInLess10Day = returnPercentageInLess10Day;
        this.returnPercentageInLess30Day = returnPercentageInLess30Day;
        this.baseReturnPercentage = baseReturnPercentage;
        this.returnPercentageIfFlightCanceled = returnPercentageIfFlightCanceled;
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

    public double getAllowCancelPercentage() {
        return allowCancelPercentage;
    }

    public void setAllowCancelPercentage(final double allowCancelPercentage) {
        this.allowCancelPercentage = allowCancelPercentage;
    }

    public double getPercentageMarkupForLastTicket() {
        return percentageMarkupForLastTicket;
    }

    public void setPercentageMarkupForLastTicket(final double percentageMarkupForLastTicket) {
        this.percentageMarkupForLastTicket = percentageMarkupForLastTicket;
    }

    public double getPercentageDiscountIfAllCancel() {
        return percentageDiscountIfAllCancel;
    }

    public void setPercentageDiscountIfAllCancel(final double percentageDiscountIfAllCancel) {
        this.percentageDiscountIfAllCancel = percentageDiscountIfAllCancel;
    }

    public double getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(final double pricePerKm) {
        this.pricePerKm = pricePerKm;
    }

    public double getCoefficientOfFlownKilometers() {
        return coefficientOfFlownKilometers;
    }

    public void setCoefficientOfFlownKilometers(final double coefficientOfFlownKilometers) {
        this.coefficientOfFlownKilometers = coefficientOfFlownKilometers;
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

    public double getReturnPercentageInLess30Day() {
        return returnPercentageInLess30Day;
    }

    public void setReturnPercentageInLess30Day(final double returnPercentageInLess30Day) {
        this.returnPercentageInLess30Day = returnPercentageInLess30Day;
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
}
