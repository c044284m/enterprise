package com.example.requestingleave.infrastructure.event;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity(name="event_store")//Needed for custom queries
@Table(name="event_store")
@ToString
@Getter
@Setter
public class EventStoreJpa{
    @Id
    @Column(name="id")
    @SequenceGenerator(name= "event_store_sequence",
            sequenceName = "event_store_sequence_id",
            allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="event_store_sequence")
    private long id;
    @Column(name="occurred_on")
    private LocalDate occurredOn;
    @Column(name="event_body")
    private String eventBody;
    @Column(name="event_type")
    private String eventType;

    protected EventStoreJpa(){}

    protected EventStoreJpa(LocalDate occurredOn, String eventBody, String eventType ){
        this.occurredOn =occurredOn;
        this.eventBody = eventBody;
        this.eventType = eventType;
    }

    public static EventStoreJpa create(LocalDate occurredOn, String eventBody, String eventType ){
        return new EventStoreJpa(occurredOn,eventBody, eventType);
    }
}
