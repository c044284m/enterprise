package com.example.requestingleave.staff;

import com.example.common.domain.*;
import com.example.requestingleave.domain.events.StaffAddedEvent;
import com.example.requestingleave.domain.staff.LeaveEntitlement;
import com.example.requestingleave.domain.staff.Staff;
import com.example.requestingleave.domain.staff.LeavePeriod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StaffTests {

    private final Identity VALID_ID = UniqueIDFactory.createID();
    private final FullName VALID_NAME = new FullName("Alice", "Johnson");
    private final EmailAddress VALID_EMAIL = new EmailAddress("alice.johnson@softco.com");
    private final Department VALID_DEPARTMENT = new Department("Engineering");

    @Test
    @DisplayName("Staff is created with valid details")
    void test01_createStaff_success() {
        Staff staff = Staff.staffOf(VALID_ID, VALID_NAME, VALID_EMAIL, VALID_DEPARTMENT);

        assertEquals(VALID_ID, staff.id());
        assertEquals(VALID_NAME, staff.fullName());
        assertEquals(VALID_EMAIL, staff.emailAddress());
        assertEquals(VALID_DEPARTMENT, staff.department());
        assertTrue(staff.retrieveAllLeaveEntitlements().isEmpty());
    }

    @Test
    @DisplayName("Staff full name can be updated")
    void test02_updateFullName_success() {
        Staff staff = Staff.staffOf(VALID_ID, VALID_NAME, VALID_EMAIL, VALID_DEPARTMENT);
        FullName newName = new FullName("Bob", "Smith");

        staff.updateFullName(newName);

        assertEquals(newName, staff.fullName());
    }

    @Test
    @DisplayName("Staff email address can be updated")
    void test03_updateEmail_success() {
        Staff staff = Staff.staffOf(VALID_ID, VALID_NAME, VALID_EMAIL, VALID_DEPARTMENT);
        EmailAddress newEmail = new EmailAddress("bob.smith@softco.com");

        staff.updateEmailAddress(newEmail);

        assertEquals(newEmail, staff.emailAddress());
    }

    @Test
    @DisplayName("Staff department can be updated")
    void test04_updateDepartment_success() {
        Staff staff = Staff.staffOf(VALID_ID, VALID_NAME, VALID_EMAIL, VALID_DEPARTMENT);
        Department newDept = new Department("Finance");

        staff.updateDepartment(newDept);

        assertEquals(newDept, staff.department());
    }

    @Test
    @DisplayName("Staff with event emits StaffAddedEvent")
    void test05_staffOfWithEvent_emitsEvent() {
        Staff staff = Staff.staffOfWithEvent(VALID_ID, VALID_NAME, VALID_EMAIL, VALID_DEPARTMENT, List.of());

        assertEquals(1, staff.listOfDomainEvents().size());
        assertInstanceOf(StaffAddedEvent.class, staff.listOfDomainEvents().getFirst());

        StaffAddedEvent event = (StaffAddedEvent) staff.listOfDomainEvents().getFirst();
        assertEquals(VALID_ID.id(), event.getAggregateId());
        assertEquals("Alice Johnson", event.getFullName());
        assertEquals("Engineering", event.getDepartment());
    }

    @Test
    @DisplayName("Staff leave entitlements list is unmodifiable")
    void test06_entitlementsList_isUnmodifiable() {
        Staff staff = Staff.staffOf(VALID_ID, VALID_NAME, VALID_EMAIL, VALID_DEPARTMENT);
        assertThrows(UnsupportedOperationException.class, () -> staff.retrieveAllLeaveEntitlements().add(null));
    }

    @Test
    @DisplayName("Adding entitlement replaces existing one with same type")
    void test07_addOrUpdateLeaveEntitlement_replacesExisting() {
        Staff staff = Staff.staffOf(VALID_ID, VALID_NAME, VALID_EMAIL, VALID_DEPARTMENT);

        LeaveEntitlement entitlement1 = LeaveEntitlement.entitlementOf(
                UniqueIDFactory.createID(),
                "Annual",
                new Days(10),
                new LeavePeriod(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31))
        );

        LeaveEntitlement entitlement2 = LeaveEntitlement.entitlementOf(
                UniqueIDFactory.createID(),
                "Annual",
                new Days(15),
                new LeavePeriod(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31))
        );

        staff.addOrUpdateLeaveEntitlement(entitlement1);
        assertEquals(1, staff.retrieveAllLeaveEntitlements().size());

        staff.addOrUpdateLeaveEntitlement(entitlement2);
        assertEquals(1, staff.retrieveAllLeaveEntitlements().size());
        assertEquals(entitlement2, staff.retrieveAllLeaveEntitlements().getFirst());
    }
}
