package com.example.requestingleave.application.leaveRequest;

import com.example.common.domain.FullName;
import com.example.common.domain.Identity;
import com.example.requestingleave.application.leaveRequest.DTO.LeaveDayDTO;
import com.example.requestingleave.application.leaveRequest.DTO.LeaveRequestDTO;
import com.example.requestingleave.domain.leaveRequest.LeaveDay;
import com.example.requestingleave.domain.leaveRequest.LeaveRequest;
import com.example.requestingleave.domain.leaveRequest.LeaveStatus;
import com.example.common.domain.LeavePeriod;
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
                    .map(LeaveRequestMapper::toLeaveDayDTO)
                    .collect(Collectors.toList());
            dto.addLeaveDays(dayDTOs);
        }

        return dto;
    }

    public static LeaveRequestJpa toJpa(LeaveRequest request) {
        LeaveRequestJpa jpa = new LeaveRequestJpa();
        jpa.setId(request.id().id());
        jpa.setStaffMemberId(request.staffMemberID());
        jpa.setFullnameFirstname(request.fullNameOfStaff().firstName());
        jpa.setFullnameSurname(request.fullNameOfStaff().surname());
        jpa.setRequestedOn(request.requestedOn());
        jpa.setLeaveStatus(request.leaveStatus().ordinal());
        jpa.setDescriptionOfStatus(request.statusDescription());

        List<LeaveDayJpa> leaveDayJpas = request.leaveDays().stream()
                .map(day -> {
                    LeaveDayJpa dayJpa = new LeaveDayJpa();
                    dayJpa.setDurationDays(day.numberOfDays());
                    dayJpa.setStartDate(day.toLeavePeriod().startDate());
                    dayJpa.setEndDate(day.toLeavePeriod().endDate());
                    dayJpa.setLeaveRequestId(request.id().id()); // assuming FK
                    return dayJpa;
                })
                .collect(Collectors.toList());

        jpa.setLeaveDays(leaveDayJpas);
        return jpa;
    }

    public static LeaveDayDTO toLeaveDayDTO(LeaveDayJpa leaveDayJpa) {
        return new LeaveDayDTO(leaveDayJpa.getDurationDays()
        );
    }

    public static LeaveRequest toDomain(LeaveRequestJpa jpa) {
        Identity requestId = new Identity(jpa.getId());
        FullName fullName = new FullName(jpa.getFullnameFirstname(), jpa.getFullnameSurname());

        List<LeaveDay> leaveDays = jpa.getLeaveDays().stream()
                .map(dayJpa -> {
                    LeavePeriod period = new LeavePeriod(dayJpa.getStartDate(), dayJpa.getEndDate());
                    return new LeaveDay(period);
                })
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