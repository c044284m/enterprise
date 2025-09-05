package com.example.requestingleave.ui.staff;

import com.example.requestingleave.application.staff.StaffQueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequestMapping("/staff")
@RestController
@AllArgsConstructor
public class StaffController {
    private StaffQueryHandler queryHandler;

    // e.g. http://localhost:8900/staff/S001
    @GetMapping("/{staff_id}")
    public Optional<?> getStaffById(@PathVariable("staff_id") String staffId) {
        Optional<?> result = queryHandler.findStaffById(staffId);
        if (result.isPresent()) {
            return result;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Staff ID not found");
        }
    }

    // e.g. http://localhost:8900/staff/entitlements/S001
    @GetMapping("/entitlements/{staff_id}")
    public List<?> getLeaveEntitlementsForStaff(@PathVariable("staff_id") String staffId) {
        try {
            return queryHandler.findLeaveEntitlementsByStaffId(staffId);
        } catch (IllegalArgumentException iae) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Staff ID not found");
        }
    }
}
