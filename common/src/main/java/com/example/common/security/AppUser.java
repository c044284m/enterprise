package com.example.common.security;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "app_user")
@Table(name = "app_user")
@Getter
@Setter
public class AppUser {
    //Needed for JWT generateToken method as well as with the columns below
    public final static String EMAIL = "email";
    public final static String FIRST_NAME = "first_name";
    public final static String ID = "id";
    public final static String PASSWORD = "password";
    public final static String ROLE = "role";
    public final static String SURNAME = "surname";
    public final static String USERNAME = "username";

    @Id
    @Column(name=ID)
    private String userUUID;

    @NotNull
    @Column(name=USERNAME)
    private String userName;

    @NotNull
    @Column(name=PASSWORD)
    private String password;

    @NotNull
    @Column(name=EMAIL)
    private String email;

    @NotNull
    @Column(name=FIRST_NAME)
    private String firstName;

    @NotNull
    @Column(name=SURNAME)
    private String surname;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="role_id")
    private Role role;

    public String toString(){
        return String.format("%s, %s, %s, %s %s" , userUUID,
                userName, password, email, role);
    }
}