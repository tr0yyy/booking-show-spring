package com.fmi.bookingshow.service;

import com.fmi.bookingshow.exceptions.DuplicateEntryException;
import com.fmi.bookingshow.model.LocationEntity;
import com.fmi.bookingshow.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public LocationEntity addLocation(LocationEntity location) throws DuplicateEntryException {
        if (locationRepository.findByName(location.getName()).isPresent()) {
            throw new DuplicateEntryException("A location with same name already exists");
        }
        return locationRepository.save(location);
    }

    public List<LocationEntity> getLocations() {
        return locationRepository.getAll();
    }

}
