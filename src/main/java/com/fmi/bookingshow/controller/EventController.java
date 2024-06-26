package com.fmi.bookingshow.controller;

import com.fmi.bookingshow.component.model_assembler.EventModelAssembler;
import com.fmi.bookingshow.dto.artist.ArtistDto;
import com.fmi.bookingshow.dto.event.ImportEventDto;
import com.fmi.bookingshow.dto.event.OutputEventDto;
import com.fmi.bookingshow.dto.ticket.TicketOutputDto;
import com.fmi.bookingshow.exceptions.ArtistNotFoundException;
import com.fmi.bookingshow.exceptions.DuplicateEntryException;
import com.fmi.bookingshow.exceptions.LocationNotFoundException;
import com.fmi.bookingshow.mapper.ArtistMapper;
import com.fmi.bookingshow.mapper.EventMapper;
import com.fmi.bookingshow.mapper.TicketMapper;
import com.fmi.bookingshow.model.EventEntity;
import com.fmi.bookingshow.model.TicketEntity;
import com.fmi.bookingshow.service.EventService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EventController {
    private final EventMapper eventMapper;
    private final TicketMapper ticketMapper;
    private final ArtistMapper artistMapper;
    private final EventService eventService;
    private final EventModelAssembler eventModelAssembler;

    public EventController(EventMapper eventMapper, TicketMapper ticketMapper, ArtistMapper artistMapper, EventService eventService, EventModelAssembler eventModelAssembler) {
        this.eventMapper = eventMapper;
        this.ticketMapper = ticketMapper;
        this.artistMapper = artistMapper;
        this.eventService = eventService;
        this.eventModelAssembler = eventModelAssembler;
    }

    @PostMapping("/admin/event/import")
    public OutputEventDto importEvent(@RequestBody ImportEventDto eventDto) throws ParseException, ArtistNotFoundException, DuplicateEntryException, LocationNotFoundException {
        EventEntity eventEntity = eventService.addNewEventInDatabase(
                eventMapper.importEventDtoToEventEntity(eventDto)
        );
        return eventMapper.eventEntityToOutputEventDto(eventEntity);
    }

    @GetMapping("/core/event/get")
    public List<OutputEventDto> getEvents() {
        return eventService.getAllEvents()
                .stream()
                .map(eventMapper::eventEntityToOutputEventDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/core/event/get_tickets")
    public Map<Long, List<TicketOutputDto>> getTicketsEvents() {
        List<EventEntity> events = eventService.getAllEvents();
        Map<Long, List<TicketOutputDto>> tickets = new HashMap<>();
        for (EventEntity event : events) {
            List<TicketOutputDto> ticketOutputDtos = event.getTickets()
                    .stream()
                    .map(ticketMapper::ticketEntityToTicketOutput)
                    .collect(Collectors.toList());
            tickets.put(event.getEventId(), ticketOutputDtos);
        }
        return tickets;
    }

    @GetMapping("/core/event/get_artists")
    public Map<Long, List<ArtistDto>> getArtistsEvents() {
        List<EventEntity> events = eventService.getAllEvents();
        Map<Long, List<ArtistDto>> artists = new HashMap<>();
        for (EventEntity event : events) {
            List<ArtistDto> artistDtos = event.getArtists()
                    .stream()
                    .map(artistMapper::artistEntityToArtistDto)
                    .collect(Collectors.toList());
            artists.put(event.getEventId(), artistDtos);
        }
        return artists;
    }

    @GetMapping("/core/event/get_all_events")
    public CollectionModel<EntityModel<OutputEventDto>> getAllEvents() {
        List<EntityModel<OutputEventDto>> events = eventService.getAllEvents()
                .stream()
                .map(eventMapper::eventEntityToOutputEventDto)
                .map(eventModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(events,
                linkTo(methodOn(EventController.class).getAllEvents()).withSelfRel());
    }

    @GetMapping("/core/event/get_event/{id}")
    public EntityModel<OutputEventDto> getEventById(@PathVariable Long id) {
        OutputEventDto event = eventMapper.eventEntityToOutputEventDto(eventService.getEventById(id));
        return eventModelAssembler.toModel(event);
    }


}
