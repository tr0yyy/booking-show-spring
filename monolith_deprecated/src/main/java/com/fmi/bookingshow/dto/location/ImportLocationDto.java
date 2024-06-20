package com.fmi.bookingshow.dto.location;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ImportLocationDto {
    @NotNull
    @NotBlank
    public String name;
    @NotNull
    @NotBlank
    public String address;
    @NotNull
    @NotBlank
    public Integer availableRows;
    @NotNull
    @NotBlank
    public Integer availableSeatsPerRow;

    public ImportLocationDto(String name, String address, Integer availableRows, Integer availableSeatsPerRow) {
        this.name = name;
        this.address = address;
        this.availableRows = availableRows;
        this.availableSeatsPerRow = availableSeatsPerRow;
    }

    public ImportLocationDto() {
    }
}
