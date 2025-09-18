package com.example.requestingleave.application.events;

import com.example.common.domain.Identity;
import com.example.common.domain.UniqueIDFactory;
import com.example.requestingleave.application.staff.StaffDomainException;
import com.example.requestingleave.application.staff.StaffMapper;
import com.example.requestingleave.domain.staff.Staff;
import com.example.requestingleave.infrastructure.staff.StaffRepository;
import com.example.requestingleave.ui.staff.AddNewStaffCommand;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StaffApplicationService {
    private StaffRepository staffRepository;
    private DomainEventManager domainEventManager;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    public void addNewStaff(AddNewStaffCommand command) throws StaffDomainException {
        try {
            Identity staffId = new Identity(UniqueIDFactory.createID().toString());

            Staff newStaff = Staff.staffOfWithEvent(staffId,
                                                    command.getFullName(),
                                                    command.getEmail(),
                                                    command.getDepartment(),
                                                    command.getLeaveEntitlements());
            staffRepository.save(StaffMapper.domainToJpa(newStaff));

            domainEventManager.manageDomainEvents(this, newStaff.listOfDomainEvents());
        } catch (IllegalArgumentException e) {
            throw new StaffDomainException("Staff details supplied are invalid");
        }
    }
}