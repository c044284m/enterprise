package com.example.requestingleave.application.leaveRequest.DTO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class LeaveDayDTO {
    private int durationDays;
}