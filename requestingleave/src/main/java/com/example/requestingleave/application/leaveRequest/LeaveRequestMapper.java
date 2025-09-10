package com.example.requestingleave.application.leaveRequest;

import com.example.common.domain.FullName;
import com.example.common.domain.Identity;
import com.example.common.domain.LeavePeriod;
import com.example.common.dto.LeaveDayDTO;
import com.example.requestingleave.application.leaveRequest.DTO.LeaveRequestDTO;
import com.example.requestingleave.domain.leaveRequest.LeaveDay;
import com.example.requestingleave.domain.leaveRequest.LeaveRequest;
import com.example.requestingleave.domain.leaveRequest.LeaveStatus;
import com.example.requestingleave.infrastructure.leaveRequest.LeaveDayJpa;
import com.example.requestingleave.infrastructure.leaveRequest.LeaveRequestJpa;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class LeaveRequestMapper {

    public static LeaveRequestDTO toLeaveRequestDTO(LeaveRequestJpa request) {
        FullName fullName = new FullName(request.getFullnameFirstname(), request.getFullnameSurname());
        LocalDate requestedOn = request.getRequestedOn();
        LeaveStatus leaveStatus = LeaveStatus.values()[request.getLeaveStatus()];

        LeaveRequestDTO dto = new LeaveRequestDTO(
                request.getId(),
                request.getStaffMemberId(),
                fullName,
                requestedOn,
                leaveStatus,
                request.getDescriptionOfStatus()
        );

        if (request.getLeaveDays() != null) {
            List<LeaveDayDTO> dayDTOs = request.getLeaveDays().stream()
                    .map(day -> new LeaveDayDTO(
                            request.getId(),
                            day.getStartDate(),
                            day.getEndDate(),
                            day.getDurationDays()
                    ))
                    .collect(Collectors.toList());

            dto.addLeaveDays(dayDTOs);
        }

        return dto;
    }

    public static LeaveRequestJpa toJpa(LeaveRequest request) {
        LeaveRequestJpa jpa = LeaveRequestJpa.leaveRequestJpaOf(
                request.id().id(),
                request.staffMemberID(),
                request.fullNameOfStaff().firstName(),
                request.fullNameOfStaff().surname(),
                request.requestedOn(),
                request.leaveStatus().ordinal(),
                request.statusDescription()
        );

        List<LeaveDayJpa> leaveDayJpas = toListLeaveDayJpa(request.leaveDays(), request.id().id());
        jpa.setLeaveDays(leaveDayJpas);

        return jpa;
    }

    private static List<LeaveDayJpa> toListLeaveDayJpa(List<LeaveDay> leaveDays, String requestId) {
        return leaveDays.stream()
                .map(day -> {
                    LeaveDayJpa dayJpa = new LeaveDayJpa();
                    dayJpa.setStartDate(day.toLeavePeriod().startDate());
                    dayJpa.setEndDate(day.toLeavePeriod().endDate());
                    dayJpa.setDurationDays(day.numberOfDays());
                    dayJpa.setLeaveRequestId(requestId);
                    return dayJpa;
                })
                .collect(Collectors.toList());
    }

    public static LeaveRequest toDomain(LeaveRequestJpa jpa) {
        Identity requestId = new Identity(jpa.getId());
        FullName fullName = new FullName(jpa.getFullnameFirstname(), jpa.getFullnameSurname());

        List<LeaveDay> leaveDays = jpa.getLeaveDays().stream()
                .map(dayJpa -> new LeaveDay(new LeavePeriod(dayJpa.getStartDate(), dayJpa.getEndDate())))
                .collect(Collectors.toList());

        return LeaveRequest.leaveRequestOf(
                requestId,
                jpa.getStaffMemberId(),
                fullName,
                leaveDays,
                jpa.getRequestedOn(),
                LeaveStatus.values()[jpa.getLeaveStatus()],
                jpa.getDescriptionOfStatus()
        );
    }
}