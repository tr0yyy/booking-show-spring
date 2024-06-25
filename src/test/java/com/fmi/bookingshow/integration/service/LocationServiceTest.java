package com.fmi.bookingshow.integration.service;

import com.fmi.bookingshow.exceptions.DuplicateEntryException;
import com.fmi.bookingshow.model.LocationEntity;
import com.fmi.bookingshow.repository.LocationRepository;
import com.fmi.bookingshow.service.LocationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("H2-Testing")
public class LocationServiceTest {
    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationRepository locationRepository;

    @BeforeEach
    public void purgeDatabase() {
        locationRepository.deleteAll();
    }

    @Test
    public void testAddLocationSuccess() throws DuplicateEntryException {
        LocationEntity location = createTestLocation();
        LocationEntity result = locationService.addLocation(location);
        Assertions.assertEquals(location.getName(), result.getName());
    }

    private LocationEntity createTestLocation() {
        LocationEntity location = new LocationEntity();
        location.setName("Test Location");
        return location;
    }
}

