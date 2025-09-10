package com.example.requestingleave.application.leaveRequest;

import com.example.common.domain.LeavePeriod;
import com.example.common.dto.LeaveDayDTO;
import com.example.requestingleave.domain.leaveRequest.LeaveDay;

import java.util.List;
import java.util.stream.Collectors;

public class LeaveDayMapper {

    public static List<LeaveDay> toDomain(List<LeaveDayDTO> dtos) {
        return dtos.stream()
                .map(dto -> {
                    LeavePeriod period = new LeavePeriod(dto.getStartDate(), dto.getEndDate());
                    return new LeaveDay(period);
                })
                .collect(Collectors.toList());
    }

    public static List<LeaveDayDTO> toDTO(List<LeaveDay> domainLeaveDays, String leaveRequestId) {
        return domainLeaveDays.stream()
                .map(day -> new LeaveDayDTO(
                        leaveRequestId,
                        day.toLeavePeriod().startDate(),
                        day.toLeavePeriod().endDate(),
                        day.numberOfDays()
                ))
                .collect(Collectors.toList());
    }
}