package app.account;

public class Account {

    enum PricingModel {
        Free,
        Premium
    }

    private final String id;
    private final PricingModel pricingModel;

    public Account(String id, PricingModel pricingModel) {
        this.id = id;
        this.pricingModel = pricingModel;
    }
}
