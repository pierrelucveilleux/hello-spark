package app.auth;

import java.util.HashMap;
import java.util.Map;

public class MemoryAuthenticationService implements AuthenticationService {

    private Map<String, String> users = new HashMap<>();

    public MemoryAuthenticationService() {
        this.users.put("admin", "admin");
    }

    @Override
    public boolean authenticate(String username, String password) {
        return false;
    }
}
