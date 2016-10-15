package app.account;

public enum PricingModel {
    Free,
    Premium;

    public static PricingModel fromValue(String value) {
        for(PricingModel pricing : PricingModel.values()) {
            if(value.toLowerCase().equals(pricing.name().toLowerCase())) {
                return pricing;
            }
        }
        throw new IllegalArgumentException("Cannot parse pricing model: " + value);
    }

    public String toValue() {
        return name().toUpperCase();
    }
}
