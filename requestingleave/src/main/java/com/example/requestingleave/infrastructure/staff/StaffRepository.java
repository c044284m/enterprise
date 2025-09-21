package com.example.requestingleave.infrastructure.staff;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends CrudRepository<StaffJpa, String> {
    List<StaffJpa> findByDepartmentName(String departmentName);
}
