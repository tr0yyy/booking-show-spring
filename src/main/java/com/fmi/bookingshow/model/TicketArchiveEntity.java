package com.fmi.bookingshow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name="TicketArchive")
@Getter
@Setter
public class TicketArchiveEntity {
    @Id
    @GeneratedValue
    private long id;
    private String identifier;
    private String content;

    public TicketArchiveEntity() {
    }

    public TicketArchiveEntity(String identifier, String content) {
        this.content = content;
        this.identifier = identifier;
    }
}
