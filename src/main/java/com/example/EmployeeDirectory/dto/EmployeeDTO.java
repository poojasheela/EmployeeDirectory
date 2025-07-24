package com.example.EmployeeDirectory.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    @NotBlank
    private String fullName;

    @NotBlank
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$",
            message = "Email must be in lowercase ")
    @Email(message = "Invalid email format")
    private String contactEmail;

    @NotBlank
    private String departmentName;
}
