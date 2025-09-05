package com.example.requestingleave.ui.leaveRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class CancelLeaveRequestCommand {
    private String requestId;
}
