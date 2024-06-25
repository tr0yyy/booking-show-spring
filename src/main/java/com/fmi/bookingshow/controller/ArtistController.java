package com.fmi.bookingshow.controller;

import com.fmi.bookingshow.dto.artist.ArtistDto;
import com.fmi.bookingshow.exceptions.DuplicateEntryException;
import com.fmi.bookingshow.mapper.ArtistMapper;
import com.fmi.bookingshow.model.ArtistEntity;
import com.fmi.bookingshow.service.ArtistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ArtistController {
    private final ArtistService artistService;
    private final ArtistMapper artistMapper;

    public ArtistController(ArtistService artistService, ArtistMapper artistMapper) {
        this.artistService = artistService;
        this.artistMapper = artistMapper;
    }

    @PostMapping("/admin/artist/import")
    public ArtistDto importArtist(@RequestBody ArtistDto artistDto) throws DuplicateEntryException {
        ArtistEntity artistEntity = artistService.importNewArtist(
                artistMapper.artistDtoToArtistEntity(artistDto)
        );
        return artistMapper.artistEntityToArtistDto(artistEntity);
    }

    @GetMapping("/core/artist/get")
    public List<ArtistDto> getArtists() {
        return artistService.getArtists()
                .stream()
                .map(artistMapper::artistEntityToArtistDto)
                .collect(Collectors.toList());
    }
}
