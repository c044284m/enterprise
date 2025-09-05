package com.example.requestingleave.infrastructure.staff;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity(name = "leave_entitlement")
@Table(name = "leave_entitlement")
@Setter
@Getter
@ToString
public class LeaveEntitlementJpaValueObject {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "leave_entitlement_sequence",
            sequenceName = "leave_entitlement_sequence_id",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "leave_entitlement_sequence")
    private long id;

    @Column(name = "leave_type")
    private String leaveType;

    @Column(name = "remaining_days")
    private int remainingDays;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    @ManyToOne
    @JoinColumn(name = "staff_member_id", referencedColumnName = "id")
    private StaffJpa staff;

    public LeaveEntitlementJpaValueObject() {}

    public LeaveEntitlementJpaValueObject(long id,
                                          String leaveType,
                                          int remainingDays,
                                          LocalDate validFrom,
                                          LocalDate validTo,
                                          StaffJpa staff) {
        this.id = id;
        this.leaveType = leaveType;
        this.remainingDays = remainingDays;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.staff = staff;
    }
}