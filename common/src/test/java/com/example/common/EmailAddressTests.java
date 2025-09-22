package com.example.common;

import com.example.common.domain.EmailAddress;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmailAddressTests {

    @Test
    @DisplayName("Two email addresses with same value are equal (case-insensitive)")
    void test01() {
        EmailAddress e1 = new EmailAddress("user@example.com");
        EmailAddress e2 = new EmailAddress("USER@example.com");
        assertEquals(e1, e2);
    }

    @Test
    @DisplayName("Email address cannot be empty")
    void test02() {
        assertThrows(IllegalArgumentException.class, () -> new EmailAddress(""));
    }

    @Test
    @DisplayName("Email address must match valid format")
    void test03() {
        assertThrows(IllegalArgumentException.class, () -> new EmailAddress("invalid-email"));
    }

    @Test
    @DisplayName("Valid email address is accepted")
    void test04() {
        EmailAddress email = new EmailAddress("valid.email@domain.co.uk");
        assertEquals("valid.email@domain.co.uk", email.value());
    }

    @Test
    @DisplayName("Copy constructor creates equal email address")
    void test05() {
        EmailAddress original = new EmailAddress("copy@domain.com");
        EmailAddress copy = new EmailAddress(original);
        assertEquals(original, copy);
    }

    @Test
    @DisplayName("Email toString returns correct format")
    void test06() {
        EmailAddress email = new EmailAddress("info@softco.com");
        assertTrue(email.toString().contains("info@softco.com"));
    }
}