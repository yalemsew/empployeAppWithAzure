package com.bright.emstesting.repository;

import com.bright.emstesting.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .firstName("Jhon")
                .lastName("Doe")
                .email("<EMAIL")
                .departmentCode("CS489")
                .build();
    }

    @Test
    @DisplayName("Test for creating a new Employee")
    void givenNonExistingEmployee_whenSave_thenReturnsResponseDto(){
        //Given employee is not in the database
        //when
        employeeRepository.saveAndFlush(employee);
        //Then
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
        assertTrue(employeeOptional.isPresent());
        assertEquals(employee, employeeOptional.get());

    }
}