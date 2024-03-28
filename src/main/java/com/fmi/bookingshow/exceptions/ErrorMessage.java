package com.fmi.bookingshow.exceptions;

public interface ErrorMessage {
    String IMPORT_ENTRY_FAILED = "Import entry failed - ";
    String DUPLICATE_ENTRY = IMPORT_ENTRY_FAILED + "there is already an entry in database!";
}
