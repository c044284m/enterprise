package com.example.requestingleave.application.staff.DTO;

import com.example.common.domain.LeavePeriod;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class LeaveEntitlementDTO {
    private String leaveType;
    private int remainingDays;
    private LeavePeriod validPeriod;
    private String staffMemberId;
}
