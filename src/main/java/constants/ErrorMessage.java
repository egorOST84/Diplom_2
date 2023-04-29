package constants;

public enum ErrorMessage {
    // User
    USER_CREATE_SECOND_ATTEMPT_ERROR_403("User already exists"),
    USER_CREATE_MISSING_FIELDS_ERROR_403("Email, password and name are required fields"),
    USER_LOGIN_UNAUTHORIZED_ERROR_401("email or password are incorrect"),
    USER_UPDATE_UNAUTHORIZED_ERROR_401("You should be authorised"),
    USER_UPDATE_EXISTING_EMAIL_ERROR_403("User with such email already exists");
    private final String description;

    ErrorMessage(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
