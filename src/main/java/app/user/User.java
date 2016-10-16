package app.user;

public class User {

    private String uuid;
    private String accountId;
    private final String openid;
    private final String firstName;
    private final String lastName;
    private final String email;

    public User(String uuid, String openid, String firstName, String lastName, String email) {
        this.uuid = uuid;
        this.openid = openid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String uuid() {
        return uuid;
    }

    public String openid() {
        return openid;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public String email() {
        return email;
    }

    public String accountId() {
        return accountId;
    }

    public void accountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid='" + uuid + '\'' +
                ", openid='" + openid + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
