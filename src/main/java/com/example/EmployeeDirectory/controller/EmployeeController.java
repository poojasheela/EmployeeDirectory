package com.example.EmployeeDirectory.controller;

import com.example.EmployeeDirectory.dto.EmployeeDTO;
import com.example.EmployeeDirectory.response.ApiResponse;
import com.example.EmployeeDirectory.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Slf4j
@RestController
@EnableWebSecurity
@EnableMethodSecurity
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.create(employeeDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse updateEmployee(@PathVariable Integer id, @Valid @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.update(id, employeeDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse deleteEmployee(@PathVariable Integer id) {
        return employeeService.delete(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ApiResponse getById(@PathVariable Integer id) {
        return employeeService.getById(id);
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ApiResponse getAllEmployees() {
        return employeeService.getAll();
    }


    @GetMapping("/email")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ApiResponse getByEmailDomain(@RequestParam Optional<String> domain) {
        return employeeService.getByEmailDomain(domain);
    }


    @GetMapping("/name")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ApiResponse getByName(@RequestParam Optional<String> name) {
        return employeeService.getByName(name);
    }
}

