package com.fmi.bookingshow.controller;

import com.fmi.bookingshow.dto.response.MessageDto;
import com.fmi.bookingshow.dto.ticket.TicketOrderDto;
import com.fmi.bookingshow.dto.ticket.TicketOutputDto;
import com.fmi.bookingshow.exceptions.EventNotFoundException;
import com.fmi.bookingshow.exceptions.OperationNotPermittedException;
import com.fmi.bookingshow.exceptions.TicketOrderException;
import com.fmi.bookingshow.mapper.TicketMapper;
import com.fmi.bookingshow.model.TicketEntity;
import com.fmi.bookingshow.model.UserEntity;
import com.fmi.bookingshow.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@Slf4j
public class TicketController {
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    public TicketController(TicketService ticketService, TicketMapper ticketMapper) {
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
    }

    @PostMapping("/core/ticket/order")
    public TicketOutputDto orderTicket(Authentication authentication, @RequestBody TicketOrderDto ticketOrderDto) throws TicketOrderException {
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        TicketEntity ticketEntity = ticketService.orderTicketForEvent(currentUser.getUserId(), ticketOrderDto.eventId, ticketOrderDto.seatRow, ticketOrderDto.seatNumber);
        return ticketMapper.ticketEntityToTicketOutput(ticketEntity);
    }

    @PostMapping("/admin/ticket/archive")
    public MessageDto archiveTickets(@RequestParam Long eventId) throws EventNotFoundException, NoSuchAlgorithmException, OperationNotPermittedException {
        return new MessageDto(String.format("Archived %d tickets for event %d",
                ticketService.archiveTicketsForEvent(eventId),
                eventId));
    }
}
