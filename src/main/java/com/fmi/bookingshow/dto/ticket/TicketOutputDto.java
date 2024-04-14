package com.fmi.bookingshow.dto.ticket;

import java.util.Date;

public class TicketOutputDto extends TicketOrderDto {
    public long ticketId;
    public Date purchaseDate;

    public TicketOutputDto() {
    }

    public TicketOutputDto(long eventId, int seatRow, int seatNumber, long ticketId, Date purchaseDate) {
        super(eventId, seatRow, seatNumber);
        this.ticketId = ticketId;
        this.purchaseDate = purchaseDate;
    }
}
