package com.example.requestingleave.leaveRequest;

import com.example.common.domain.LeavePeriod;
import com.example.requestingleave.domain.leaveRequest.LeaveDay;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LeaveDayTests {

    private LeavePeriod validPeriod() {
        return new LeavePeriod(LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 5));
    }

    private LeavePeriod singleDayPeriod() {
        return new LeavePeriod(LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 1));
    }

    @Test
    @DisplayName("LeaveDay is created with a valid LeavePeriod")
    void test01_validPeriod_createsLeaveDay() {
        LeaveDay leaveDay = new LeaveDay(validPeriod());
        assertEquals(5, leaveDay.numberOfDays());
    }

    @Test
    @DisplayName("LeaveDay with single-day period returns 1")
    void test02_singleDayPeriod_returnsOne() {
        LeaveDay leaveDay = new LeaveDay(singleDayPeriod());
        assertEquals(1, leaveDay.numberOfDays());
    }

    @Test
    @DisplayName("LeaveDay returns correct LeavePeriod via accessor")
    void test03_toLeavePeriod_returnsOriginalPeriod() {
        LeavePeriod period = validPeriod();
        LeaveDay leaveDay = new LeaveDay(period);
        assertEquals(period, leaveDay.toLeavePeriod());
    }

    @Test
    @DisplayName("Two LeaveDays with same period are equal")
    void test04_equalPeriods_areEqual() {
        LeavePeriod period = validPeriod();
        LeaveDay d1 = new LeaveDay(period);
        LeaveDay d2 = new LeaveDay(period);
        assertEquals(d1, d2);
    }

    @Test
    @DisplayName("LeaveDay throws if period is null")
    void test05_nullPeriod_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new LeaveDay(null));
    }

    @Test
    @DisplayName("LeaveDay equality fails for different periods")
    void test06_differentPeriods_notEqual() {
        LeaveDay d1 = new LeaveDay(new LeavePeriod(LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 5)));
        LeaveDay d2 = new LeaveDay(new LeavePeriod(LocalDate.of(2025, 9, 2), LocalDate.of(2025, 9, 6)));
        assertNotEquals(d1, d2);
    }
}
