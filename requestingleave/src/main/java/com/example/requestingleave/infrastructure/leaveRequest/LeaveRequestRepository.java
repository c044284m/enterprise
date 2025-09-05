package com.example.requestingleave.infrastructure.leaveRequest;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeaveRequestRepository extends CrudRepository<LeaveRequestJpa, String> {
    @Query("FROM leave_request lr WHERE lr.staffMemberId = :id")
    Optional<Iterable<LeaveRequestJpa>> findByStaffMemberId(String id);

    @Query("FROM leave_request lr WHERE lr.id = :id")
    Optional<LeaveRequestJpa> findLeaveRequestById(String id);
}
