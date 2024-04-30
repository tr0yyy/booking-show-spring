package com.fmi.bookingshow.controller;

import com.fmi.bookingshow.dto.location.ImportLocationDto;
import com.fmi.bookingshow.dto.location.OutputLocationDto;
import com.fmi.bookingshow.exceptions.DuplicateEntryException;
import com.fmi.bookingshow.mapper.LocationMapper;
import com.fmi.bookingshow.model.LocationEntity;
import com.fmi.bookingshow.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class LocationController {
    private final LocationService locationService;
    private final LocationMapper locationMapper;

    public LocationController(LocationService locationService, LocationMapper locationMapper) {
        this.locationService = locationService;
        this.locationMapper = locationMapper;
    }

    @PostMapping("/admin/location/import")
    public OutputLocationDto insertLocation(@RequestBody ImportLocationDto locationDto) throws DuplicateEntryException {
        LocationEntity location = locationMapper.importLocationDtoToLocationEntity(locationDto);
        location = locationService.addLocation(location);
        return locationMapper.locationEntityToOutputLocationDto(location);
    }

    @GetMapping("/core/location/get")
    public List<OutputLocationDto> getLocations() {
        List<LocationEntity> locations = locationService.getLocations();
        return locations.stream().map(
                locationMapper::locationEntityToOutputLocationDto
        ).toList();
    }
}
