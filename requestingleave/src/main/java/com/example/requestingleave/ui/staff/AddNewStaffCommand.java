package com.example.requestingleave.ui.staff;

import com.example.common.domain.Department;
import com.example.common.domain.EmailAddress;
import com.example.common.domain.FullName;
import com.example.requestingleave.domain.staff.LeaveEntitlement;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AddNewStaffCommand {
    FullName fullName;
    EmailAddress email;
    Department department;
    List<LeaveEntitlement> leaveEntitlements;
}