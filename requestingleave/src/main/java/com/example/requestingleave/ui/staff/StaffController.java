package com.example.requestingleave.ui.staff;

import com.example.common.ui.CommonController;
import com.example.requestingleave.application.events.StaffApplicationService;
import com.example.requestingleave.application.staff.StaffDomainException;
import com.example.requestingleave.application.staff.StaffQueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequestMapping("/staff")
@RestController
@AllArgsConstructor
public class StaffController extends CommonController {
    private StaffQueryHandler queryHandler;
    private StaffApplicationService staffApplicationService;

    @GetMapping("/findAll")
    @PreAuthorize("hasAuthority('ADMIN')") //Not hasRole as our role names would need to be stored with ROLE_ADMIN
    public Iterable<?> getAllStaffDetails(){
        return queryHandler.findAllStaff();
    }

    // e.g. http://localhost:8900/staff/S001
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')") //Not hasRole as our role names would need to be stored with ROLE_ADMIN
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')") //Not hasRole as our role names would need to be stored with ROLE_ADMIN
    public List<?> getLeaveEntitlementsForStaff(@PathVariable("staff_id") String staffId) {
        try {
            return queryHandler.findLeaveEntitlementsByStaffId(staffId);
        } catch (IllegalArgumentException iae) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Staff ID not found");
        }
    }

    @PostMapping("/newStaff")
    @PreAuthorize("hasAuthority('ADMIN')") //Not hasRole as our role names would need to be stored with ROLE_ADMIN
    public HttpStatus addRestaurant(@RequestBody AddNewStaffCommand command)
            throws StaffDomainException {
        staffApplicationService.addNewStaff(command);
        return HttpStatus.CREATED;
    }
}
