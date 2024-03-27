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
    @OneToMany
    private List<TicketEntity> tickets;
    @ManyToOne
    private LocationEntity location;
}
