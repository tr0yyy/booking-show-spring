package com.fmi.bookingshow.integration.service;

import com.fmi.bookingshow.exceptions.ArtistNotFoundException;
import com.fmi.bookingshow.exceptions.DuplicateEntryException;
import com.fmi.bookingshow.exceptions.LocationNotFoundException;
import com.fmi.bookingshow.model.ArtistEntity;
import com.fmi.bookingshow.model.EventEntity;
import com.fmi.bookingshow.model.LocationEntity;
import com.fmi.bookingshow.repository.ArtistRepository;
import com.fmi.bookingshow.repository.EventRepository;
import com.fmi.bookingshow.repository.LocationRepository;
import com.fmi.bookingshow.service.EventService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest
@ActiveProfiles("H2-Testing")
public class EventServiceTest {
    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private LocationRepository locationRepository;

    @BeforeEach
    public void purgeDatabase() {
        eventRepository.deleteAll();
        artistRepository.deleteAll();
        locationRepository.deleteAll();
    }

    @Test
    @Transactional
    void testAddNewEventInDatabaseSuccess() throws DuplicateEntryException, ArtistNotFoundException, LocationNotFoundException {
        EventEntity event = createTestEvent();
        ArtistEntity artist = new ArtistEntity();
        LocationEntity location = new LocationEntity();
        artist.setEvents(new ArrayList<>());
        event.setArtists(List.of(artistRepository.save(artist)));
        event.setLocation(locationRepository.save(location));

        EventEntity result = eventService.addNewEventInDatabase(event);

        Assertions.assertEquals(event.getTitle(), result.getTitle());
    }

    private EventEntity createTestEvent() {
        EventEntity event = new EventEntity();
        event.setEventId(0L);
        event.setTitle("Test Event");
        event.setDateTime(new Date());
        event.setDescription("Test Description");
        event.setTicketPrice(20.0F);
        return event;
    }
}
