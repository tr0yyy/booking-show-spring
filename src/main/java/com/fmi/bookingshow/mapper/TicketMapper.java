package com.fmi.bookingshow.mapper;

import com.fmi.bookingshow.dto.ticket.TicketOutputDto;
import com.fmi.bookingshow.model.TicketEntity;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    public TicketOutputDto ticketEntityToTicketOutput(TicketEntity ticketEntity) {
        return new TicketOutputDto(
                ticketEntity.getEvent().getEventId(),
                ticketEntity.getSeat().getSeatRowNumber(),
                ticketEntity.getSeat().getSeatNumber(),
                ticketEntity.getTicketId(),
                ticketEntity.getPurchaseDate()
        );
    }
}
