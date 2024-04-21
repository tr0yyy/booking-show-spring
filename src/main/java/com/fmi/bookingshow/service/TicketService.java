package com.fmi.bookingshow.service;

import com.fmi.bookingshow.exceptions.EventNotFoundException;
import com.fmi.bookingshow.exceptions.OperationNotPermittedException;
import com.fmi.bookingshow.exceptions.TicketOrderException;
import com.fmi.bookingshow.model.*;
import com.fmi.bookingshow.repository.*;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final SeatRepository seatRepository;
    private final TicketArchiveRepository ticketArchiveRepository;

    public TicketService(TicketRepository ticketRepository,
                         EventRepository eventRepository,
                         SeatRepository seatRepository,
                         UserRepository userRepository,
                         TicketArchiveRepository ticketArchiveRepository) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
        this.ticketArchiveRepository = ticketArchiveRepository;
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

    public int archiveTicketsForEvent(long eventId) throws EventNotFoundException, NoSuchAlgorithmException, OperationNotPermittedException {
        EventEntity event = eventRepository.findByEventId(eventId).orElse(null);
        if (event == null) {
            throw new EventNotFoundException();
        }

        if (event.getDateTime().getTime() > new Date().getTime()) {
            throw new OperationNotPermittedException("Event is still active");
        }

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        List<TicketArchiveEntity> ticketArchiveEntityList = new ArrayList<>();
        List<Long> ticketIds = new ArrayList<>();

        for(TicketEntity ticket: event.getTickets()) {
            ticketIds.add(ticket.getTicketId());
            String identifier = String.format("%s_%s", ticket.getTicketId(), eventId);
            messageDigest.update(ticket.toString().getBytes());
            String content = new String(messageDigest.digest());
            ticketArchiveEntityList.add(new TicketArchiveEntity(identifier, content));
        }

        ticketRepository.deleteAllById(ticketIds);
        ticketArchiveRepository.saveAll(ticketArchiveEntityList);

        return ticketArchiveEntityList.size();
    }
}
