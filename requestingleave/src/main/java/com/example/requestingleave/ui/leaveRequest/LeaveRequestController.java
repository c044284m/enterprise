package com.example.requestingleave.ui.leaveRequest;

import com.example.common.ui.CommonController;
import com.example.requestingleave.application.leaveRequest.DTO.LeaveRequestDTO;
import com.example.requestingleave.application.leaveRequest.LeaveRequestQueryHandler;
import com.example.requestingleave.application.leaveRequest.RequestingLeaveService;
import com.example.requestingleave.application.leaveRequest.RequestingLeaveDomainException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@RequestMapping("/leaveRequests")
@RestController
public class LeaveRequestController extends CommonController {
    private final LeaveRequestQueryHandler queryHandler;
    private final RequestingLeaveService requestingLeaveService;

    // GET: http://localhost:8900/leaveRequests/findAll
    @GetMapping("/findAll")
    @PreAuthorize("hasAuthority('ADMIN')") //Not hasRole as our role names would need to be stored with ROLE_ADMIN
    public Iterable<?> getAllLeaveRequests() {
        return queryHandler.findAllLeaveRequests();
    }

    // GET: http://localhost:8900/leaveRequests/staff/S001
    @GetMapping("/staff/{staff_id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')") //Not hasRole as our role names would need to be stored with ROLE_ADMIN
    public Iterable<LeaveRequestDTO> getLeaveRequestsByStaffId(@PathVariable("staff_id") String staffId) {
        return queryHandler.findLeaveRequestsByStaffId(staffId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No leave requests found for that staff ID"));
    }

    // GET: http://localhost:8900/leaveRequests/LR001
    @GetMapping("/{request_id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')") //Not hasRole as our role names would need to be stored with ROLE_ADMIN
    public LeaveRequestDTO getLeaveRequestById(@PathVariable("request_id") String requestId) {
        return queryHandler.findLeaveRequestById(requestId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leave request ID not found"));
    }

    /** POST: http://localhost:8900/leaveRequests/submit
     {
     "staffId": "S001",
     "fullName": {
     "firstName": "Alice",
     "surname": "Smith"
     },
     "requestedPeriod": {
     "startDate": "2025-09-01",
     "endDate": "2025-09-05"
     }
     }
     **/
    @PostMapping("/submit")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'USER')") //Not hasRole as our role names would need to be stored with ROLE_ADMIN
    public HttpStatus submitLeaveRequest(@RequestBody SubmitLeaveRequestCommand command)
            throws RequestingLeaveDomainException {
        requestingLeaveService.submitLeaveRequest(command);
        return HttpStatus.CREATED;
    }

    /** POST: http://localhost:8900/leaveRequests/cancel
     {
     "requestId": "LR001"
     }
     **/
    @PostMapping("/cancel")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'USER')") //Not hasRole as our role names would need to be stored with ROLE_ADMIN
    public HttpStatus cancelLeaveRequest(@RequestBody CancelLeaveRequestCommand command)
            throws RequestingLeaveDomainException {
        requestingLeaveService.cancelLeaveRequest(command);
        return HttpStatus.OK;
    }
}