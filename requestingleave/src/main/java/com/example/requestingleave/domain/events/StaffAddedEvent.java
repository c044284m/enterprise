package com.example.requestingleave.domain.events;

import com.example.common.events.RemoteEvent;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class StaffAddedEvent implements RemoteEvent {
    private final String occurredOn;
    private final String aggregateId;
    private final String staffId;
    private final String fullName;
    private final String department;

    public StaffAddedEvent(String aggregateId,
                           String staffId,
                           String fullName,
                           String department) {
        this.occurredOn = LocalDate.now().toString(); // Prevents JSON serialization issues
        this.aggregateId = aggregateId;
        this.staffId = staffId;
        this.fullName = fullName;
        this.department = department;
    }
}
