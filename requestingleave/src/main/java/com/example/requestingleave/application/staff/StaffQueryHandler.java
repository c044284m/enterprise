package com.example.requestingleave.application.staff;

import com.example.requestingleave.application.staff.DTO.StaffDTO;
import com.example.requestingleave.application.staff.DTO.LeaveEntitlementDTO;
import com.example.requestingleave.infrastructure.staff.StaffJpa;
import com.example.requestingleave.infrastructure.staff.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class StaffQueryHandler {
    private final StaffRepository staffRepository;

    // Not recommended to have this method - but included here for demo purposes
    public Iterable<StaffJpa> findAllStaff() {
        return staffRepository.findAll();
    }

    public Optional<StaffDTO> findStaffById(String staffId) {
        return staffRepository.findById(staffId)
                .map(StaffMapper::toStaffMemberDTO);
    }

    public List<LeaveEntitlementDTO> findLeaveEntitlementsByStaffId(String staffId) {
        return staffRepository.findById(staffId)
                .map(StaffMapper::convertToLeaveEntitlementDTOs)
                .orElseThrow(() -> new IllegalArgumentException("Staff ID is not recognised"));
    }

    public List<?> findStaffByDepartment(String departmentName) {
        if (departmentName == null || departmentName.isBlank()) {
            throw new IllegalArgumentException("Department name cannot be empty");
        }
        return staffRepository.findByDepartmentName(departmentName);
    }
}
