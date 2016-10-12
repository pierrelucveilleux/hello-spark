package app.user;

interface UserRepository {

    String create(User user);

    User findByEmail(String email);
}
