package com.example.requestingleave.application.leaveRequest.DTO;

import com.example.common.dto.LeaveDayDTO;
import com.example.common.domain.FullName;
import com.example.requestingleave.domain.leaveRequest.LeaveStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@Getter
@ToString
public class LeaveRequestDTO {
    private final String id;
    private final String staffMemberId;
    private final FullName fullName;
    private final LocalDate requestedOn;
    private final LeaveStatus leaveStatus;
    private final String descriptionOfStatus;

    private final List<LeaveDayDTO> leaveDays;

    public LeaveRequestDTO(String id, String staffMemberId, FullName fullName,
                           LocalDate requestedOn, LeaveStatus leaveStatus, String descriptionOfStatus) {
        this.id = id;
        this.staffMemberId = staffMemberId;
        this.fullName = fullName;
        this.requestedOn = requestedOn;
        this.leaveStatus = leaveStatus;
        this.descriptionOfStatus = descriptionOfStatus;
        this.leaveDays = new ArrayList<>();
    }

    public void addLeaveDays(List<LeaveDayDTO> days) {
        leaveDays.addAll(days);
    }
}
