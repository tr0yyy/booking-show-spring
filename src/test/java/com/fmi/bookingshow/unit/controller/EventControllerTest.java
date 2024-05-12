package com.fmi.bookingshow.unit.controller;

import com.fmi.bookingshow.controller.EventController;
import com.fmi.bookingshow.dto.event.ImportEventDto;
import com.fmi.bookingshow.dto.event.OutputEventDto;
import com.fmi.bookingshow.exceptions.ArtistNotFoundException;
import com.fmi.bookingshow.exceptions.DuplicateEntryException;
import com.fmi.bookingshow.exceptions.LocationNotFoundException;
import com.fmi.bookingshow.mapper.EventMapper;
import com.fmi.bookingshow.mapper.TicketMapper;
import com.fmi.bookingshow.model.EventEntity;
import com.fmi.bookingshow.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("H2-Testing")
public class EventControllerTest {

    @InjectMocks
    private EventController eventController;

    @Mock
    private EventService eventService;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private TicketMapper ticketMapper;


    @Test
    public void testImportEventSuccess() throws ParseException, ArtistNotFoundException, DuplicateEntryException, LocationNotFoundException {
        ImportEventDto eventDto = new ImportEventDto();
        EventEntity eventEntity = new EventEntity();
        OutputEventDto outputEventDto = new OutputEventDto();

        when(eventMapper.importEventDtoToEventEntity(eventDto)).thenReturn(eventEntity);
        when(eventService.addNewEventInDatabase(eventEntity)).thenReturn(eventEntity);
        when(eventMapper.eventEntityToOutputEventDto(eventEntity)).thenReturn(outputEventDto);

        OutputEventDto response = eventController.importEvent(eventDto);

        assertEquals(outputEventDto, response);
    }

    @Test
    public void testGetEvents() {
        List<EventEntity> events = new ArrayList<>();
        when(eventService.getAllEvents()).thenReturn(events);
        when(eventMapper.eventEntityToOutputEventDto(any())).thenReturn(new OutputEventDto());

        List<OutputEventDto> response = eventController.getEvents();

        assertEquals(events.size(), response.size());
    }

}
