package com.fmi.bookingshow.dto.ticket;

import com.fmi.bookingshow.utils.DateUtils;

import java.util.Date;

public class TicketOutputDto extends TicketOrderDto {
    public long ticketId;
    public String purchaseDate;
    public long userId;

    public TicketOutputDto() {
    }

    public TicketOutputDto(long eventId, int seatRow, int seatNumber, long ticketId, Date purchaseDate, long userId) {
        super(eventId, seatRow, seatNumber);
        this.ticketId = ticketId;
        this.purchaseDate = DateUtils.formatDate(purchaseDate);
        this.userId = userId;
    }
}
