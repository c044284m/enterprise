package com.example.requestingleave.infrastructure.leaveRequest;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "leave_request")
@Table(name = "leave_request")
@ToString
@Getter
@Setter
public class LeaveRequestJpa {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "staff_member_id")
    private String staffMemberId;

    @Column(name = "fullname_firstname")
    private String fullnameFirstname;

    @Column(name = "fullname_surname")
    private String fullnameSurname;

    @Column(name = "requested_on")
    private LocalDate requestedOn;

    @Column(name = "leave_status")
    private int leaveStatus;

    @Column(name = "description_of_status")
    private String descriptionOfStatus;

    @OneToMany(mappedBy = "leaveRequestId", cascade = CascadeType.ALL)
    private List<LeaveDayJpa> leaveDays;

    public LeaveRequestJpa() {}

    protected LeaveRequestJpa(String id,
                              String staffMemberId,
                              String fullnameFirstname,
                              String fullnameSurname,
                              LocalDate requestedOn,
                              int leaveStatus,
                              String descriptionOfStatus) {
        this.id = id;
        this.staffMemberId = staffMemberId;
        this.fullnameFirstname = fullnameFirstname;
        this.fullnameSurname = fullnameSurname;
        this.requestedOn = requestedOn;
        this.leaveStatus = leaveStatus;
        this.descriptionOfStatus = descriptionOfStatus;
        this.leaveDays = new ArrayList<>();
    }

    public static LeaveRequestJpa leaveRequestJpaOf(String id,
                                                    String staffMemberId,
                                                    String fullnameFirstname,
                                                    String fullnameSurname,
                                                    LocalDate requestedOn,
                                                    int leaveStatus,
                                                    String descriptionOfStatus) {
        return new LeaveRequestJpa(id, staffMemberId, fullnameFirstname, fullnameSurname,
                requestedOn, leaveStatus, descriptionOfStatus);
    }

    public void addLeaveDay(LeaveDayJpa leaveDayJpa) {
        leaveDays.add(leaveDayJpa);
    }
}