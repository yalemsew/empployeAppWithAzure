package com.bright.emstesting.repository;

import com.bright.emstesting.exception.employee.DuplicateEmailException;
import com.bright.emstesting.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .firstName("Jhon")
                .lastName("Doe")
                .email("jhon.smith@gmail.com")
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
        assertEquals("Jhon", employeeOptional.get().getFirstName());
        assertEquals("Doe", employeeOptional.get().getLastName());

    }
    @Test
    @DisplayName("Test for saving an existing employee")
public void givenExistingEmployee_whenSave_thenThrowsException(){
        //Given
        Employee savedEmployee = employeeRepository.saveAndFlush(employee);
        Employee employee2 = Employee.builder()
                .firstName("Jhon")
                .lastName("Smith")
                .email("jhon.smith@gmail.com")
                .build();

        //throw exception when save existing employee
        assertThrows(DataIntegrityViolationException.class, () -> employeeRepository.saveAndFlush(employee2));
        //
    }
  @Test
  @DisplayName("Test for finding an employee by email")
  void givenExistingEmployee_whenFindByEmail_thenReturnEmployee(){
        employeeRepository.saveAndFlush(employee);
        Optional<Employee> employeeOptional = employeeRepository.findByEmail("jhon.smith@gmail.com");

        assertTrue(employeeOptional.isPresent());
        assertEquals("Jhon", employeeOptional.get().getFirstName());
        assertEquals("Doe", employeeOptional.get().getLastName());
        assertEquals("jhon.smith@gmail.com", employeeOptional.get().getEmail());
  }

  @Test
  @DisplayName("Test for deleting an existing employee")
  void givenExistingEmployee_whenDelete_thenDelete(){
        //Given
      Employee savedEmployee = employeeRepository.saveAndFlush(employee);
      //when
     employeeRepository.deleteById(savedEmployee.getId());
      employeeRepository.deleteByEmail(employee.getEmail());

//      Optional<Employee> foundEmployee = employeeRepository.findByEmail(employee.getEmail());

      Employee foundEmployee = employeeRepository.findByEmail(employee.getEmail()).orElse(null);
      assertNull(foundEmployee);
  }
}