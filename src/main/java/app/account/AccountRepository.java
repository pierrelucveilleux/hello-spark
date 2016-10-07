package app.account;

public interface AccountRepository {

    Account findAccount(String id);

    String create(Account.PricingModel pricingModel);
}
