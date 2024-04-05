package com.fmi.bookingshow.controller;

import com.fmi.bookingshow.component.ApiFactory;
import com.fmi.bookingshow.dto.location.ImportLocationDto;
import com.fmi.bookingshow.dto.location.OutputLocationDto;
import com.fmi.bookingshow.dto.response.ResponseDto;
import com.fmi.bookingshow.exceptions.DuplicateEntryException;
import com.fmi.bookingshow.mapper.LocationMapper;
import com.fmi.bookingshow.model.LocationEntity;
import com.fmi.bookingshow.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    private final ApiFactory apiFactory;

    public LocationController(LocationService locationService, LocationMapper locationMapper, ApiFactory apiFactory) {
        this.locationService = locationService;
        this.locationMapper = locationMapper;
        this.apiFactory = apiFactory;
    }

    @PostMapping("/api/location/insert")
    public ResponseEntity<ResponseDto<OutputLocationDto>> insertLocation(@RequestBody ImportLocationDto locationDto) {
        return apiFactory.create(() -> {
            LocationEntity location = locationMapper.importLocationDtoToLocationEntity(locationDto);
            location = locationService.addLocation(location);
            return locationMapper.locationEntityToOutputLocationDto(location);
        }, DuplicateEntryException.class);
    }

    @GetMapping("/api/location/get")
    public ResponseEntity<ResponseDto<List<OutputLocationDto>>> getLocations() {
        return apiFactory.create(() -> {
            List<LocationEntity> locations = locationService.getLocations();
            return locations.stream().map(
                    locationMapper::locationEntityToOutputLocationDto
            ).toList();
        });
    }
}
