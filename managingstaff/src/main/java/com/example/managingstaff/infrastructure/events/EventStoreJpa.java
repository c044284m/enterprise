package com.example.managingstaff.infrastructure.events;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "event_store")
@Getter
@Setter
@NoArgsConstructor
public class EventStoreJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate occurredOn;

    @Column(length = 65000)
    private String eventBody;

    @Column(length = 50)
    private String eventType;

    public static EventStoreJpa create(LocalDate occurredOn, String eventBody, String eventType) {
        EventStoreJpa event = new EventStoreJpa();
        event.setOccurredOn(occurredOn);
        event.setEventBody(eventBody);
        event.setEventType(eventType);
        return event;
    }
}