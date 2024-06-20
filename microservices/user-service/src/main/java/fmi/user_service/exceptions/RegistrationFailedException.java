package fmi.user_service.exceptions;

public class RegistrationFailedException extends Exception {
    public RegistrationFailedException(String customMessage) {
        super(String.format("%s - %s", ErrorMessage.REGISTRATION_FAILED, customMessage));
    }
}
