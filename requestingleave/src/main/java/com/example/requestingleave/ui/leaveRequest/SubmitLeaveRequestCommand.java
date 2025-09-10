package com.example.requestingleave.ui.leaveRequest;

import com.example.common.domain.FullName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import com.example.common.dto.LeaveDayDTO;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubmitLeaveRequestCommand {
    private String staffId;
    private FullName fullName;
    private LocalDate requestedOn;
    private List<LeaveDayDTO> leaveDays;

    @Override
    public String toString() {
        String leaveDaysAsString = leaveDays.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));

        return String.format(
                "\nStaff: %s [%s], Requested On: %s, Requested Leave Days:\n[%s]",
                staffId,
                fullName,
                requestedOn,
                leaveDaysAsString
        );
    }
}