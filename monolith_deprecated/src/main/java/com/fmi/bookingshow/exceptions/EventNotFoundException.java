package com.fmi.bookingshow.exceptions;

public class EventNotFoundException extends Exception{
    public EventNotFoundException(String customMessage) {
        super(String.format("%s - %s", ErrorMessage.EVENT_NOT_FOUND, customMessage));
    }

    public EventNotFoundException() {
        super(ErrorMessage.EVENT_NOT_FOUND);
    }
}
