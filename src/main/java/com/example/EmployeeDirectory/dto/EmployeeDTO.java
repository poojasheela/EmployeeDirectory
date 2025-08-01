package com.example.EmployeeDirectory.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    @NotBlank
    private String name;

    @NotBlank
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$",
            message = "Email must be in lowercase")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String role;

    private String departmentName;
}
