package com.bright.emstesting.dto.request;

public record EmployeePatchDto(
        String firstName,
        String lastName,
        String departmentCode
) {
}
