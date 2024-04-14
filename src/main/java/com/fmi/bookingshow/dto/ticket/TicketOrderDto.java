package com.fmi.bookingshow.dto.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TicketOrderDto {
    @NotNull
    @NotBlank
    public long eventId;
    @NotNull
    @NotBlank
    public int seatRow;
    @NotNull
    @NotBlank
    public int seatNumber;

    public TicketOrderDto() {
    }

    public TicketOrderDto(long eventId, int seatRow, int seatNumber) {
        this.eventId = eventId;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
    }
}
