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
    @ManyToOne
    private SeatEntity seat;
    @ManyToOne
    private EventEntity event;
    private Date purchaseDate;
    private Float price;
}
