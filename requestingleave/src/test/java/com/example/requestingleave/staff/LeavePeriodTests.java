package com.example.requestingleave.staff;

import com.example.requestingleave.domain.staff.LeavePeriod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LeavePeriodTests {

    @Test
    @DisplayName("LeavePeriod is created with valid start and end dates")
    void test01_validPeriod_success() {
        LocalDate start = LocalDate.of(2025, 9, 1);
        LocalDate end = LocalDate.of(2025, 9, 5);

        LeavePeriod period = new LeavePeriod(start, end);

        assertEquals(start, period.startDate());
        assertEquals(end, period.endDate());
    }

    @Test
    @DisplayName("LeavePeriod throws if end date is before start date")
    void test02_invalidPeriod_throws() {
        LocalDate start = LocalDate.of(2025, 9, 5);
        LocalDate end = LocalDate.of(2025, 9, 1);

        assertThrows(IllegalArgumentException.class, () -> new LeavePeriod(start, end));
    }

    @Test
    @DisplayName("LeavePeriod allows same start and end date")
    void test03_singleDayPeriod_success() {
        LocalDate date = LocalDate.of(2025, 9, 1);
        LeavePeriod period = new LeavePeriod(date, date);

        assertEquals(date, period.startDate());
        assertEquals(date, period.endDate());
    }

    @Test
    @DisplayName("Two LeavePeriods with same dates are equal")
    void test04_equalPeriods_success() {
        LocalDate start = LocalDate.of(2025, 9, 1);
        LocalDate end = LocalDate.of(2025, 9, 5);

        LeavePeriod p1 = new LeavePeriod(start, end);
        LeavePeriod p2 = new LeavePeriod(start, end);

        assertEquals(p1, p2);
    }

    @Test
    @DisplayName("LeavePeriods with different dates are not equal")
    void test05_differentPeriods_notEqual() {
        LeavePeriod p1 = new LeavePeriod(LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 5));
        LeavePeriod p2 = new LeavePeriod(LocalDate.of(2025, 9, 2), LocalDate.of(2025, 9, 6));

        assertNotEquals(p1, p2);
    }
}
