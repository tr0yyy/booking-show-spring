package com.fmi.bookingshow.exceptions;

public class RegistrationFailedException extends Exception {
    public RegistrationFailedException(String customMessage) {
        super(String.format("%s - %s", ErrorMessage.REGISTRATION_FAILED, customMessage));
    }
}
