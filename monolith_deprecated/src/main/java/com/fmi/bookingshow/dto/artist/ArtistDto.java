package com.fmi.bookingshow.dto.artist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ArtistDto {
    public Long id;
    @NotBlank
    @NotNull
    public String name;
    public String bio;

    public ArtistDto() {
    }

    public ArtistDto(Long id, String name, String bio) {
        this.id = id;
        this.name = name;
        this.bio = bio;
    }
}
