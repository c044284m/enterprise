package com.example.common.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class LeavePeriod extends ValueObject {
    private final LocalDate startDate;
    private final LocalDate endDate;

    @JsonCreator
    public LeavePeriod(
            @JsonProperty("startDate") LocalDate startDate,
            @JsonProperty("endDate") LocalDate endDate) {
        assertValidPeriod(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Shallow copy
    public LeavePeriod(LeavePeriod period) {
        this(period.startDate, period.endDate);
    }

    public LocalDate startDate() {
        return startDate;
    }

    public LocalDate endDate() {
        return endDate;
    }

    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) return false;
        LeavePeriod other = (LeavePeriod) o;
        return this.startDate.equals(other.startDate) &&
                this.endDate.equals(other.endDate);
    }

    private void assertValidPeriod(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates must not be null");
        }
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
    }

    public int numberOfDays() {
        return (int) (endDate.toEpochDay() - startDate.toEpochDay()) + 1;
    }

    public boolean includes(LocalDate date) {
        return (date.isEqual(startDate) || date.isAfter(startDate)) &&
                (date.isEqual(endDate) || date.isBefore(endDate));
    }
}