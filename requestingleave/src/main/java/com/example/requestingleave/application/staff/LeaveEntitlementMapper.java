package com.example.requestingleave.application.staff;

import com.example.requestingleave.domain.staff.LeaveEntitlement;
import com.example.requestingleave.infrastructure.staff.LeaveEntitlementJpa;
import com.example.requestingleave.infrastructure.staff.StaffJpa;

public class LeaveEntitlementMapper {

    public static LeaveEntitlementJpa toLeaveEntitlementJpa(LeaveEntitlement entitlement, StaffJpa staffJpa) {
        LeaveEntitlementJpa jpa = new LeaveEntitlementJpa();

        jpa.setRemainingDays(entitlement.remainingDays().asInt());
        jpa.setValidFrom(entitlement.validPeriod().startDate());
        jpa.setValidTo(entitlement.validPeriod().endDate());
        jpa.setStaff(staffJpa);

        return jpa;
    }
}
