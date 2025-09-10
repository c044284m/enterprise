package com.example.common.dto;

import lombok.*;
import java.time.LocalDate;

@EqualsAndHashCode
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LeaveDayDTO {
    private String leaveRequestId; // optional, useful for internal mapping
    private LocalDate startDate;
    private LocalDate endDate;
    private int durationDays;
}