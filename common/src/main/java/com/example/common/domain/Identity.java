package com.example.common.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter //needed for JSON serialisation in events (unless convert to DTO)
public class Identity extends ValueObject{
    protected String id;

    @JsonCreator
    public Identity(@JsonProperty("id") String id) {
        setID(id);
    }

    private void setID(String id){
        this.assertArgumentNotEmpty(id,"id cannot be empty");
        this.id = id;
    }

    public String id(){
        return id;
    }

    public String toString(){ return id;}
}


