package com.example.requestingleave.domain.leaveRequest;

import com.example.common.domain.ValueObject;
import com.example.common.domain.LeavePeriod;
import lombok.ToString;

@ToString
public class LeaveDay extends ValueObject {
    private final LeavePeriod period;

    public LeaveDay(LeavePeriod period) {
        if (period == null || period.numberOfDays() <= 0) {
            throw new IllegalArgumentException("Leave period must be positive");
        }
        this.period = period;
    }

    public int numberOfDays() {
        return period.numberOfDays();
    }

    public LeavePeriod toLeavePeriod() {
        return period;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) return false;
        LeaveDay other = (LeaveDay) o;
        return other.period.equals(this.period);
    }
}