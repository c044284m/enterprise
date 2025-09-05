package com.example.approvingleave.application;

import com.example.approvingleave.application.events.DomainEventManager;
import com.example.common.events.*;
import com.example.common.domain.Identity;
import com.example.common.domain.UniqueIDFactory;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ApprovingLeaveService {
    private final DomainEventManager domainEventManager;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    public void processLeaveRequest(LeaveRequestStartedEvent event) {
        LOG.info("LeaveRequestStartedEvent received by ApprovingLeaveService");

        // Simulate auto-approval logic (in real case, you'd validate entitlements, dates, etc.)
        Identity approvalId = UniqueIDFactory.createID();

        LeaveRequestApprovedEvent approvedEvent = new LeaveRequestApprovedEvent(
                approvalId,
                event.getAggregateID().id(),
                event.getStaffId(),
                //event.getLeaveType(),
                event.getRequestedPeriod()
        );

        List<Event> domainEvents = new ArrayList<>();
        domainEvents.add(approvedEvent);
        domainEventManager.manageDomainEvents(this, domainEvents);
    }

    public void cancelLeaveRequest(LeaveRequestCancelledEvent event) {
        LOG.info("LeaveRequestCancelledEvent received by ApprovingLeaveService");

        // In a full implementation, you'd retrieve the approval ticket and mark it cancelled
        // For now, just log and optionally raise a local event
    }
}
