package com.example.managingstaff.application.events;

import com.example.managingstaff.infrastructure.events.EventStoreJpa;
import com.example.managingstaff.infrastructure.events.EventStoreRepository;
import com.example.common.events.Event;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component//Will not find this class without this annotation when trying to inject
@AllArgsConstructor
public class EventStoreService {
    private EventStoreRepository eventsStore;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    public void append(Event event){
        EventStoreJpa newEvent = EventStoreJpa.create(LocalDate.now(),
                event.toString(),
                event.getClass().getSimpleName());
        eventsStore.save(newEvent);
        LOG.info("Added to event store " + newEvent + "\n");
    }
}