package com.fmi.bookingshow.unit.service;

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
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest
@ActiveProfiles("H2-Testing")
public class EventServiceTest {
    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;
    @Mock
    private ArtistRepository artistRepository;
    @Mock
    private LocationRepository locationRepository;

    @Test
    void testAddNewEventInDatabaseSuccess() throws DuplicateEntryException, ArtistNotFoundException, LocationNotFoundException {
        EventEntity event = createTestEvent();
        ArtistEntity artist = new ArtistEntity();
        LocationEntity location = new LocationEntity();
        artist.setArtistId(1L);
        location.setLocationId(1L);
        event.setArtists(List.of(artist));
        event.setLocation(location);
        Mockito.when(eventRepository.existsById(event.getEventId())).thenReturn(false);
        Mockito.when(eventRepository.save(any(EventEntity.class))).thenReturn(event);
        Mockito.when(artistRepository.existsById(anyLong())).thenReturn(true);
        Mockito.when(locationRepository.existsById(anyLong())).thenReturn(true);

        EventEntity result = eventService.addNewEventInDatabase(event);

        Assertions.assertEquals(event.getTitle(), result.getTitle());
    }

    @Test
    void testAddNewEventInDatabaseDuplicateEntryExceptionThrown() {
        EventEntity event = createTestEvent();
        Mockito.when(eventRepository.existsById(event.getEventId())).thenReturn(true);

        Assertions.assertThrows(DuplicateEntryException.class, () -> eventService.addNewEventInDatabase(event));
    }

    private EventEntity createTestEvent() {
        EventEntity event = new EventEntity();
        event.setEventId(1L);
        event.setTitle("Test Event");
        event.setDateTime(new Date());
        event.setDescription("Test Description");
        event.setTicketPrice(20.0F);
        return event;
    }
}
