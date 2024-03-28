package com.fmi.bookingshow.dto.location;

public class OutputLocationDto extends ImportLocationDto {
    public Long locationId;

    public OutputLocationDto() {
    }

    public OutputLocationDto(Long locationId, String name, String address) {
        super(name, address);
        this.locationId = locationId;
    }
}
