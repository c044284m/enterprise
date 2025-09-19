package com.example.requestingleave.application.events;

import com.example.requestingleave.application.leaveRequest.RequestingLeaveService;
import com.example.common.events.LeaveRequestApprovedEvent;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class LeaveRequestApprovedEventListener {
    private final RequestingLeaveService requestingLeaveService;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @RabbitListener(queues = "${rabbit.queue.leaveRequestApproved}", id = "leaveRequestApprovedListener")
    public void receiveLeaveRequestApproved(LeaveRequestApprovedEvent event) {
        LOG.info("LeaveRequestApprovedEvent received by RequestingLeaveService");
//        requestingLeaveService.markRequestAsApproved(event);
    }
}