package com.fmi.bookingshow.mapper;

import com.fmi.bookingshow.dto.event.ImportEventDto;
import com.fmi.bookingshow.dto.event.OutputEventDto;
import com.fmi.bookingshow.model.ArtistEntity;
import com.fmi.bookingshow.model.EventEntity;
import com.fmi.bookingshow.model.LocationEntity;
import com.fmi.bookingshow.model.TicketEntity;
import com.fmi.bookingshow.utils.DateUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;

@Component
public class EventMapper {
   private final ArtistMapper artistMapper;
   private final TicketMapper ticketMapper;

   public EventMapper(ArtistMapper artistMapper, TicketMapper ticketMapper) {
       this.artistMapper = artistMapper;
       this.ticketMapper = ticketMapper;
   }

    public EventEntity importEventDtoToEventEntity(ImportEventDto eventDto) throws ParseException {
        LocationEntity tempLocation = new LocationEntity();
        tempLocation.setLocationId(eventDto.locationId);

        List<ArtistEntity> tempArtists = eventDto.artistIds.stream().map(artistId -> {
            ArtistEntity tempArtist = new ArtistEntity();
            tempArtist.setArtistId(artistId);
            return tempArtist;
        }).toList();

        return new EventEntity(
                eventDto.eventId,
                eventDto.title,
                DateUtils.parseDate(eventDto.dateTime),
                eventDto.description,
                tempArtists,
                tempLocation,
                eventDto.ticketPrice
        );
    }

    public OutputEventDto eventEntityToOutputEventDto(EventEntity eventEntity) {
        return new OutputEventDto(
                eventEntity.getEventId(),
                eventEntity.getTitle(),
                DateUtils.formatDate(eventEntity.getDateTime()),
                eventEntity.getDescription(),
                eventEntity.getArtists().stream().map(artistMapper::artistEntityToArtistDto).toList(),
                eventEntity.getLocation().getLocationId(),
                eventEntity.getTicketPrice(),
                eventEntity.getTickets() == null
                        ? null
                        : eventEntity.getTickets().stream().map(ticketMapper::ticketEntityToTicketOutput).toList()
        );
    }
}
