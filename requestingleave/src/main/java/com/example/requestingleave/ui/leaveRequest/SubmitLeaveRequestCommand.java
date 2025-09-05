package com.example.requestingleave.ui.leaveRequest;

import com.example.common.domain.FullName;
import com.example.common.domain.LeavePeriod;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SubmitLeaveRequestCommand {
    private final String staffId;
    private final FullName fullName;
    private final LeavePeriod requestedPeriod;

    @JsonCreator
    public SubmitLeaveRequestCommand(
            @JsonProperty("staffId") String staffId,
            @JsonProperty("fullName") FullName fullName,
            @JsonProperty("requestedPeriod") LeavePeriod requestedPeriod) {
        this.staffId = staffId;
        this.fullName = fullName;
        this.requestedPeriod = requestedPeriod;
    }

    public String getStaffId() {
        return staffId;
    }

    public FullName getFullName() {
        return fullName;
    }

    public LeavePeriod getRequestedPeriod() {
        return requestedPeriod;
    }
}
