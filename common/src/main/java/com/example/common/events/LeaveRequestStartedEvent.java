package com.example.common.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.common.domain.Identity;
import com.example.common.domain.LeavePeriod;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class LeaveRequestStartedEvent implements RemoteEvent {
    private final Identity aggregateID;
    private final String staffId;
    private final LeavePeriod requestedPeriod;
    private final String occurredOn;

    @JsonCreator
    public LeaveRequestStartedEvent(
            @JsonProperty("aggregateID") Identity aggregateID,
            @JsonProperty("staffId") String staffId,
            @JsonProperty("requestedPeriod") LeavePeriod requestedPeriod,
            @JsonProperty("occurredOn") String occurredOn
    ) {
        this.aggregateID = aggregateID;
        this.staffId = staffId;
        this.requestedPeriod = requestedPeriod;
        this.occurredOn = LocalDate.now().toString();
    }
}
