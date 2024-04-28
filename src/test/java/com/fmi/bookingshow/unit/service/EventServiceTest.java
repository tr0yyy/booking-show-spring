package com.fmi.bookingshow.unit.service;

import com.fmi.bookingshow.repository.ArtistRepository;
import com.fmi.bookingshow.repository.EventRepository;
import com.fmi.bookingshow.repository.LocationRepository;
import com.fmi.bookingshow.service.EventService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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

}
