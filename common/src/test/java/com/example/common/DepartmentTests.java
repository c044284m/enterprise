package com.example.common;

import com.example.common.domain.Department;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DepartmentTests {

    @Test
    @DisplayName("Two departments with same name are equal")
    void test01() {
        Department d1 = new Department("Engineering");
        Department d2 = new Department("engineering");
        assertEquals(d1, d2);
    }

    @Test
    @DisplayName("Department name must be at least 2 characters")
    void test02() {
        assertThrows(IllegalArgumentException.class, () -> new Department("A"));
    }

    @Test
    @DisplayName("Department name must not exceed 30 characters")
    void test03() {
        String longName = "A".repeat(31);
        assertThrows(IllegalArgumentException.class, () -> new Department(longName));
    }

    @Test
    @DisplayName("Valid department name is accepted")
    void test04() {
        Department dept = new Department("Human Resources");
        assertEquals("Human Resources", dept.name());
    }

    @Test
    @DisplayName("Copy constructor creates equal department")
    void test05() {
        Department original = new Department("Finance");
        Department copy = new Department(original);
        assertEquals(original, copy);
    }

    @Test
    @DisplayName("Department toString returns correct format")
    void test06() {
        Department dept = new Department("Legal");
        assertTrue(dept.toString().contains("Legal"));
    }
}