package com.example.requestingleave.application.staff;

import com.example.common.domain.Identity;
import com.example.requestingleave.domain.staff.LeaveEntitlement;
import com.example.requestingleave.domain.staff.LeavePeriod;
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

    public static LeaveEntitlement toDomain(LeaveEntitlementJpa jpa) {
        return new LeaveEntitlement(
                new Identity(String.valueOf(jpa.getId())),
                "Annual", // or extract from JPA if you store leaveType
                jpa.getRemainingDays(),
                new LeavePeriod(jpa.getValidFrom(), jpa.getValidTo())
        );
    }
}
