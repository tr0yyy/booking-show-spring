package com.fmi.bookingshow.controller;

import com.fmi.bookingshow.dto.location.ImportLocationDto;
import com.fmi.bookingshow.dto.location.OutputLocationDto;
import com.fmi.bookingshow.exceptions.DuplicateEntryException;
import com.fmi.bookingshow.mapper.LocationMapper;
import com.fmi.bookingshow.model.LocationEntity;
import com.fmi.bookingshow.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LocationController {
    private final LocationService locationService;
    private final LocationMapper locationMapper;
    private final Logger logger = LoggerFactory.getLogger(LocationController.class);

    public LocationController(LocationService locationService, LocationMapper locationMapper) {
        this.locationService = locationService;
        this.locationMapper = locationMapper;
    }

    @PostMapping("/api/location/insert")
    public ResponseEntity<OutputLocationDto> insertLocation(@RequestBody ImportLocationDto locationDto) {
        try {
            LocationEntity location = locationMapper.importLocationDtoToLocationEntity(locationDto);
            location = locationService.addLocation(location);
            return ResponseEntity.ok(locationMapper.locationEntityToOutputLocationDto(location));
        } catch (DuplicateEntryException duplicateEntryException) {
            this.logger.error(duplicateEntryException.getMessage() + " " + Arrays.toString(duplicateEntryException.getStackTrace()));
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/api/location/get")
    public ResponseEntity<List<OutputLocationDto>> getLocations() {
        List<LocationEntity> locations = locationService.getLocations();
        List<OutputLocationDto> outputLocationDtos = locations.stream().map(
                locationMapper::locationEntityToOutputLocationDto
        ).toList();
        return ResponseEntity.ok(outputLocationDtos);
    }
}
