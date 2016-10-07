package app.account;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryAccountRepository implements AccountRepository {

    private Map<String, Account> accounts = new HashMap<>();

    @Override
    public Account findAccount(String id) {
        return accounts.get(id);
    }

    @Override
    public String create(Account.PricingModel pricingModel) {
        String id = UUID.randomUUID().toString();
        accounts.put(id, new Account(id, pricingModel));
        return id;
    }
}
