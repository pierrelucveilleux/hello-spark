package app.account;

import java.util.HashMap;
import java.util.Map;

public class MemoryAccountRepository implements AccountRepository {

    private Map<String, Account> accounts = new HashMap<>();

    @Override
    public Account findAccount(String id) {
        return accounts.get(id);
    }

    @Override
    public void create(String id, String name) {
        accounts.put(id, new Account(id, name));
    }
}
