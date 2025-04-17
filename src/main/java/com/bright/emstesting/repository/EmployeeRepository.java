package com.bright.emstesting.repository;

import com.bright.emstesting.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
    List<Employee> findByDepartmentCodeIgnoreCase(String departmentCode);
    void deleteByEmail(String email);
    List<Employee> findByFirstNameIgnoreCase(String firstName);
    List<Employee> findByLastNameIgnoreCase(String lastName);
}

