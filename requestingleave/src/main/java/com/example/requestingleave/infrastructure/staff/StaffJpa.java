package com.example.requestingleave.infrastructure.staff;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "staff_member") // Needed for custom queries
@Table(name = "staff_member")
@ToString
@Getter
@Setter
public class StaffJpa {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "fullname_firstname")
    private String fullnameFirstname;

    @Column(name = "fullname_surname")
    private String fullnameSurname;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "department_name")
    private String departmentName;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
    private List<LeaveEntitlementJpaValueObject> leaveEntitlements;

    public StaffJpa() {}

    protected StaffJpa(String id,
                             String fullnameFirstname,
                             String fullnameSurname,
                             String emailAddress,
                             String departmentName) {
        this.id = id;
        this.fullnameFirstname = fullnameFirstname;
        this.fullnameSurname = fullnameSurname;
        this.emailAddress = emailAddress;
        this.departmentName = departmentName;
        this.leaveEntitlements = new ArrayList<>();
    }

    // Factory method
    public static StaffJpa staffJpaOf(String id,
                                            String fullnameFirstname,
                                            String fullnameSurname,
                                            String emailAddress,
                                            String departmentName) {
        return new StaffJpa(id, fullnameFirstname, fullnameSurname, emailAddress, departmentName);
    }
}
