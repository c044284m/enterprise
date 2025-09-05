package com.example.requestingleave.infrastructure.leaveRequest;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity(name = "leave_day")
@Table(name = "leave_day")
@ToString
@Getter
@Setter
public class LeaveDayJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "duration_days")
    private int durationDays;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "leave_request_id")
    private String leaveRequestId;

    public LeaveDayJpa() {}

    // Constructor for DTO conversion or mapping
    protected LeaveDayJpa(int durationDays, LocalDate startDate, LocalDate endDate, String leaveRequestId) {
        this.durationDays = durationDays;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveRequestId = leaveRequestId;
    }

    // Factory method
    public static LeaveDayJpa leaveDayJpaOf(int durationDays, LocalDate startDate, LocalDate endDate, String leaveRequestId) {
        return new LeaveDayJpa(durationDays, startDate, endDate, leaveRequestId);
    }
}