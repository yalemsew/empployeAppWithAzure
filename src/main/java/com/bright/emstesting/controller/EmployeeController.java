package com.bright.emstesting.controller;

import com.bright.emstesting.dto.request.EmployeePatchDto;
import com.bright.emstesting.dto.request.EmployeeRequestDto;
import com.bright.emstesting.dto.response.EmployeeResponseDto;
import com.bright.emstesting.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponseDto> createEmployee(@Valid @RequestBody EmployeeRequestDto employeeRequestDto) {
        EmployeeResponseDto employeeResponseDto = employeeService.createEmployee(employeeRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getEmployees() {
        List<EmployeeResponseDto> employeeResponseDtos = employeeService.getAllEmployees();
        return ResponseEntity.status(HttpStatus.OK).body(employeeResponseDtos);
    }

    @PatchMapping("/{email}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(@PathVariable String email, @Valid @RequestBody EmployeePatchDto employeePatchDto) {
        EmployeeResponseDto employeeResponseDto = employeeService.updateEmployeePartially(email, employeePatchDto);
        return ResponseEntity.status(HttpStatus.OK).body(employeeResponseDto);
    }

    @PutMapping("/{email}")
    public ResponseEntity<EmployeeResponseDto> updateEmployeeEmail(@PathVariable String email, @Valid @RequestBody EmployeeRequestDto employeeRequestDto) {
        EmployeeResponseDto employeeResponseDto = employeeService.updateEmployee(email, employeeRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(employeeResponseDto);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String email) {
        employeeService.deleteEmployee(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
