package com.example.managingstaff.domain.events;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NewStaffAddedEvent {
    private final String staffId;
    private final String fullName;
    private final String email;
    private final String department;
    private final String role;

    public NewStaffAddedEvent(String staffId, String fullName, String email, String department, String role) {
        this.staffId = staffId;
        this.fullName = fullName;
        this.email = email;
        this.department = department;
        this.role = role;
    }
}
