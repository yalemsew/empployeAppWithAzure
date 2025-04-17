package com.bright.emstesting.service;

import com.bright.emstesting.dto.request.EmployeePatchDto;
import com.bright.emstesting.dto.request.EmployeeRequestDto;
import com.bright.emstesting.dto.response.EmployeeResponseDto;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    EmployeeResponseDto createEmployee(EmployeeRequestDto employeeRequestDto);
    List<EmployeeResponseDto> getAllEmployees();
    List<EmployeeResponseDto> findByFirstName(String firstName);
    List<EmployeeResponseDto> findByLastName(String lastName);
    List<EmployeeResponseDto> findByDepartmentCode(String departmentCode);
    EmployeeResponseDto findByEmail(String email);
    EmployeeResponseDto updateEmployee(String email, EmployeeRequestDto employeeRequestDto);
    EmployeeResponseDto updateEmployeePartially(String email, EmployeePatchDto employeePatchDto);
    void deleteEmployee(String email);
}
