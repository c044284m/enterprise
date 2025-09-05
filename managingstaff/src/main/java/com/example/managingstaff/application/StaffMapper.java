package com.example.managingstaff.application;

import com.example.managingstaff.domain.Staff;
import com.example.managingstaff.infrastructure.StaffJpa;

public class StaffMapper {
    public static StaffJpa toJpa(Staff staff) {
        return new StaffJpa(
                staff.getId().id(),
                staff.getFullName(),
                staff.getEmail(),
                staff.getDepartment(),
                staff.getRole()
        );
    }
}