package app.account;

public class Account {

    enum PricingModel {
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
    }

    private final String id;
    private final PricingModel pricingModel;

    public Account(String id, PricingModel pricingModel) {
        this.id = id;
        this.pricingModel = pricingModel;
    }
}
