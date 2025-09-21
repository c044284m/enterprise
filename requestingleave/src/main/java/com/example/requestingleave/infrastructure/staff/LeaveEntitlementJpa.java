package com.example.requestingleave.infrastructure.staff;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class LeaveEntitlementJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "remaining_days")
    private int remainingDays;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    @ManyToOne
    @JsonIgnore // Stops circular references
    @JoinColumn(name = "staff_member_id", referencedColumnName = "id")
    private StaffJpa staff;

    public LeaveEntitlementJpa() {}

    public LeaveEntitlementJpa(long id,
                               int remainingDays,
                               LocalDate validFrom,
                               LocalDate validTo,
                               StaffJpa staff) {
        this.id = id;
        this.remainingDays = remainingDays;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.staff = staff;
    }
}