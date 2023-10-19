package task3.Rules;

public class LuggageRules {
    private double maxUnpaidWeightInKg;
    private double maxUnpaidSideLengthInCm;
    private double priceForExtraWeightByKg;
    private double priceForExtraLengthByCm;
    private double maxWeightInKg;
    private double maxSideLengthInCm;

    public LuggageRules(final double maxUnpaidWeightInKg, final double maxUnpaidSideLengthInCm, final double priceForExtraWeightByKg, final double priceForExtraLengthByCm, final double maxWeightInKg, final double maxSideLengthInCm) {
        this.maxUnpaidWeightInKg = maxUnpaidWeightInKg;
        this.maxUnpaidSideLengthInCm = maxUnpaidSideLengthInCm;
        this.priceForExtraWeightByKg = priceForExtraWeightByKg;
        this.priceForExtraLengthByCm = priceForExtraLengthByCm;
        this.maxWeightInKg = maxWeightInKg;
        this.maxSideLengthInCm = maxSideLengthInCm;
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
