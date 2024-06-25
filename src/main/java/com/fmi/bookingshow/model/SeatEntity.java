package com.fmi.bookingshow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name="Seats")
@Getter
@Setter
public class SeatEntity {
    @Id
    @GeneratedValue
    private Long seatId;
    private Integer seatRowNumber;
    private Integer seatNumber;
    @OneToOne(mappedBy = "seat")
    private TicketEntity ticketEntity;

    public SeatEntity(Integer seatRowNumber, Integer seatNumber) {
        this.seatRowNumber = seatRowNumber;
        this.seatNumber = seatNumber;
    }

    public SeatEntity() {
    }
}
