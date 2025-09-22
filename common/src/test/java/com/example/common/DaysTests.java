package com.example.common;

import com.example.common.domain.Days;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DaysTests {

    @Test
    @DisplayName("Two Days objects with the same value are equal")
    void test01() {
        Days d1 = new Days(5);
        Days d2 = new Days(5);
        assertEquals(d1, d2);
    }

    @Test
    @DisplayName("Days cannot be created with zero")
    void test02() {
        assertThrows(IllegalArgumentException.class, () -> new Days(0));
    }

    @Test
    @DisplayName("Days cannot be created with negative value")
    void test03() {
        assertThrows(IllegalArgumentException.class, () -> new Days(-3));
    }

    @Test
    @DisplayName("Days can be created from a valid string")
    void test04() {
        Days d = new Days("7");
        assertEquals(7, d.asInt());
    }

    @Test
    @DisplayName("Days throws when created from invalid string")
    void test05() {
        assertThrows(NumberFormatException.class, () -> new Days("abc"));
    }

    @Test
    @DisplayName("Days copy constructor creates equal object")
    void test06() {
        Days original = new Days(10);
        Days copy = new Days(original);
        assertEquals(original, copy);
    }

    @Test
    @DisplayName("Days add returns correct result")
    void test07() {
        Days d1 = new Days(4);
        Days d2 = new Days(6);
        Days result = d1.add(d2);
        assertEquals(10, result.asInt());
    }

    @Test
    @DisplayName("Days subtract returns correct result")
    void test08() {
        Days d1 = new Days(10);
        Days d2 = new Days(3);
        Days result = d1.subtract(d2);
        assertEquals(7, result.asInt());
    }

    @Test
    @DisplayName("Days subtract throws if result would be negative")
    void test09() {
        Days d1 = new Days(2);
        Days d2 = new Days(5);
        assertThrows(IllegalArgumentException.class, () -> d1.subtract(d2));
    }

    @Test
    @DisplayName("Days comparison returns true when greater or equal")
    void test10() {
        Days d1 = new Days(10);
        Days d2 = new Days(5);
        assertTrue(d1.isGreaterThanOrEqual(d2));
        assertTrue(d1.isGreaterThanOrEqual(new Days(10)));
    }

    @Test
    @DisplayName("Days comparison returns false when less")
    void test11() {
        Days d1 = new Days(3);
        Days d2 = new Days(5);
        assertFalse(d1.isGreaterThanOrEqual(d2));
    }

    @Test
    @DisplayName("Days toString returns correct format")
    void test12() {
        Days d = new Days(8);
        assertEquals("days 8", d.toString());
    }
}