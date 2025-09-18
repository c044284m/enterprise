package com.example.requestingleave.application.staff;

import com.example.common.domain.*;
import com.example.requestingleave.application.events.DomainEventManager;
import com.example.requestingleave.domain.staff.Staff;
import com.example.requestingleave.infrastructure.staff.StaffRepository;
import com.example.requestingleave.ui.staff.AddNewStaffCommand;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StaffService {
    private final StaffRepository staffRepository;
    private final DomainEventManager domainEventManager;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Transactional
    public void addNewStaffMember(AddNewStaffCommand command) throws StaffDomainException {
        try {
//            Identity newStaffId = UniqueIDFactory.createID();
//            LOG.info("Creating new staff member with ID {}", newStaffId);
//
//            FullName fullName = FullName.fromRaw(command.getFullName());
//            EmailAddress email = EmailAddress.of(command.getEmail());
//            Department department = Department.of(command.getDepartment());
//
//            Staff newStaff = Staff.staffOf(newStaffId, fullName, email, department);
//
//            command.getLeaveEntitlements().forEach(newStaff::addOrUpdateLeaveEntitlement);
//
//            staffRepository.save(StaffMapper.toJpa(newStaff));
//
//            // Optional: publish domain events if needed
//            domainEventManager.manageDomainEvents(this, newStaff.listOfDomainEvents());
        } catch (IllegalArgumentException e) {
//            LOG.warn("Failed to add staff member: {}", e.getMessage());
//            throw new StaffDomainException(e.getMessage());
        }
    }
}
