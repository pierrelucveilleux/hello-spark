package app.assignment;

public class UserAssignment {

    private final String userId;
    private final String accountId;

    public UserAssignment(String userId, String accountId) {
        this.userId = userId;
        this.accountId = accountId;
    }

    public String userId() {
        return userId;
    }

    public String accountId() {
        return accountId;
    }

    @Override
    public String toString() {
        return "UserAssignment{" +
                "userId='" + userId + '\'' +
                ", accountId='" + accountId + '\'' +
                '}';
    }
}
