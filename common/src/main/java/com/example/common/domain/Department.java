package com.example.common.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Department extends ValueObject {
    private String name;

    public Department(String name) {
        setName(name);
    }

    // Shallow copy
    public Department(Department department) {
        this(department.name);
    }

    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        Department other = (Department) o;
        return this.name.equalsIgnoreCase(other.name);
    }

    private void setName(String name) {
        this.assertArgumentLength(name, 2, 30, "Department name must be between 2 and 30 characters");
        this.name = name;
    }

    public String name() {
        return this.name;
    }
}
