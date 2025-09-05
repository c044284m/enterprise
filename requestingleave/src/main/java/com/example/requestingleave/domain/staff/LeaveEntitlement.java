package com.example.requestingleave.domain.staff;

import com.example.common.domain.Entity;
import com.example.common.domain.Identity;
import com.example.common.domain.Days;
import lombok.ToString;

@ToString
public class LeaveEntitlement extends Entity {
    private String leaveType;
    private Days remainingDays;
    private LeavePeriod validPeriod;

    public LeaveEntitlement(Identity id, String leaveType, int totalDays, LeavePeriod validPeriod) {
        super(id);
        setLeaveType(leaveType);
        this.remainingDays = new Days(totalDays);
        this.validPeriod = validPeriod;
    }

    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) return false;
        LeaveEntitlement other = (LeaveEntitlement) o;
        return other.leaveType.equals(this.leaveType) &&
                other.validPeriod.equals(this.validPeriod);
    }

    private void setLeaveType(String leaveType) {
        this.assertArgumentNotEmpty(leaveType, "Leave type cannot be empty");
        this.leaveType = leaveType;
    }

    public String leaveType() {
        return leaveType;
    }

    public Days remainingDays() {
        return remainingDays;
    }

    public LeavePeriod validPeriod() {
        return validPeriod;
    }

    public void deductDays(Days daysTaken) {
        if (!remainingDays.isGreaterThanOrEqual(daysTaken)) {
            throw new IllegalArgumentException("Insufficient leave balance");
        }
        this.remainingDays = remainingDays.subtract(daysTaken);
    }
}
