package com.example.requestingleave.application.staff.DTO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class StaffDTO {
    private String id;
    private String fullname_firstname;
    private String fullname_surname;
    private String email;
    private String department;
}