package com.fmi.bookingshow.service;

import com.fmi.bookingshow.exceptions.ArtistNotFoundException;
import com.fmi.bookingshow.exceptions.DuplicateEntryException;
import com.fmi.bookingshow.exceptions.LocationNotFoundException;
import com.fmi.bookingshow.model.ArtistEntity;
import com.fmi.bookingshow.model.EventEntity;
import com.fmi.bookingshow.repository.ArtistRepository;
import com.fmi.bookingshow.repository.EventRepository;
import com.fmi.bookingshow.repository.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EventService {
    private final EventRepository eventRepository;
    private final ArtistRepository artistRepository;
    private final LocationRepository locationRepository;

    public EventService(EventRepository eventRepository, ArtistRepository artistRepository, LocationRepository locationRepository) {
        this.eventRepository = eventRepository;
        this.artistRepository = artistRepository;
        this.locationRepository = locationRepository;
    }

    public EventEntity addNewEventInDatabase(EventEntity event) throws DuplicateEntryException, ArtistNotFoundException, LocationNotFoundException {
        if (eventRepository.existsById(event.getEventId())) {
            throw new DuplicateEntryException("Event with id " + event.getEventId() + " already exists in the database");
        }
        for (ArtistEntity artistEntity : event.getArtists()) {
            if (!artistRepository.existsById(artistEntity.getArtistId())) {
                throw new ArtistNotFoundException("Artist with id " + artistEntity.getArtistId() + " not found in the database");
            }
        }
        if (!locationRepository.existsById(event.getLocation().getLocationId())) {
            throw new LocationNotFoundException("Location with id " + event.getLocation().getLocationId() + " already exists in the database");
        }

        EventEntity eventEntityToSave = new EventEntity(
                event.getTitle(),
                event.getDateTime(),
                event.getDescription(),
                artistRepository.findAllById(event.getArtists().stream().map(ArtistEntity::getArtistId).toList()),
                locationRepository.findById(event.getLocation().getLocationId()).orElse(null),
                event.getTicketPrice()
        );
        EventEntity savedEntity = eventRepository.save(eventEntityToSave);

        for (ArtistEntity artistEntity: eventEntityToSave.getArtists()) {
            artistEntity.getEvents().add(savedEntity);
            artistRepository.save(artistEntity);
        }

        return savedEntity;
    }

    public List<EventEntity> getAllEvents() {
        return eventRepository.findAll();
    }

    public EventEntity getEventById(long eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }


}
