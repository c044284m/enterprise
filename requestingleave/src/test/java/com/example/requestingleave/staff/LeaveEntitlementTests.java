package com.example.requestingleave.staff;

import com.example.common.domain.Days;
import com.example.common.domain.Identity;
import com.example.requestingleave.domain.staff.LeavePeriod;
import com.example.common.domain.UniqueIDFactory;
import com.example.requestingleave.domain.staff.LeaveEntitlement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LeaveEntitlementTests {

    private final Identity VALID_ID = UniqueIDFactory.createID();
    private final String LEAVE_TYPE = "Annual";
    private final LeavePeriod VALID_PERIOD = new LeavePeriod(
            LocalDate.of(2025, 1, 1),
            LocalDate.of(2025, 12, 31)
    );

    @Test
    @DisplayName("LeaveEntitlement is created with valid details")
    void test01_create_success() {
        LeaveEntitlement entitlement = LeaveEntitlement.entitlementOf(
                VALID_ID, LEAVE_TYPE, new Days(15), VALID_PERIOD);

        assertEquals(VALID_ID, entitlement.id());
        assertEquals("Annual", entitlement.leaveType());
        assertEquals(15, entitlement.remainingDays().asInt());
        assertEquals(VALID_PERIOD, entitlement.validPeriod());
    }

    @Test
    @DisplayName("LeaveEntitlement throws if leave type is empty")
    void test02_emptyLeaveType_throws() {
        assertThrows(IllegalArgumentException.class, () -> {
            new LeaveEntitlement(VALID_ID, "", 10, VALID_PERIOD);
        });
    }

    @Test
    @DisplayName("Deducting days reduces remaining balance")
    void test03_deductDays_success() {
        LeaveEntitlement entitlement = LeaveEntitlement.entitlementOf(
                VALID_ID, LEAVE_TYPE, new Days(10), VALID_PERIOD);

        entitlement.deductDays(new Days(3));

        assertEquals(7, entitlement.remainingDays().asInt());
    }

    @Test
    @DisplayName("Deducting more days than available throws exception")
    void test04_deductTooManyDays_throws() {
        LeaveEntitlement entitlement = LeaveEntitlement.entitlementOf(
                VALID_ID, LEAVE_TYPE, new Days(5), VALID_PERIOD);

        assertThrows(IllegalArgumentException.class, () -> {
            entitlement.deductDays(new Days(6));
        });
    }

    @Test
    @DisplayName("Two entitlements with same type and period are equal")
    void test05_equality_success() {
        LeaveEntitlement e1 = LeaveEntitlement.entitlementOf(
                VALID_ID, "Annual", new Days(10), VALID_PERIOD);

        LeaveEntitlement e2 = LeaveEntitlement.entitlementOf(
                VALID_ID, "Annual", new Days(10), VALID_PERIOD);

        assertEquals(e1, e2);
    }

    @Test
    @DisplayName("Entitlements with different types are not equal")
    void test06_differentType_notEqual() {
        LeaveEntitlement e1 = LeaveEntitlement.entitlementOf(
                VALID_ID, "Annual", new Days(10), VALID_PERIOD);

        LeaveEntitlement e2 = LeaveEntitlement.entitlementOf(
                VALID_ID, "Sick", new Days(10), VALID_PERIOD);

        assertNotEquals(e1, e2);
    }

    @Test
    @DisplayName("Entitlements with different periods are not equal")
    void test07_differentPeriod_notEqual() {
        LeavePeriod differentPeriod = new LeavePeriod(
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 30)
        );

        LeaveEntitlement e1 = LeaveEntitlement.entitlementOf(
                VALID_ID, "Annual", new Days(10), VALID_PERIOD);

        LeaveEntitlement e2 = LeaveEntitlement.entitlementOf(
                VALID_ID, "Annual", new Days(10), differentPeriod);

        assertNotEquals(e1, e2);
    }
}
