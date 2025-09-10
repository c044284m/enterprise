package com.example.approvingleave.application.events;

import com.example.common.events.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class DomainEventManager {
    private Environment env;
    private RabbitTemplate sender;

    private ApplicationEventPublisher eventPublisher;
    private EventStoreService eventStoreService;

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    public void manageDomainEvents(Object nameOfService, List<Event> events) {
        for (Event event : events){
            LOG.info("{} -> {}", nameOfService.getClass().getSimpleName(), event);

            if (event instanceof LocalEvent) {
                eventPublisher.publishEvent(event);
            }
            if (event instanceof RemoteEvent){
                sendRemoteEvent(event);
            }
            //add event to local event store
            eventStoreService.append(event);
        }
    }

    private void sendRemoteEvent(Event event) {
        String exchange = env.getProperty("rabbitmq.order_exchange");
        if (event instanceof LeaveRequestApprovedEvent) {
            String routingKey = env.getProperty("rabbitmq.newOrderConfirmedKey");
            sender.convertAndSend(Objects.requireNonNull(exchange), Objects.requireNonNull(routingKey), event);
        }
        else {
            LOG.warn("Unsupported event type: " + event.getClass().getSimpleName());
        }
        LOG.info("{} remote event has been sent", event.getClass().getSimpleName());
    }
}
