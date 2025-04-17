package com.bright.emstesting.service.impl;


import com.bright.emstesting.dto.request.EmployeePatchDto;
import com.bright.emstesting.dto.request.EmployeeRequestDto;
import com.bright.emstesting.dto.response.EmployeeResponseDto;
import com.bright.emstesting.exception.employee.DuplicateEmailException;
import com.bright.emstesting.exception.employee.EmployeeNotFoundException;
import com.bright.emstesting.model.Employee;
import com.bright.emstesting.repository.EmployeeRepository;
import com.bright.emstesting.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponseDto createEmployee(EmployeeRequestDto employeeRequestDto) {
        if (employeeRepository.findByEmail(employeeRequestDto.email()).isPresent()) {
            throw new DuplicateEmailException("Employee already exists with email: " + employeeRequestDto.email());
        }
        Employee employee = new Employee(
                employeeRequestDto.firstName(),
                employeeRequestDto.lastName(),
                employeeRequestDto.email(),
                employeeRequestDto.departmentCode()
        );
        Employee saved = employeeRepository.save(employee);
        return mapToResponse(saved);
    }

    private EmployeeResponseDto mapToResponse(Employee e) {
        return new EmployeeResponseDto(
                e.getFirstName(),
                e.getLastName(),
                e.getDepartmentCode()
        );
    }

    @Override
    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<EmployeeResponseDto> findByFirstName(String firstName) {
        return employeeRepository.findByFirstNameIgnoreCase(firstName).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<EmployeeResponseDto> findByLastName(String lastName) {
        return employeeRepository.findByLastNameIgnoreCase(lastName).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<EmployeeResponseDto> findByDepartmentCode(String departmentCode) {
        return employeeRepository.findByDepartmentCodeIgnoreCase(departmentCode).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public EmployeeResponseDto findByEmail(String email) {
        return employeeRepository.findByEmail(email).map(this::mapToResponse).orElseThrow(() -> new EmployeeNotFoundException(email));
    }

    @Override
    public EmployeeResponseDto updateEmployee(String currentEmail, EmployeeRequestDto employeeRequestDto) {
        Employee employee = employeeRepository.findByEmail(currentEmail).orElseThrow(() -> new EmployeeNotFoundException(currentEmail));
        String newEmail = employeeRequestDto.email();

        // Only check if email is changing
        if (!currentEmail.equalsIgnoreCase(newEmail)) {
            employeeRepository.findByEmail(newEmail).ifPresent(existing -> {
                throw new DuplicateEmailException("Another employee already uses the email: " + newEmail);
            });
            employee.setEmail(newEmail);
        }
        employee.setFirstName(employeeRequestDto.firstName());
        employee.setLastName(employeeRequestDto.lastName());
        employee.setDepartmentCode(employeeRequestDto.departmentCode());
        employeeRepository.save(employee);
        return mapToResponse(employee);
    }

    @Override
    public EmployeeResponseDto updateEmployeePartially(String email, EmployeePatchDto employeePatchDto) {
        Employee employee = employeeRepository.findByEmail(email).orElseThrow(() -> new EmployeeNotFoundException(email));
        if (employeePatchDto.firstName() != null) {
            employee.setFirstName(employeePatchDto.firstName());
        }
        if (employeePatchDto.lastName() != null) {
            employee.setLastName(employeePatchDto.lastName());
        }
        if (employeePatchDto.departmentCode() != null) {
            employee.setDepartmentCode(employeePatchDto.departmentCode());
        }
        employeeRepository.save(employee);
        return mapToResponse(employee);
    }

    @Override
    public void deleteEmployee(String email) {
        employeeRepository.findByEmail(email).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));//404
        employeeRepository.deleteByEmail(email);
    }
}
