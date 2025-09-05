package com.example.managingstaff.infrastructure;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "staff")
@Getter
@Setter
@NoArgsConstructor
public class StaffJpa {
    @Id
    private String id;

    private String fullName;
    private String email;
    private String department;
    private String role;

    public StaffJpa(String id, String fullName, String email, String department, String role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.department = department;
        this.role = role;
    }
}
