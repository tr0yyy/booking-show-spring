package com.fmi.bookingshow.mapper;

import com.fmi.bookingshow.dto.location.ImportLocationDto;
import com.fmi.bookingshow.dto.location.OutputLocationDto;
import com.fmi.bookingshow.model.LocationEntity;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {
    public LocationEntity importLocationDtoToLocationEntity(ImportLocationDto importLocationDto) {
        return new LocationEntity(importLocationDto.name, importLocationDto.address, importLocationDto.availableRows, importLocationDto.availableSeatsPerRow);
    }

    public OutputLocationDto locationEntityToOutputLocationDto(LocationEntity location) {
        return new OutputLocationDto(location.getLocationId(), location.getName(), location.getAddress(), location.getAvailableRows(), location.getAvailableSeatsPerRow());
    }
}
