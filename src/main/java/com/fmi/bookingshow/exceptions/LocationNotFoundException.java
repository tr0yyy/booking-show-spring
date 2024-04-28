package com.fmi.bookingshow.exceptions;

public class LocationNotFoundException extends Exception {
    public LocationNotFoundException(String message) {
        super(String.format("%s - %s", ErrorMessage.LOCATION_NOT_FOUND, message));
    }
}
