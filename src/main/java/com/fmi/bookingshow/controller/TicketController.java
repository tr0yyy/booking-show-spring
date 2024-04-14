package com.fmi.bookingshow.controller;

import com.fmi.bookingshow.component.ApiFactory;
import com.fmi.bookingshow.dto.response.ResponseDto;
import com.fmi.bookingshow.dto.ticket.TicketOrderDto;
import com.fmi.bookingshow.dto.ticket.TicketOutputDto;
import com.fmi.bookingshow.exceptions.TicketOrderException;
import com.fmi.bookingshow.mapper.TicketMapper;
import com.fmi.bookingshow.model.TicketEntity;
import com.fmi.bookingshow.model.UserEntity;
import com.fmi.bookingshow.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TicketController {
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;
    private final ApiFactory apiFactory;

    public TicketController(TicketService ticketService, TicketMapper ticketMapper, ApiFactory apiFactory) {
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
        this.apiFactory = apiFactory;
    }

    @PostMapping("/core/ticket/order")
    public ResponseEntity<ResponseDto<TicketOutputDto>> orderTicket(Authentication authentication, @RequestBody TicketOrderDto ticketOrderDto) {
        return apiFactory.create(() -> {
            UserEntity currentUser = (UserEntity) authentication.getPrincipal();
            TicketEntity ticketEntity = ticketService.orderTicketForEvent(currentUser.getUserId(), ticketOrderDto.eventId, ticketOrderDto.seatRow, ticketOrderDto.seatNumber);
            return ticketMapper.ticketEntityToTicketOutput(ticketEntity);
        }, TicketOrderException.class);
    }
}
