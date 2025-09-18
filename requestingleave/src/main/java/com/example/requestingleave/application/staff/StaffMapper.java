package com.example.requestingleave.application.staff;

import com.example.requestingleave.application.staff.DTO.StaffDTO;
import com.example.requestingleave.domain.staff.LeaveEntitlement;
import com.example.requestingleave.domain.staff.Staff;
import com.example.requestingleave.infrastructure.staff.StaffJpa;
import com.example.requestingleave.application.staff.DTO.LeaveEntitlementDTO;
import com.example.common.domain.LeavePeriod;
import com.example.requestingleave.infrastructure.staff.LeaveEntitlementJpa;

import java.util.List;
import java.util.stream.Collectors;

public class StaffMapper {

    public static StaffDTO toStaffMemberDTO(StaffJpa staff) {
        return new StaffDTO(
                staff.getId(),
                staff.getFullnameFirstname(),
                staff.getFullnameSurname(),
                staff.getEmailAddress(),
                staff.getDepartmentName()
        );
    }

    public static List<LeaveEntitlementDTO> convertToLeaveEntitlementDTOs(StaffJpa staff) {
        return staff.getLeaveEntitlements().stream()
                .map(StaffMapper::convertToLeaveEntitlementDTO)
                .collect(Collectors.toList());
    }

    public static LeaveEntitlementDTO convertToLeaveEntitlementDTO(LeaveEntitlementJpa entitlement) {
        LeavePeriod validPeriod = new LeavePeriod(
                entitlement.getValidFrom(),
                entitlement.getValidTo()
        );

        String staffId = entitlement.getStaff() != null ? entitlement.getStaff().getId() : null;

        return new LeaveEntitlementDTO(
                entitlement.getRemainingDays(),
                validPeriod,
                staffId
        );
    }

    public static StaffJpa domainToJpa(Staff staff) {
        StaffJpa staffJpa = new StaffJpa();

        staffJpa.setId(staff.id().id());
        staffJpa.setFullnameFirstname(staff.fullName().firstName());
        staffJpa.setFullnameSurname(staff.fullName().surname());
        staffJpa.setEmailAddress(staff.emailAddress().toString());
        staffJpa.setDepartmentName(staff.department().name());

        for (LeaveEntitlement entitlement : staff.retrieveAllLeaveEntitlements()) {
            staffJpa.addLeaveEntitlement(LeaveEntitlementMapper.toLeaveEntitlementJpa(entitlement, staffJpa));
        }

        return staffJpa;
    }


}