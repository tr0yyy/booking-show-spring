package fmi.user_service.exceptions;

public class LoginFailedException extends Exception {
    public LoginFailedException(String customMessage) {
        super(String.format("%s - %s", ErrorMessage.LOGIN_FAILED, customMessage));
    }
}
