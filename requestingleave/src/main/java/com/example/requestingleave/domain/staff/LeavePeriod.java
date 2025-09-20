package com.example.requestingleave.domain.staff;

import com.example.common.domain.ValueObject;
import lombok.ToString;

import java.time.LocalDate;

@ToString
public class LeavePeriod extends ValueObject {
    private LocalDate startDate;
    private LocalDate endDate;

    public LeavePeriod(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) return false;
        LeavePeriod other = (LeavePeriod) o;
        return other.startDate.equals(this.startDate) &&
                other.endDate.equals(this.endDate);
    }

    public LocalDate startDate() {
        return startDate;
    }

    public LocalDate endDate() {
        return endDate;
    }

}
