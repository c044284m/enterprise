package com.example.requestingleave.application.leaveRequest;

import com.example.common.domain.Identity;
import com.example.common.domain.UniqueIDFactory;
import com.example.requestingleave.application.events.DomainEventManager;
import com.example.requestingleave.application.staff.LeaveEntitlementMapper;
import com.example.requestingleave.domain.leaveRequest.LeaveRequest;
import com.example.requestingleave.domain.staff.LeaveEntitlement;
import com.example.requestingleave.infrastructure.leaveRequest.LeaveRequestJpa;
import com.example.requestingleave.infrastructure.leaveRequest.LeaveRequestRepository;
import com.example.requestingleave.infrastructure.staff.LeaveEntitlementJpa;
import com.example.requestingleave.infrastructure.staff.StaffJpa;
import com.example.requestingleave.infrastructure.staff.StaffRepository;
import com.example.requestingleave.ui.leaveRequest.ApproveLeaveRequestCommand;
import com.example.requestingleave.ui.leaveRequest.CancelLeaveRequestCommand;
import com.example.requestingleave.ui.leaveRequest.RejectLeaveRequestCommand;
import com.example.requestingleave.ui.leaveRequest.SubmitLeaveRequestCommand;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RequestingLeaveService {
    private final LeaveRequestRepository leaveRequestRepository;
    private final DomainEventManager domainEventManager;
    private final StaffRepository staffRepository;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Transactional
    public void submitLeaveRequest(SubmitLeaveRequestCommand command) throws RequestingLeaveDomainException {
        try {
            Identity newRequestId = UniqueIDFactory.createID();
            LOG.info("New leave request ID is {}", newRequestId);

            var leaveDays = LeaveDayMapper.toDomain(command.getLeaveDays());

            LeaveRequest newRequest = LeaveRequest.createWithEvent(
                    newRequestId,
                    command.getStaffId(),
                    command.getFullName(),
                    leaveDays,
                    command.getRequestedOn()
            );

            StaffJpa staffJpa = staffRepository.findById(command.getStaffId())
                    .orElseThrow(() -> new RequestingLeaveDomainException("Staff not found"));

            LeaveEntitlementJpa entitlementJpa = staffJpa.getLeaveEntitlements().stream()
                    .filter(e -> e.getValidFrom().isBefore(command.getRequestedOn()) &&
                            e.getValidTo().isAfter(command.getRequestedOn()))
                    .findFirst()
                    .orElseThrow(() -> new RequestingLeaveDomainException("No valid leave entitlement found"));

            LeaveEntitlement entitlement = LeaveEntitlementMapper.toDomain(entitlementJpa);
            newRequest.applyToEntitlement(entitlement);

            LeaveEntitlementJpa updatedEntitlementJpa = LeaveEntitlementMapper.toLeaveEntitlementJpa(entitlement, staffJpa);
            staffJpa.addLeaveEntitlement(updatedEntitlementJpa);
            staffRepository.save(staffJpa);

            leaveRequestRepository.save(LeaveRequestMapper.toJpa(newRequest));

            domainEventManager.manageDomainEvents(this, newRequest.listOfDomainEvents());

        } catch (IllegalArgumentException e) {
            LOG.warn("Leave request submission failed: {}", e.getMessage());
            throw new RequestingLeaveDomainException(e.getMessage());
        }
    }

    public void cancelLeaveRequest(CancelLeaveRequestCommand command) throws RequestingLeaveDomainException {
        LeaveRequest requestToCancel = findRequestAndConvertToDomain(command.getRequestId());
        requestToCancel.cancel();
        leaveRequestRepository.save(LeaveRequestMapper.toJpa(requestToCancel));
        domainEventManager.manageDomainEvents(this, requestToCancel.listOfDomainEvents());
    }

    public void markRequestAsApproved(ApproveLeaveRequestCommand command) throws RequestingLeaveDomainException {
        LeaveRequest markAsApproved = findRequestAndConvertToDomain(command.getRequestId());
        markAsApproved.markAsApproved();
        leaveRequestRepository.save(LeaveRequestMapper.toJpa(markAsApproved));
        domainEventManager.manageDomainEvents(this, markAsApproved.listOfDomainEvents());
    }

    public void markRequestAsRejected(RejectLeaveRequestCommand command) throws RequestingLeaveDomainException {
        LeaveRequest markAsRejected = findRequestAndConvertToDomain(command.getRequestId());
        markAsRejected.markAsRejected();
        leaveRequestRepository.save(LeaveRequestMapper.toJpa(markAsRejected));
        domainEventManager.manageDomainEvents(this, markAsRejected.listOfDomainEvents());
    }

    private LeaveRequest findRequestAndConvertToDomain(String requestId) throws RequestingLeaveDomainException {
        Optional<LeaveRequestJpa> requestJpa = leaveRequestRepository.findById(requestId);
        if (requestJpa.isEmpty()) {
            throw new RequestingLeaveDomainException("Leave request ID does not exist");
        }
        return LeaveRequestMapper.toDomain(requestJpa.get());
    }
}