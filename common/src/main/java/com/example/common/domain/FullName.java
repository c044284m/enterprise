package com.example.common.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class FullName extends ValueObject {
    private String surname;
    private String firstName;

    public FullName(@JsonProperty("firstName") String firstName,
                    @JsonProperty("surname") String surname){
        setFirstName(firstName);
        setSurname(surname);
    }

    //Shallow copy
    public FullName(FullName fullName){
        this(fullName.firstName, fullName.surname);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FullName other)) return false;
        return firstName.equals(other.firstName) && surname.equals(other.surname);
    }

    private void setFirstName(String firstName){
        this.assertArgumentLength(firstName, 1,20,"first name must be no more than 20 characters");
        this.firstName = firstName;
    }

    private void setSurname(String surname){
        this.assertArgumentLength(surname, 1,20,"surname must be no more than 20 characters");
        this.surname = surname;
    }

    public String firstName(){
        return this.firstName;
    }

    public String surname(){
        return this.surname;
    }
}
