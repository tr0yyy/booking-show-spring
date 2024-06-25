package com.fmi.bookingshow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "Location")
@Getter
@Setter
public class LocationEntity {
    @Id
    @GeneratedValue
    private Long locationId;
    private String name;
    private String address;
    private Integer availableRows;
    private Integer availableSeatsPerRow;

    public LocationEntity(String name, String address, Integer availableRows, Integer availableSeatsPerRow) {
        this.name = name;
        this.address = address;
        this.availableRows = availableRows;
        this.availableSeatsPerRow = availableSeatsPerRow;
    }

    public LocationEntity() {
    }
}
