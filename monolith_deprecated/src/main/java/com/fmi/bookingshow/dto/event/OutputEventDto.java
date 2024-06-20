package com.fmi.bookingshow.dto.event;

import com.fmi.bookingshow.dto.artist.ArtistDto;
import com.fmi.bookingshow.dto.ticket.TicketOutputDto;


import java.util.List;

public class OutputEventDto extends ImportEventDto {
    public List<TicketOutputDto> tickets;
    public List<ArtistDto> artistDtos;

    public OutputEventDto() {
    }

    public OutputEventDto(long eventId, String title, String dateTime, String description, List<ArtistDto> artists, long locationId, float ticketPrice, List<TicketOutputDto> tickets) {
        super(eventId, title, dateTime, description, null, locationId, ticketPrice);
        this.tickets = tickets;
        this.artistDtos = artists;
    }
}
