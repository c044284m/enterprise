package com.example.managingstaff.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddNewStaffCommand {
    private final String fullName;
    private final String email;
    private final String department;
    private final String role;
}
