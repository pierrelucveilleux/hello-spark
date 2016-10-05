package app.auth;

public interface AuthenticationService {

    boolean authenticate(String username, String password);
}
