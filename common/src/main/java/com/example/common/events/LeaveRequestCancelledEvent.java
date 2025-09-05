package com.example.common.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.common.domain.Identity;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class LeaveRequestCancelledEvent implements RemoteEvent {
    private final Identity aggregateID;
    private final String occurredOn;

    @JsonCreator
    public LeaveRequestCancelledEvent(@JsonProperty("aggregateID") Identity aggregateID) {
        this.aggregateID = aggregateID;
        this.occurredOn = LocalDate.now().toString();
    }
}