package app.marketplace.subscription;

public class SubscriptionEvent {

    public String type;
    public MarketPlace marketplace;
    public Creator creator;
    public PayLoad payload;
    public String returnUrl;
}
