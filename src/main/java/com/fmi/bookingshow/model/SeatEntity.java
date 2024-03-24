package com.fmi.bookingshow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name="Seats")
@Getter
@Setter
public class SeatEntity {
    @Id
    @GeneratedValue
    private Long seatId;
    private Integer rowNumber;
    private Integer seatNumber;
}
