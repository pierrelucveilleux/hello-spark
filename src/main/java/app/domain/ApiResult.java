package app.domain;

public class ApiResult {

    private boolean success;
    private String accountIdentifier;
    private String errorCode;
    private String errorMessage;

    private ApiResult(boolean success, String accountIdentifier, String errorCode, String errorMessage) {
        this.success = success;
        this.accountIdentifier = accountIdentifier;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public static ApiResult succesUser() {
        return new ApiResult(true, null, null, null);
    }

    public static ApiResult succesAccount(String accountIdentifier) {
        return new ApiResult(true, accountIdentifier, null, null);
    }

    public static ApiResult error(String errorCode, String errorMessage) {
        return new ApiResult(false, null, errorCode, errorMessage);
    }
}
