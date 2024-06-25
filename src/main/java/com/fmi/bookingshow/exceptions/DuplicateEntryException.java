package com.fmi.bookingshow.exceptions;

public class DuplicateEntryException extends Exception {
    public DuplicateEntryException(String customMessage) {
        super(String.format("%s - %s", ErrorMessage.DUPLICATE_ENTRY, customMessage));
    }
}
