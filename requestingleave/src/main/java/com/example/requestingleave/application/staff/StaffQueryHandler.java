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

    public Optional<StaffDTO> findStaffById(String staffId) {
        return staffRepository.findById(staffId)
                .map(StaffMapper::toStaffMemberDTO);
    }

    public List<LeaveEntitlementDTO> findLeaveEntitlementsByStaffId(String staffId) {
        return staffRepository.findById(staffId)
                .map(StaffMapper::convertToLeaveEntitlementDTOs)
                .orElseThrow(() -> new IllegalArgumentException("Staff ID is not recognised"));
    }
}
