package com.example.EmployeeDirectory.controller;


import com.example.EmployeeDirectory.dto.EmployeeDTO;
import com.example.EmployeeDirectory.response.ApiResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.EmployeeDirectory.service.EmployeeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
@RestController
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

        @GetMapping("/findByName")
        @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
        public ApiResponse getByName(@RequestParam Optional<String> name) {
            return employeeService.getByName(name);
        }

        @GetMapping("/findByEmail")
        @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
        public ApiResponse getByEmailDomain(@RequestParam Optional<String> domain) {
            return employeeService.getByEmailDomain(domain);
        }

        @GetMapping
        @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
        public ApiResponse getAllEmployees() {
            return employeeService.getAll();
        }
    }
