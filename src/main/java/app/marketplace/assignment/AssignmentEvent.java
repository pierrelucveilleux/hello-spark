package app.marketplace.assignment;

import app.marketplace.User;
import app.marketplace.subscription.MarketPlace;

public class AssignmentEvent {
    public String type;
    public MarketPlace marketplace;
    public User creator;
    public PayLoad payload;
}
