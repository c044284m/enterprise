package com.example.requestingleave.application.leaveRequest;

import com.example.requestingleave.application.leaveRequest.DTO.LeaveRequestDTO;
import com.example.requestingleave.infrastructure.leaveRequest.LeaveRequestJpa;
import com.example.requestingleave.infrastructure.leaveRequest.LeaveRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class LeaveRequestQueryHandler {
    private final LeaveRequestRepository leaveRequestRepository;

    public Iterable<LeaveRequestJpa> findAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    public Optional<Iterable<LeaveRequestDTO>> findLeaveRequestsByStaffId(String staffId) {
        return Optional.of(leaveRequestRepository.findByStaffMemberId(staffId)
                .map(requests -> StreamSupport.stream(requests.spliterator(), false)
                        .map(LeaveRequestMapper::toLeaveRequestDTO)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList()));
    }

    public Optional<LeaveRequestDTO> findLeaveRequestById(String requestId) {
        return leaveRequestRepository.findLeaveRequestById(requestId)
                .map(LeaveRequestMapper::toLeaveRequestDTO);
    }
}
