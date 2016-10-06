package app.domain;

public class ApiResult {

    private final String accountIdentifier;
    private final boolean success;

    public ApiResult(String accountIdentifier, boolean success) {
        this.accountIdentifier = accountIdentifier;
        this.success = success;
    }
}
