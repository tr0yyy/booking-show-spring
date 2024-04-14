package com.fmi.bookingshow.service;

import com.fmi.bookingshow.exceptions.TicketOrderException;
import com.fmi.bookingshow.model.*;
import com.fmi.bookingshow.repository.EventRepository;
import com.fmi.bookingshow.repository.SeatRepository;
import com.fmi.bookingshow.repository.TicketRepository;
import com.fmi.bookingshow.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final SeatRepository seatRepository;

    public TicketService(TicketRepository ticketRepository,
                         EventRepository eventRepository,
                         SeatRepository seatRepository,
                         UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
    }

    public TicketEntity orderTicketForEvent(long userId, long eventId, int seatRow, int seatNumber) throws TicketOrderException {
        UserEntity user = userRepository.findByUserId(userId).orElse(null);
        if (user == null) {
            throw new TicketOrderException("User not found");
        }

        EventEntity event = eventRepository.findByEventId(eventId).orElse(null);
        if (event == null) {
            throw new TicketOrderException("Event not found");
        }

        // location validation (seat must be inside location limits)
        LocationEntity location = event.getLocation();
        if (seatRow > location.getAvailableRows()
                || seatRow < 0
                || seatNumber > location.getAvailableSeatsPerRow()
                || seatNumber < 0) {
            throw new TicketOrderException("Invalid seat data provided");
        }

        TicketEntity ticketWithSameSeat = event.getTickets()
                .stream()
                .filter(ticket ->
                        ticket.getSeat().getSeatNumber() == seatNumber
                                && ticket.getSeat().getSeatRowNumber() == seatRow
                )
                .findFirst()
                .orElse(null);

        if (ticketWithSameSeat != null) {
            throw new TicketOrderException("Seat already purchased");
        }

        SeatEntity seatEntity = seatRepository.save(
                new SeatEntity(
                        seatRow,
                        seatNumber
                )
        );

        return ticketRepository.save(
                new TicketEntity(
                        user,
                        seatEntity,
                        event,
                        new Date()
                )
        );
    }
}
