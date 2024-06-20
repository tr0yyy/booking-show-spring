package com.fmi.bookingshow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity(name="Events")
@Getter
@Setter
public class EventEntity {
    @Id
    @GeneratedValue
    private Long eventId;
    private String title;
    private Date dateTime;
    private String description;
    @ManyToMany(mappedBy = "events")
    private List<ArtistEntity> artists;
    @OneToMany(mappedBy = "event")
    private List<TicketEntity> tickets;
    @ManyToOne
    private LocationEntity location;
    private Float ticketPrice;

    public EventEntity() {
    }

    public EventEntity(Long eventId, String title, Date dateTime, String description, List<ArtistEntity> artists, LocationEntity location, Float ticketPrice) {
        this.eventId = eventId;
        this.title = title;
        this.dateTime = dateTime;
        this.description = description;
        this.artists = artists;
        this.location = location;
        this.ticketPrice = ticketPrice;
    }

    public EventEntity(String title, Date dateTime, String description, List<ArtistEntity> artists, LocationEntity location, Float ticketPrice) {
        this.title = title;
        this.dateTime = dateTime;
        this.description = description;
        this.artists = artists;
        this.location = location;
        this.ticketPrice = ticketPrice;
    }
}
