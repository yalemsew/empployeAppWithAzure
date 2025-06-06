package com.bright.emstesting.exception.employee;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String message) {
        super("Employee not found with email: " + message);
    }
}
