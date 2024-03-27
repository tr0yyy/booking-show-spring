package com.fmi.bookingshow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "Artist")
@Getter
@Setter
public class ArtistEntity {
    @Id
    @GeneratedValue
    private Long artistId;
    private String name;
    private String bio;
    @ManyToMany
    @JoinTable(name = "ArtistEvent",
    joinColumns = @JoinColumn(name="artistId", referencedColumnName = "artistId"),
    inverseJoinColumns = @JoinColumn(name="eventId", referencedColumnName = "eventId"))
    private List<EventEntity> events;
}
