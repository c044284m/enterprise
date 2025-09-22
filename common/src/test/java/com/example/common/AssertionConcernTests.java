package com.example.common;

import com.example.common.domain.AssertionConcern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AssertionConcernTests extends AssertionConcern {

    @Test
    @DisplayName("Valid UUID passes assertion")
    void test01() {
        assertDoesNotThrow(() -> assertArgumentIsUUID("123e4567-e89b-12d3-a456-426614174000"));
    }

    @Test
    @DisplayName("Invalid UUID throws exception")
    void test02() {
        assertThrows(IllegalArgumentException.class, () -> assertArgumentIsUUID("not-a-uuid"));
    }

    @Test
    @DisplayName("String exceeding max length throws exception")
    void test03() {
        assertThrows(IllegalArgumentException.class, () -> assertArgumentLength("abcde", 3, "Too long"));
    }

    @Test
    @DisplayName("String within max length passes")
    void test04() {
        assertDoesNotThrow(() -> assertArgumentLength("abc", 5, "Should pass"));
    }

    @Test
    @DisplayName("String shorter than min length throws")
    void test05() {
        assertThrows(IllegalArgumentException.class, () -> assertArgumentLength("a", 2, 5, "Too short"));
    }

    @Test
    @DisplayName("String longer than max length throws")
    void test06() {
        assertThrows(IllegalArgumentException.class, () -> assertArgumentLength("abcdef", 2, 5, "Too long"));
    }

    @Test
    @DisplayName("String within min and max length passes")
    void test07() {
        assertDoesNotThrow(() -> assertArgumentLength("abc", 2, 5, "Valid length"));
    }

    @Test
    @DisplayName("BigDecimal below minimum throws")
    void test08() {
        assertThrows(IllegalArgumentException.class, () -> assertValueIsGreaterThan(new BigDecimal("4.99"), new BigDecimal("5.00"), "Too small"));
    }

    @Test
    @DisplayName("BigDecimal above minimum passes")
    void test09() {
        assertDoesNotThrow(() -> assertValueIsGreaterThan(new BigDecimal("5.01"), new BigDecimal("5.00"), "Valid"));
    }

    @Test
    @DisplayName("Null or empty object throws not-empty assertion")
    void test10() {
        assertThrows(IllegalArgumentException.class, () -> assertArgumentNotEmpty(null, "Empty"));
        assertThrows(IllegalArgumentException.class, () -> assertArgumentNotEmpty("   ", "Empty"));
    }

    @Test
    @DisplayName("Non-empty object passes not-empty assertion")
    void test11() {
        assertDoesNotThrow(() -> assertArgumentNotEmpty("hello", "Valid"));
    }

    @Test
    @DisplayName("Date before threshold throws")
    void test12() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime earlier = now.minusDays(1);
        assertThrows(IllegalArgumentException.class, () -> assertDateIsBefore(earlier, now, "Too early"));
    }

    @Test
    @DisplayName("Date after threshold throws")
    void test13() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime later = now.plusDays(1);
        assertThrows(IllegalArgumentException.class, () -> assertDateIsAfter(later, now, "Too late"));
    }

    @Test
    @DisplayName("Date before threshold passes")
    void test14() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime later = now.plusDays(1);
        assertDoesNotThrow(() -> assertDateIsBefore(now, later, "Valid"));
    }

    @Test
    @DisplayName("Date after threshold passes")
    void test15() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime earlier = now.minusDays(1);
        assertDoesNotThrow(() -> assertDateIsAfter(now, earlier, "Valid"));
    }

    @Test
    @DisplayName("Matching regex passes")
    void test16() {
        assertDoesNotThrow(() -> assertArgumentMatches("abc123", "^[a-z0-9]+$", "Valid"));
    }

    @Test
    @DisplayName("Non-matching regex throws")
    void test17() {
        assertThrows(IllegalArgumentException.class, () -> assertArgumentMatches("ABC!", "^[a-z0-9]+$", "Invalid"));
    }
}