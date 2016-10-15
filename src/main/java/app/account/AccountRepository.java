package app.account;

public interface AccountRepository {

    Account find(String id);

    String create(PricingModel pricingModel);

    void remove(String id);

    void update(Account account);
}
