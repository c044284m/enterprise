package com.example.common;

import com.example.common.domain.LeavePeriod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LeavePeriodTests {

    private LeavePeriod createPeriod(LocalDate start, LocalDate end) {
        return new LeavePeriod(start, end);
    }

    @Test
    @DisplayName("Two LeavePeriods with same dates are equal")
    void test01() {
        LocalDate start = LocalDate.of(2025, 9, 1);
        LocalDate end = LocalDate.of(2025, 9, 5);
        LeavePeriod p1 = createPeriod(start, end);
        LeavePeriod p2 = createPeriod(start, end);
        assertEquals(p1, p2);
    }

    @Test
    @DisplayName("LeavePeriod throws if start date is null")
    void test02() {
        LocalDate end = LocalDate.of(2025, 9, 5);
        assertThrows(IllegalArgumentException.class, () -> new LeavePeriod(null, end));
    }

    @Test
    @DisplayName("LeavePeriod throws if end date is null")
    void test03() {
        LocalDate start = LocalDate.of(2025, 9, 1);
        assertThrows(IllegalArgumentException.class, () -> new LeavePeriod(start, null));
    }

    @Test
    @DisplayName("LeavePeriod throws if end date is before start date")
    void test04() {
        LocalDate start = LocalDate.of(2025, 9, 5);
        LocalDate end = LocalDate.of(2025, 9, 1);
        assertThrows(IllegalArgumentException.class, () -> new LeavePeriod(start, end));
    }

    @Test
    @DisplayName("LeavePeriod calculates correct number of days")
    void test05() {
        LeavePeriod period = createPeriod(LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 5));
        assertEquals(5, period.numberOfDays());
    }

    @Test
    @DisplayName("LeavePeriod with same start and end date is 1 day")
    void test06() {
        LocalDate date = LocalDate.of(2025, 9, 1);
        LeavePeriod period = createPeriod(date, date);
        assertEquals(1, period.numberOfDays());
    }

    @Test
    @DisplayName("Copy constructor creates equal LeavePeriod")
    void test07() {
        LeavePeriod original = createPeriod(LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 5));
        LeavePeriod copy = new LeavePeriod(original);
        assertEquals(original, copy);
    }

    @Test
    @DisplayName("LeavePeriod accessor methods return correct dates")
    void test08() {
        LocalDate start = LocalDate.of(2025, 9, 1);
        LocalDate end = LocalDate.of(2025, 9, 5);
        LeavePeriod period = createPeriod(start, end);
        assertEquals(start, period.startDate());
        assertEquals(end, period.endDate());
    }
}