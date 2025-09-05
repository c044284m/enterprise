package com.example.common.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EmailAddress extends ValueObject {
    private String value;

    public EmailAddress(String value) {
        setValue(value);
    }

    // Shallow copy
    public EmailAddress(EmailAddress emailAddress) {
        this(emailAddress.value);
    }

    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        EmailAddress other = (EmailAddress) o;
        return this.value.equalsIgnoreCase(other.value);
    }

    private void setValue(String value) {
        this.assertArgumentNotEmpty(value, "Email cannot be empty");
        this.assertArgumentMatches(value, "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", "Invalid email format");
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}