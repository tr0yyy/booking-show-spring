package com.fmi.bookingshow.unit.controller;

import com.fmi.bookingshow.controller.TicketController;
import com.fmi.bookingshow.dto.response.MessageDto;
import com.fmi.bookingshow.dto.ticket.TicketOrderDto;
import com.fmi.bookingshow.dto.ticket.TicketOutputDto;
import com.fmi.bookingshow.exceptions.TicketOrderException;
import com.fmi.bookingshow.mapper.TicketMapper;
import com.fmi.bookingshow.model.TicketEntity;
import com.fmi.bookingshow.model.UserEntity;
import com.fmi.bookingshow.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("H2-Testing")
public class TicketControllerTest {

    @InjectMocks
    private TicketController ticketController;

    @Mock
    private TicketService ticketService;

    @Mock
    private TicketMapper ticketMapper;


    @Test
    public void testOrderTicketSuccess() throws TicketOrderException {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(1L);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userEntity, null);
        TicketOrderDto ticketOrderDto = new TicketOrderDto();
        TicketEntity ticketEntity = new TicketEntity();
        TicketOutputDto ticketOutputDto = new TicketOutputDto();

        when(ticketService.orderTicketForEvent(anyLong(), anyLong(), anyInt(), anyInt())).thenReturn(ticketEntity);
        when(ticketMapper.ticketEntityToTicketOutput(ticketEntity)).thenReturn(ticketOutputDto);

        TicketOutputDto response = ticketController.orderTicket(authentication, ticketOrderDto);

        assertEquals(ticketOutputDto, response);
    }

    @Test
    public void testArchiveTicketsSuccess() throws Exception {
        MessageDto expectedMessageDto = new MessageDto("Archived 10 tickets for event 123");
        when(ticketService.archiveTicketsForEvent(anyLong())).thenReturn(10);

        MessageDto response = ticketController.archiveTickets(123L);

        assertEquals(expectedMessageDto.data, response.data);
    }
}
