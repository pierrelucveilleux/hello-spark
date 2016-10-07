package app.domain;

public class User {

    private final String uuid;
    private final String fullName;
    private final String email;
    private final String openIdUrl;

    public User(String uuid, String fullName, String email, String openIdUrl) {
        this.uuid = uuid;
        this.fullName = fullName;
        this.email = email;
        this.openIdUrl = openIdUrl;
    }
}
