package com.fmi.bookingshow.service;

import com.fmi.bookingshow.exceptions.DuplicateEntryException;
import com.fmi.bookingshow.model.ArtistEntity;
import com.fmi.bookingshow.repository.ArtistRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ArtistService {
    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public ArtistEntity importNewArtist(ArtistEntity artistEntity) throws DuplicateEntryException {
        if (artistRepository.findByName(artistEntity.getName()).orElse(null) != null) {
            throw new DuplicateEntryException("An artist with same name already exists");
        }
        return artistRepository.save(artistEntity);
    }

    public List<ArtistEntity> getArtists() {
        return artistRepository.findAll();
    }
}
