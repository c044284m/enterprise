package com.example.requestingleave.application.events;

import com.example.requestingleave.application.leaveRequest.RequestingLeaveService;
import com.example.common.events.LeaveRequestCancelledEvent;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class LeaveRequestRejectedEventListener {
    private final RequestingLeaveService requestingLeaveService;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @RabbitListener(queues = "${rabbit.queue.leaveRequestRejected}", id = "leaveRequestRejectedListener")
    public void receiveLeaveRequestRejected(LeaveRequestCancelledEvent event) {
        LOG.info("LeaveRequestRejectedEvent received by RequestingLeaveService");
        requestingLeaveService.markRequestAsRejected(event);
    }
}