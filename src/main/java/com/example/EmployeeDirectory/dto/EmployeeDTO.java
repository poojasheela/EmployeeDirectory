package com.example.EmployeeDirectory.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    @Email(message = "Invalid email format")
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$", message = "Email must be lowercase and valid format")
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String role;

    private String departmentName;
}
