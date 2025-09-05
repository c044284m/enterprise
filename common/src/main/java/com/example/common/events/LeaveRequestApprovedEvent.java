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
public class LeaveRequestApprovedEvent implements RemoteEvent {
    private final Identity aggregateID;
    private final String staffId;
    private final String leaveType;
    private final LeavePeriod approvedPeriod;
    private final String occurredOn;

    @JsonCreator
    public LeaveRequestApprovedEvent(
            @JsonProperty("aggregateID") Identity aggregateID,
            @JsonProperty("staffId") String staffId,
            @JsonProperty("leaveType") String leaveType,
            @JsonProperty("approvedPeriod") LeavePeriod approvedPeriod
    ) {
        this.aggregateID = aggregateID;
        this.staffId = staffId;
        this.leaveType = leaveType;
        this.approvedPeriod = approvedPeriod;
        this.occurredOn = LocalDate.now().toString();
    }
}