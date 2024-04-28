package com.fmi.bookingshow.mapper;

import com.fmi.bookingshow.dto.artist.ArtistDto;
import com.fmi.bookingshow.model.ArtistEntity;
import org.springframework.stereotype.Component;

@Component
public class ArtistMapper {
    public ArtistEntity artistDtoToArtistEntity (ArtistDto artistDto) {
        return new ArtistEntity(artistDto.name, artistDto.bio);
    }

    public ArtistDto artistEntityToArtistDto (ArtistEntity artistEntity) {
        return new ArtistDto(artistEntity.getArtistId(), artistEntity.getName(), artistEntity.getBio());
    }
}
