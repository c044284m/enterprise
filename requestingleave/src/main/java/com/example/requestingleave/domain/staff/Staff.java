package com.example.requestingleave.domain.staff;

import com.example.common.domain.Entity;
import com.example.common.domain.FullName;
import com.example.common.domain.Identity;
import com.example.common.domain.EmailAddress;
import com.example.common.domain.Department;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ToString
public class Staff extends Entity {
    private FullName fullName;
    private EmailAddress emailAddress;
    private Department department;
    private List<LeaveEntitlement> leaveEntitlements;

    public Staff(Identity id, FullName fullName, EmailAddress emailAddress, Department department) {
        super(id);
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.department = department;
        this.leaveEntitlements = new ArrayList<>();
    }

    // Factory method for testing
    public static Staff staffOf(Identity id, FullName fullName, EmailAddress emailAddress, Department department) {
        return new Staff(id, fullName, emailAddress, department);
    }

    // Domain accessors
    public Identity id() {
        return id;
    }

    public FullName fullName() {
        return fullName;
    }

    public EmailAddress emailAddress() {
        return emailAddress;
    }

    public Department department() {
        return department;
    }

    // Domain command methods
    public void updateFullName(FullName fullName) {
        this.fullName = fullName;
    }

    public void updateEmailAddress(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void updateDepartment(Department department) {
        this.department = department;
    }

    public LeaveEntitlement addOrUpdateLeaveEntitlement(LeaveEntitlement entitlement) {
        leaveEntitlements.removeIf(e -> e.leaveType().equals(entitlement.leaveType()));
        leaveEntitlements.add(entitlement);
        return entitlement;
    }

    public List<LeaveEntitlement> retrieveAllLeaveEntitlements() {
        return Collections.unmodifiableList(leaveEntitlements);
    }
}
