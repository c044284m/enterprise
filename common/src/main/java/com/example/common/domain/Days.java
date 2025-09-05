package com.example.common.domain;

import java.util.Objects;

public class Days extends ValueObject {
    private int value;

    public Days(int value) {
        assertValueIsGreaterThan(value, 0, "Days must be greater than zero");
        this.value = value;
    }

    public Days(String s) {
        this(Integer.parseInt(s));
    }

    // Shallow copy
    public Days(Days days) {
        this(days.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Days other = (Days) o;
        return this.value == other.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.format("days %d", value);
    }

    public Days add(Days delta) {
        return new Days(this.value + delta.value);
    }

    public Days subtract(Days delta) {
        int result = this.value - delta.value;
        if (result < 0) throw new IllegalArgumentException("Resulting days cannot be negative");
        return new Days(result);
    }

    public boolean isGreaterThanOrEqual(Days other) {
        return this.value >= other.value;
    }

    public int asInt() {
        return this.value;
    }

    public String asString() {
        return String.valueOf(this.value);
    }

    public Days multiply(int x) {
        return new Days(this.value * x);
    }

    private void assertValueIsGreaterThan(int value, int threshold, String message) {
        if (value <= threshold) {
            throw new IllegalArgumentException(message);
        }
    }
}
