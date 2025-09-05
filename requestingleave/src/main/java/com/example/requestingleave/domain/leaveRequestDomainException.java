package com.example.requestingleave.domain;

public class leaveRequestDomainException extends Exception {
    private String message;
    public leaveRequestDomainException(String message){
        this.message = message;
    }

    public String toString(){
        return message;
    }
}