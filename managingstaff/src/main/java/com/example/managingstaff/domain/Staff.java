package com.example.managingstaff.domain;

import com.example.common.domain.Identity;
import com.example.managingstaff.domain.events.NewStaffAddedEvent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Staff {
    private final Identity id;
    private final String fullName;
    private final String email;
    private final String department;
    private final String role;

    private final List<Object> domainEvents = new ArrayList<>();

    private Staff(Identity id, String fullName, String email, String department, String role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.department = department;
        this.role = role;
    }

    public static Staff createWithEvent(Identity id, String fullName, String email, String department, String role) {
        Staff staff = new Staff(id, fullName, email, department, role);
        staff.domainEvents.add(new NewStaffAddedEvent(id.id(), fullName, email, department, role));
        return staff;
    }

    public List<Object> listOfDomainEvents() {
        return domainEvents;
    }
}
