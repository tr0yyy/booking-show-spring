package com.fmi.bookingshow.controller;

import com.fmi.bookingshow.dto.event.ImportEventDto;
import com.fmi.bookingshow.dto.event.OutputEventDto;
import com.fmi.bookingshow.exceptions.ArtistNotFoundException;
import com.fmi.bookingshow.exceptions.DuplicateEntryException;
import com.fmi.bookingshow.exceptions.LocationNotFoundException;
import com.fmi.bookingshow.mapper.EventMapper;
import com.fmi.bookingshow.model.EventEntity;
import com.fmi.bookingshow.service.EventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EventController {
    private final EventMapper eventMapper;
    private final EventService eventService;

    public EventController(EventMapper eventMapper, EventService eventService) {
        this.eventMapper = eventMapper;
        this.eventService = eventService;
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

}
