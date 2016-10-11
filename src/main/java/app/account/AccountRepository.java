package app.account;

public interface AccountRepository {

    Account find(String id);

    String create(Account.PricingModel pricingModel);
}
