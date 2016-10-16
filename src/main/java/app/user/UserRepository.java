package app.user;

public interface UserRepository {
    String create(User user);

    User findByEmail(String email);

    User findByUuid(String uuid);
}
