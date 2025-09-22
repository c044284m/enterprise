package com.example.requestingleave.leaveRequest;

import com.example.common.domain.*;
import com.example.common.events.LeaveRequestCancelledEvent;
import com.example.common.events.LeaveRequestStartedEvent;
import com.example.requestingleave.domain.leaveRequest.LeaveDay;
import com.example.requestingleave.domain.leaveRequest.LeaveRequest;
import com.example.requestingleave.domain.leaveRequest.LeaveStatus;
import com.example.requestingleave.domain.leaveRequest.Leave_Status_Description_Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LeaveRequestTests {

    private final Identity VALID_ID = UniqueIDFactory.createID();
    private final FullName VALID_NAME = new FullName("Alice", "Johnson");
    private final String VALID_STAFF_ID = "S001";
    private final LeaveDay VALID_LEAVE_DAY = new LeaveDay(new LeavePeriod(LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 5)));
    private final List<LeaveDay> VALID_LEAVE_DAYS = List.of(VALID_LEAVE_DAY);

    @Test
    @DisplayName("LeaveRequest is created with valid details and emits LeaveRequestStartedEvent")
    void test01_createWithEvent_emitsStartedEvent() {
        LeaveRequest request = LeaveRequest.createWithEvent(
                VALID_ID, VALID_STAFF_ID, VALID_NAME, VALID_LEAVE_DAYS, LocalDate.of(2025, 9, 20));

        assertEquals(VALID_STAFF_ID, request.staffMemberID());
        assertEquals(LeaveStatus.Pending, request.leaveStatus());
        assertEquals(1, request.listOfDomainEvents().size());
        assertInstanceOf(LeaveRequestStartedEvent.class, request.listOfDomainEvents().getFirst());
    }

    @Test
    @DisplayName("Approving a pending LeaveRequest updates status to Approved")
    void test02_markAsApproved_setsStatus() {
        LeaveRequest request = LeaveRequest.createWithEvent(
                VALID_ID, VALID_STAFF_ID, VALID_NAME, VALID_LEAVE_DAYS, LocalDate.now());

        request.markAsApproved();

        assertEquals(LeaveStatus.Approved, request.leaveStatus());
        assertEquals(Leave_Status_Description_Constants.APPROVED, request.statusDescription());
    }

    @Test
    @DisplayName("Rejecting a pending LeaveRequest updates status and emits cancellation event")
    void test03_markAsRejected_setsStatusAndEvent() {
        LeaveRequest request = LeaveRequest.createWithEvent(
                VALID_ID, VALID_STAFF_ID, VALID_NAME, VALID_LEAVE_DAYS, LocalDate.now());

        request.markAsRejected();

        assertEquals(LeaveStatus.Rejected, request.leaveStatus());
        assertEquals(Leave_Status_Description_Constants.REJECTED, request.statusDescription());
        assertTrue(request.listOfDomainEvents().stream().anyMatch(e -> e instanceof LeaveRequestCancelledEvent));
    }

    @Test
    @DisplayName("Cancelling a pending LeaveRequest updates status and emits cancellation event")
    void test04_cancel_setsStatusAndEvent() {
        LeaveRequest request = LeaveRequest.createWithEvent(
                VALID_ID, VALID_STAFF_ID, VALID_NAME, VALID_LEAVE_DAYS, LocalDate.now());

        request.cancel();

        assertEquals(LeaveStatus.Cancelled, request.leaveStatus());
        assertEquals(Leave_Status_Description_Constants.CANCELLED, request.statusDescription());
        assertTrue(request.listOfDomainEvents().stream().anyMatch(e -> e instanceof LeaveRequestCancelledEvent));
    }

    @Test
    @DisplayName("Approving a non-pending LeaveRequest throws exception")
    void test05_markAsApproved_invalidState_throws() {
        LeaveRequest request = LeaveRequest.createWithEvent(
                VALID_ID, VALID_STAFF_ID, VALID_NAME, VALID_LEAVE_DAYS, LocalDate.now());

        request.markAsRejected(); // now status is Rejected

        assertThrows(IllegalStateException.class, request::markAsApproved);
    }

    @Test
    @DisplayName("Rejecting a non-pending LeaveRequest throws exception")
    void test06_markAsRejected_invalidState_throws() {
        LeaveRequest request = LeaveRequest.createWithEvent(
                VALID_ID, VALID_STAFF_ID, VALID_NAME, VALID_LEAVE_DAYS, LocalDate.now());

        request.markAsApproved(); // now status is Approved

        assertThrows(IllegalStateException.class, request::markAsRejected);
    }

    @Test
    @DisplayName("Cancelling a non-pending LeaveRequest throws exception")
    void test07_cancel_invalidState_throws() {
        LeaveRequest request = LeaveRequest.createWithEvent(
                VALID_ID, VALID_STAFF_ID, VALID_NAME, VALID_LEAVE_DAYS, LocalDate.now());

        request.markAsApproved(); // now status is Approved

        assertThrows(IllegalStateException.class, request::cancel);
    }

    @Test
    @DisplayName("LeaveRequest calculates total days correctly")
    void test08_totalDaysRequested_returnsCorrectSum() {
        LeaveRequest request = LeaveRequest.createWithEvent(
                VALID_ID, VALID_STAFF_ID, VALID_NAME, VALID_LEAVE_DAYS, LocalDate.now());

        assertEquals(5, request.totalDaysRequested());
    }

    @Test
    @DisplayName("LeaveRequest returns unmodifiable list of leave days")
    void test09_leaveDays_isUnmodifiable() {
        LeaveRequest request = LeaveRequest.createWithEvent(
                VALID_ID, VALID_STAFF_ID, VALID_NAME, VALID_LEAVE_DAYS, LocalDate.now());

        LeaveDay newDay = new LeaveDay(new LeavePeriod(
                LocalDate.of(2025, 10, 1),
                LocalDate.of(2025, 10, 3)
        ));

        assertThrows(UnsupportedOperationException.class, () -> request.leaveDays().add(newDay));
    }
    }
