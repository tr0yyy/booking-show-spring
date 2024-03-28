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

    public ImportLocationDto(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public ImportLocationDto() {
    }
}
