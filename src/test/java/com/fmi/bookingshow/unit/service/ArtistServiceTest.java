package com.fmi.bookingshow.unit.service;

import com.fmi.bookingshow.exceptions.DuplicateEntryException;
import com.fmi.bookingshow.model.ArtistEntity;
import com.fmi.bookingshow.repository.ArtistRepository;
import com.fmi.bookingshow.service.ArtistService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("H2-Testing")
public class ArtistServiceTest {
    @InjectMocks
    private ArtistService artistService;

    @Mock
    private ArtistRepository artistRepository;

    @Test
    public void testPassedImportNewArtist() throws DuplicateEntryException {
        Mockito.when(artistRepository.findByName("test")).thenReturn(Optional.empty());
        ArtistEntity artist = new ArtistEntity();
        artist.setName("test");
        Mockito.when(artistRepository.save(any(ArtistEntity.class))).thenReturn(artist);

        ArtistEntity result = artistService.importNewArtist(artist);
        Assertions.assertEquals(artist.getName(), result.getName());
    }

    @Test
    public void testFailedImportNewArtist() {
        Mockito.when(artistRepository.findByName("test")).thenReturn(Optional.of(new ArtistEntity()));
        ArtistEntity artist = new ArtistEntity();
        artist.setName("test");
        Mockito.when(artistRepository.save(any(ArtistEntity.class))).thenReturn(artist);
        Assertions.assertThrows(DuplicateEntryException.class, () -> artistService.importNewArtist(artist));
    }
}
