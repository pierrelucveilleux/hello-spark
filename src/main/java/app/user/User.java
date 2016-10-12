package app.user;

public class User {

    private String id;
    private final String name;
    private final String email;

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void id(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String email() {
        return email;
    }
}
