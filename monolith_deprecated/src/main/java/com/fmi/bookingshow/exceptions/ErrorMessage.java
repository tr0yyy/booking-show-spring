package com.fmi.bookingshow.exceptions;

public interface ErrorMessage {
    String INTERNAL_SERVER_ERROR = "Internal server error";
    String IMPORT_ENTRY_FAILED = "Import entry failed ";
    String DUPLICATE_ENTRY = IMPORT_ENTRY_FAILED + "there is already an entry in database!";
    String REGISTRATION_FAILED = "Registration failed";
    String LOGIN_FAILED = "Login failed";
    String TICKET_ORDER_FAILED = "Ticket order failed";
    String EVENT_NOT_FOUND = "Event not found";
    String ARTIST_NOT_FOUND = "Artist not found";
    String LOCATION_NOT_FOUND = "Location not found";
    String OPERATION_NOT_PERMITTED = "Operation not permitted";
}
