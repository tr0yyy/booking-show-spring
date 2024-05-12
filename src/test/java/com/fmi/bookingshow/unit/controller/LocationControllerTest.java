package com.fmi.bookingshow.unit.controller;

import com.fmi.bookingshow.controller.LocationController;
import com.fmi.bookingshow.dto.location.ImportLocationDto;
import com.fmi.bookingshow.dto.location.OutputLocationDto;
import com.fmi.bookingshow.exceptions.DuplicateEntryException;
import com.fmi.bookingshow.mapper.LocationMapper;
import com.fmi.bookingshow.model.LocationEntity;
import com.fmi.bookingshow.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("H2-Testing")
public class LocationControllerTest {

    @InjectMocks
    private LocationController locationController;

    @Mock
    private LocationService locationService;

    @Mock
    private LocationMapper locationMapper;


    @Test
    public void testInsertLocationSuccess() throws DuplicateEntryException {
        ImportLocationDto locationDto = new ImportLocationDto();
        LocationEntity locationEntity = new LocationEntity();
        OutputLocationDto outputLocationDto = new OutputLocationDto();

        when(locationMapper.importLocationDtoToLocationEntity(locationDto)).thenReturn(locationEntity);
        when(locationService.addLocation(locationEntity)).thenReturn(locationEntity);
        when(locationMapper.locationEntityToOutputLocationDto(locationEntity)).thenReturn(outputLocationDto);

        OutputLocationDto response = locationController.insertLocation(locationDto);

        assertEquals(outputLocationDto, response);
    }

    @Test
    public void testGetLocations() {
        List<LocationEntity> locations = new ArrayList<>();
        when(locationService.getLocations()).thenReturn(locations);
        when(locationMapper.locationEntityToOutputLocationDto(any())).thenReturn(new OutputLocationDto());

        List<OutputLocationDto> response = locationController.getLocations();

        assertEquals(locations.size(), response.size());
    }
}
