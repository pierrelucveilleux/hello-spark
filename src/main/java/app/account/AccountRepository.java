package app.account;

public interface AccountRepository {

    Account findAccount(String id);

    void create(String id, String name);
}
