package com.example.requestingleave.domain.leaveRequest;

import com.example.common.domain.*;
import com.example.common.events.LeaveRequestCancelledEvent;
import com.example.common.events.LeaveRequestStartedEvent;
import com.example.requestingleave.domain.staff.LeaveEntitlement;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class LeaveRequest extends Entity {
    private final String staffMemberID;
    private final FullName fullNameOfStaff;
    private final List<LeaveDay> leaveDays;
    private LocalDate requestedOn;
    private LeaveStatus leaveStatus;
    private String statusDescription;

    public LeaveRequest(Identity id,
                        String staffMemberID,
                        FullName fullNameOfStaff,
                        List<LeaveDay> leaveDays) {
        super(id);
        assertArgumentNotEmpty(staffMemberID, "StaffMemberID cannot be empty");
        this.staffMemberID = staffMemberID;
        this.fullNameOfStaff = fullNameOfStaff;
        this.leaveDays = leaveDays;
        this.requestedOn = LocalDate.now();
        this.leaveStatus = LeaveStatus.Pending;
        this.statusDescription = Leave_Status_Description_Constants.PENDING;
    }

    // Event-driven factory method
    public static LeaveRequest createWithEvent(Identity id,
                                               String staffId,
                                               FullName fullName,
                                               List<LeaveDay> leaveDays,
                                               LocalDate requestedOn) {
        LeaveRequest request = new LeaveRequest(id, staffId, fullName, leaveDays);
        request.requestedOn = requestedOn;
        request.leaveStatus = LeaveStatus.Pending;
        request.statusDescription = Leave_Status_Description_Constants.PENDING;

        LeavePeriod period = leaveDays.get(0).toLeavePeriod();
        String occurredOn = requestedOn != null ? requestedOn.toString() : LocalDate.now().toString();
        request.addDomainEvent(new LeaveRequestStartedEvent(id, staffId, period, occurredOn));

        return request;
    }

    // Used by mapper
    public static LeaveRequest leaveRequestOf(Identity id,
                                              String staffMemberID,
                                              FullName fullNameOfStaff,
                                              List<LeaveDay> leaveDays,
                                              LocalDate requestedOn,
                                              LeaveStatus leaveStatus,
                                              String statusDescription) {
        LeaveRequest request = new LeaveRequest(id, staffMemberID, fullNameOfStaff, leaveDays);
        request.requestedOn = requestedOn;
        request.leaveStatus = leaveStatus;
        request.statusDescription = statusDescription;
        return request;
    }

    public void markAsApproved() {
        if (leaveStatus != LeaveStatus.Pending) {
            throw new IllegalStateException("Cannot approve a request that is not pending");
        }
        this.leaveStatus = LeaveStatus.Approved;
        this.statusDescription = Leave_Status_Description_Constants.APPROVED;

//        LeavePeriod approvedPeriod = leaveDays.get(0).toLeavePeriod();
//        this.addDomainEvent(new LeaveRequestApprovedEvent(
//                approverAggregateId,
//                this.staffMemberID,
//                "Annual", // could be dynamic
//                approvedPeriod
//        ));
    }

    public void markAsRejected() {
        if (leaveStatus != LeaveStatus.Pending) {
            throw new IllegalStateException("Cannot reject a request that is not pending");
        }
        this.leaveStatus = LeaveStatus.Rejected;
        this.statusDescription = Leave_Status_Description_Constants.REJECTED;
        this.addDomainEvent(new LeaveRequestCancelledEvent(this.id));
    }

    public void cancel() {
        if (leaveStatus != LeaveStatus.Pending) {
            throw new IllegalStateException("Cannot cancel a request that is not pending");
        }
        this.leaveStatus = LeaveStatus.Cancelled;
        this.statusDescription = Leave_Status_Description_Constants.CANCELLED;
        this.addDomainEvent(new LeaveRequestCancelledEvent(this.id));
    }

    // Domain-specific accessors
    public Identity id() {
        return id;
    }

    public String staffMemberID() {
        return staffMemberID;
    }

    public FullName fullNameOfStaff() {
        return fullNameOfStaff;
    }

    public LocalDate requestedOn() {
        return requestedOn;
    }

    public LeaveStatus leaveStatus() {
        return leaveStatus;
    }

    public String statusDescription() {
        return statusDescription;
    }

    public List<LeaveDay> leaveDays() {
        return Collections.unmodifiableList(leaveDays);
    }

    public int totalDaysRequested() {
        return leaveDays.stream()
                .mapToInt(LeaveDay::numberOfDays)
                .sum();
    }

    public void applyToEntitlement(LeaveEntitlement entitlement) {
        Days requested = new Days(this.totalDaysRequested());

        if (!entitlement.remainingDays().isGreaterThanOrEqual(requested)) {
            throw new IllegalArgumentException("Insufficient leave balance for request");
        }
        entitlement.deductDays(requested);
    }
}