package com.fmi.bookingshow.integration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmi.bookingshow.constants.Role;
import com.fmi.bookingshow.controller.TicketController;
import com.fmi.bookingshow.dto.response.MessageDto;
import com.fmi.bookingshow.dto.ticket.TicketOrderDto;
import com.fmi.bookingshow.dto.ticket.TicketOutputDto;
import com.fmi.bookingshow.exceptions.TicketOrderException;
import com.fmi.bookingshow.mapper.TicketMapper;
import com.fmi.bookingshow.model.*;
import com.fmi.bookingshow.repository.*;
import com.fmi.bookingshow.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("H2-Testing")
public class TicketControllerTest {
    @Autowired
    private MockMvc mockMvc;

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

    private RequestPostProcessor testUser() {
        return SecurityMockMvcRequestPostProcessors.user("user").roles("USER");
    }

    @Test
    @Transactional
    @WithUserDetails("user")
    public void testOrderTicketSuccess() throws Exception {
        UserEntity user = new UserEntity();
        user.setUsername("user");
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

        TicketOrderDto ticketOrderDto = new TicketOrderDto();
        ticketOrderDto.eventId = savedEvent.getEventId();
        ticketOrderDto.seatRow = 5;
        ticketOrderDto.seatNumber = 10;

        mockMvc.perform(MockMvcRequestBuilders.post("/core/ticket/order")
                        .principal(new UsernamePasswordAuthenticationToken(savedUser, null))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ticketOrderDto)))
                .andExpect(status().isOk());
    }
}
