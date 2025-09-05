package com.example.managingstaff.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<StaffJpa, String> {
}
