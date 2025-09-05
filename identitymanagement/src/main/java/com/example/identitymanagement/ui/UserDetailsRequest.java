package com.example.identitymanagement.ui;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDetailsRequest {
    private String username;
    private String password;
}