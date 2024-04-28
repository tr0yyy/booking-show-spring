package com.fmi.bookingshow.exceptions;

public class ArtistNotFoundException extends Exception {
    public ArtistNotFoundException(String message) {
        super(String.format("%s - %s", ErrorMessage.ARTIST_NOT_FOUND, message));
    }
}
