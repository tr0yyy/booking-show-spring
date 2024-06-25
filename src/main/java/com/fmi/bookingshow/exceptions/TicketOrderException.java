package com.fmi.bookingshow.exceptions;

public class TicketOrderException extends Exception{
    public TicketOrderException(String customMessage) {
        super(String.format("%s - %s", ErrorMessage.TICKET_ORDER_FAILED, customMessage));
    }
}
