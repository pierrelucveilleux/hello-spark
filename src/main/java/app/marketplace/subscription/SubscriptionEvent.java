package app.marketplace.subscription;

import app.marketplace.User;

public class SubscriptionEvent {

    public String type;
    public MarketPlace marketplace;
    public User creator;
    public PayLoad payload;
    public String returnUrl;
}
