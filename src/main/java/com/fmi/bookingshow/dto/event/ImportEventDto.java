package com.fmi.bookingshow.dto.event;

import java.util.List;

public class ImportEventDto {
    public long eventId;
    public String title;
    public String dateTime;
    public String description;
    public List<Long> artistIds;
    public long locationId;
    public float ticketPrice;

    public ImportEventDto() {
    }

    public ImportEventDto(long eventId, String title, String dateTime, String description, List<Long> artistIds, long locationId, float ticketPrice) {
        this.eventId = eventId;
        this.title = title;
        this.dateTime = dateTime;
        this.description = description;
        this.artistIds = artistIds;
        this.locationId = locationId;
        this.ticketPrice = ticketPrice;
    }
}
