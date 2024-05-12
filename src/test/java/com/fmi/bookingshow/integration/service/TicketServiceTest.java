package com.fmi.bookingshow.integration.service;

import com.fmi.bookingshow.exceptions.EventNotFoundException;
import com.fmi.bookingshow.exceptions.OperationNotPermittedException;
import com.fmi.bookingshow.exceptions.TicketOrderException;
import com.fmi.bookingshow.model.*;
import com.fmi.bookingshow.repository.*;
import com.fmi.bookingshow.service.TicketService;
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

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("H2-Testing")
public class TicketServiceTest {
    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @BeforeEach
    public void purgeDatabase() {
        ticketRepository.deleteAll();
        userRepository.deleteAll();
        eventRepository.deleteAll();
        locationRepository.deleteAll();
        artistRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testSuccessfulTicketPurchase() throws TicketOrderException {
        UserEntity user = new UserEntity();
        user.setEmail("user@example.com");
        UserEntity savedUser = userRepository.save(user);
        EventEntity event = new EventEntity();
        event.setDateTime(new Date());
        LocationEntity location = new LocationEntity("test", "test", 10, 10); // 10 rows, 10 seats per row
        event.setLocation(locationRepository.save(location));
        ArtistEntity artist = new ArtistEntity("test", "test");
        event.setArtists(List.of(artistRepository.save(artist)));
        event.setTickets(null);
        EventEntity savedEvent = eventRepository.save(event);

        TicketEntity result = ticketService.orderTicketForEvent(savedUser.getUserId(), savedEvent.getEventId(), 1, 1);

        Assertions.assertNotNull(result);
    }
}
