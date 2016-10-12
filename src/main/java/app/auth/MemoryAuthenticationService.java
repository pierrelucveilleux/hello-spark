package app.auth;

import java.util.HashMap;
import java.util.Map;

public class MemoryAuthenticationService implements AuthenticationService {

    private Map<String, String> users = new HashMap<>();

    public MemoryAuthenticationService() {
        this.users.put("admin@test.com", "admin");
        this.users.put("pierrelucveilleux@gmail.com", "pluc");
    }

    @Override
    public boolean authenticate(String email, String password) {
        return users.containsKey(email) && users.get(email).equals(password);
    }
}
