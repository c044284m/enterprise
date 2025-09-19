package com.example.requestingleave.application.leaveRequest;

import com.example.common.domain.Identity;
import com.example.common.domain.UniqueIDFactory;
import com.example.common.events.LeaveRequestApprovedEvent;
import com.example.common.events.LeaveRequestCancelledEvent;
import com.example.requestingleave.application.events.DomainEventManager;
import com.example.requestingleave.application.staff.LeaveEntitlementMapper;
import com.example.requestingleave.domain.leaveRequest.LeaveRequest;
import com.example.requestingleave.domain.staff.LeaveEntitlement;
import com.example.requestingleave.infrastructure.leaveRequest.LeaveRequestJpa;
import com.example.requestingleave.infrastructure.leaveRequest.LeaveRequestRepository;
import com.example.requestingleave.infrastructure.staff.LeaveEntitlementJpa;
import com.example.requestingleave.infrastructure.staff.StaffJpa;
import com.example.requestingleave.infrastructure.staff.StaffRepository;
import com.example.requestingleave.ui.leaveRequest.SubmitLeaveRequestCommand;
import com.example.requestingleave.ui.leaveRequest.CancelLeaveRequestCommand;
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

            // Construct aggregate with domain event
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

            // 3. Map to domain and apply deduction
            LeaveEntitlement entitlement = LeaveEntitlementMapper.toDomain(entitlementJpa);
            newRequest.applyToEntitlement(entitlement);

            // 4. Map back and persist updated entitlement
            LeaveEntitlementJpa updatedEntitlementJpa = LeaveEntitlementMapper.toLeaveEntitlementJpa(entitlement, staffJpa);
            staffJpa.addLeaveEntitlement(updatedEntitlementJpa); // or replace existing
            staffRepository.save(staffJpa);

            // 5. Persist leave request
            leaveRequestRepository.save(LeaveRequestMapper.toJpa(newRequest));

            // 6. Notify subscribers
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

    @Transactional
    public void markRequestAsApproved(LeaveRequestApprovedEvent event) throws RequestingLeaveDomainException {
        LeaveRequest request = findRequestAndConvertToDomain(event.getAggregateID().id());
        request.markAsApproved(event.getAggregateID());
        leaveRequestRepository.save(LeaveRequestMapper.toJpa(request));
        domainEventManager.manageDomainEvents(this, request.listOfDomainEvents());
    }

    @Transactional
    public void markRequestAsRejected(LeaveRequestCancelledEvent event) throws RequestingLeaveDomainException {
        LeaveRequest request = findRequestAndConvertToDomain(event.getAggregateID().id());
        request.markAsRejected(event.getAggregateID());
        leaveRequestRepository.save(LeaveRequestMapper.toJpa(request));
        domainEventManager.manageDomainEvents(this, request.listOfDomainEvents());
    }

    private LeaveRequest findRequestAndConvertToDomain(String requestId) throws RequestingLeaveDomainException {
        Optional<LeaveRequestJpa> requestJpa = leaveRequestRepository.findById(requestId);
        if (requestJpa.isEmpty()) {
            throw new RequestingLeaveDomainException("Leave request ID does not exist");
        }
        return LeaveRequestMapper.toDomain(requestJpa.get());
    }
}