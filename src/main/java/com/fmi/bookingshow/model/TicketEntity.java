package com.fmi.bookingshow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity(name = "Tickets")
@Getter
@Setter
public class TicketEntity {
    @Id
    @GeneratedValue
    private long ticketId;
    @ManyToOne
    private UserEntity user;
    @OneToOne
    private SeatEntity seat;
    @ManyToOne
    private EventEntity event;
    private Date purchaseDate;

    public TicketEntity(UserEntity user, SeatEntity seat, EventEntity event, Date purchaseDate) {
        this.user = user;
        this.seat = seat;
        this.event = event;
        this.purchaseDate = purchaseDate;
    }

    public TicketEntity() {
    }
}
