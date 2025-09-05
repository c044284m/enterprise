package com.example.approvingleave.application.events;

import com.example.approvingleave.application.ApprovingLeaveService;
import com.example.common.events.LeaveRequestStartedEvent;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class LeaveRequestStartedEventListener {
    private ApprovingLeaveService approvingLeaveService;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @RabbitListener(queues = "leaveRequestStarted", id = "leaveRequestStartedListener")
    public void receiveLeaveRequestStarted(LeaveRequestStartedEvent event) {
        LOG.info("LeaveRequestStartedEvent received by ApprovingLeaveService");
        approvingLeaveService.processLeaveRequest(event);
    }
}
