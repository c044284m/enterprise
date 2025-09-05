package com.example.approvingleave.application.events;

import com.example.approvingleave.application.ApprovingLeaveService;
import com.example.common.events.LeaveRequestCancelledEvent;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class LeaveRequestCancelledEventListener {
    private ApprovingLeaveService approvingLeaveService;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @RabbitListener(queues = "leaveRequestCancelled", id = "leaveRequestCancelledListener")
    public void receiveLeaveRequestCancelled(LeaveRequestCancelledEvent event) {
        LOG.info("LeaveRequestCancelledEvent received by ApprovingLeaveService");
        approvingLeaveService.cancelLeaveRequest(event);
    }
}
