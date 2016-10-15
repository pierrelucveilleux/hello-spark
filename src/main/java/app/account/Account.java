package app.account;

public class Account {

    private final String id;
    private PricingModel pricingModel;

    public Account(String id, PricingModel pricingModel) {
        this.id = id;
        this.pricingModel = pricingModel;
    }

    public String id() {
        return id;
    }

    public PricingModel pricingModel() {
        return pricingModel;
    }

    public void pricingModel(PricingModel pricingModel) {
        this.pricingModel = pricingModel;
    }
}
