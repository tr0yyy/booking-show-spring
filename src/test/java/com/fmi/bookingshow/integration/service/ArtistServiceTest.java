package com.fmi.bookingshow.integration.service;

import com.fmi.bookingshow.exceptions.DuplicateEntryException;
import com.fmi.bookingshow.model.ArtistEntity;
import com.fmi.bookingshow.repository.ArtistRepository;
import com.fmi.bookingshow.service.ArtistService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("H2-Testing")
public class ArtistServiceTest {
    @Autowired
    private ArtistService artistService;

    @Autowired
    private ArtistRepository artistRepository;

    @BeforeEach
    public void purgeDatabase() {
        artistRepository.deleteAll();
    }

    @Test
    public void testPassedImportNewArtist() throws DuplicateEntryException {
        ArtistEntity artist = new ArtistEntity();
        artist.setName("test");
        artist.setBio("test");

        ArtistEntity result = artistService.importNewArtist(artist);
        Assertions.assertEquals(artist.getName(), result.getName());
    }
}
