package com.fmi.bookingshow.unit.controller;

import com.fmi.bookingshow.controller.ArtistController;
import com.fmi.bookingshow.dto.artist.ArtistDto;
import com.fmi.bookingshow.exceptions.DuplicateEntryException;
import com.fmi.bookingshow.mapper.ArtistMapper;
import com.fmi.bookingshow.model.ArtistEntity;
import com.fmi.bookingshow.service.ArtistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("H2-Testing")
public class ArtistControllerTest {

    @InjectMocks
    private ArtistController artistController;

    @Mock
    private ArtistService artistService;

    @Mock
    private ArtistMapper artistMapper;

    @Test
    public void testImportArtist_Success() throws DuplicateEntryException {
        ArtistDto artistDto = new ArtistDto();
        ArtistEntity artistEntity = new ArtistEntity();

        when(artistMapper.artistDtoToArtistEntity(artistDto)).thenReturn(artistEntity);
        when(artistService.importNewArtist(artistEntity)).thenReturn(artistEntity);

        ArtistDto response = artistController.importArtist(artistDto);

        assertNotNull(response);

    }

    @Test
    public void testGetArtists() {
        List<ArtistEntity> artistEntities = Collections.singletonList(new ArtistEntity());
        List<ArtistDto> artistDtos = Collections.singletonList(new ArtistDto());

        when(artistService.getArtists()).thenReturn(artistEntities);
        when(artistMapper.artistEntityToArtistDto(any())).thenReturn(artistDtos.get(0));

        List<ArtistDto> response = artistController.getArtists();

        assertEquals(artistDtos, response);
        verify(artistService).getArtists();
        verify(artistMapper).artistEntityToArtistDto(artistEntities.get(0));
    }
}

