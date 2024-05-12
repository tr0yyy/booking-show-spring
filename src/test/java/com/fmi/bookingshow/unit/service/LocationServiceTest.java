package com.fmi.bookingshow.unit.service;

import com.fmi.bookingshow.exceptions.DuplicateEntryException;
import com.fmi.bookingshow.model.LocationEntity;
import com.fmi.bookingshow.repository.ArtistRepository;
import com.fmi.bookingshow.repository.EventRepository;
import com.fmi.bookingshow.repository.LocationRepository;
import com.fmi.bookingshow.service.EventService;
import com.fmi.bookingshow.service.LocationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("H2-Testing")
public class LocationServiceTest {
    @InjectMocks
    private LocationService locationService;

    @Mock
    private LocationRepository locationRepository;

    @Test
    public void testAddLocation_Success() throws DuplicateEntryException {
       LocationEntity location = createTestLocation();
        Mockito.when(locationRepository.findByName(location.getName())).thenReturn(Optional.empty());
        Mockito.when(locationRepository.save(any(LocationEntity.class))).thenReturn(location);

        LocationEntity result = locationService.addLocation(location);

        Assertions.assertEquals(location.getName(), result.getName());
    }

    @Test
    public void testAddLocation_DuplicateEntryExceptionThrown() {
        LocationEntity location = createTestLocation();
        Mockito.when(locationRepository.findByName(location.getName())).thenReturn(Optional.of(location));

        Assertions.assertThrows(DuplicateEntryException.class, () -> locationService.addLocation(location));
    }

    private  LocationEntity createTestLocation() {
        LocationEntity location = new LocationEntity();
        location.setLocationId(1L);
        location.setName("Test Location");
        return location;
    }
}

