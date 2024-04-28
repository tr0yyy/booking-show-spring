package com.fmi.bookingshow.unit.service;

import com.fmi.bookingshow.exceptions.EventNotFoundException;
import com.fmi.bookingshow.exceptions.OperationNotPermittedException;
import com.fmi.bookingshow.exceptions.TicketOrderException;
import com.fmi.bookingshow.model.*;
import com.fmi.bookingshow.repository.*;
import com.fmi.bookingshow.service.TicketService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("H2-Testing")
public class TicketServiceTest {
    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private TicketArchiveRepository ticketArchiveRepository;

    @Test
    public void testSuccessfulTicketPurchase() throws TicketOrderException {
        UserEntity user = new UserEntity();
        user.setUserId(1L);
        user.setEmail("user@example.com");
        EventEntity event = new EventEntity();
        event.setEventId(1L);
        event.setDateTime(new Date());
        LocationEntity location = new LocationEntity("test", "test", 10, 10); // 10 rows, 10 seats per row
        event.setLocation(location);
        Mockito.when(userRepository.findByUserId(1L)).thenReturn(Optional.of(user));
        Mockito.when(eventRepository.findByEventId(1L)).thenReturn(Optional.of(event));
        Mockito.when(seatRepository.save(any(SeatEntity.class))).thenReturn(new SeatEntity(1, 1));
        Mockito.when(ticketRepository.save(any(TicketEntity.class))).thenReturn(new TicketEntity());

        TicketEntity result = ticketService.orderTicketForEvent(1L, 1L, 1, 1);

        Assertions.assertNotNull(result);
    }

    @Test
    public void testFailedTicketOrderMissingUser() {
        Mockito.when(userRepository.findByUserId(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(TicketOrderException.class, () -> ticketService.orderTicketForEvent(1L, 1L, 1, 1));
    }

    @Test
    public void testSuccessfulTicketArchive() throws EventNotFoundException, NoSuchAlgorithmException, OperationNotPermittedException {
        EventEntity event = new EventEntity();
        event.setEventId(1L);
        event.setDateTime(new Date(System.currentTimeMillis() - 1));
        event.setTickets(List.of(new TicketEntity()));
        Mockito.when(eventRepository.findByEventId(1L)).thenReturn(Optional.of(event));
        Mockito.when(ticketArchiveRepository.saveAll(any())).thenReturn(List.of(new TicketArchiveEntity()));

        int result = ticketService.archiveTicketsForEvent(1L);

        Assertions.assertEquals(1, result);
    }

    @Test
    public void testFailedTicketArchive() {
        EventEntity event = new EventEntity();
        event.setEventId(1L);
        event.setDateTime(new Date(System.currentTimeMillis() + 100000000));
        Mockito.when(eventRepository.findByEventId(1L)).thenReturn(Optional.of(event));

        Assertions.assertThrows(OperationNotPermittedException.class, () -> ticketService.archiveTicketsForEvent(1L));
    }
}
