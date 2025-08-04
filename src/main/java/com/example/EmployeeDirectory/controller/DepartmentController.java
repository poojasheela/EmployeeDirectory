package com.example.EmployeeDirectory.controller;


import com.example.EmployeeDirectory.dto.DepartmentDTO;
import com.example.EmployeeDirectory.response.ApiResponse;
import com.example.EmployeeDirectory.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/department")
public class DepartmentController {


    private final DepartmentService departmentService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse createDepartment(@Valid @RequestBody DepartmentDTO dto) {
        return departmentService.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse updateDepartment(@PathVariable Integer id, @Valid @RequestBody DepartmentDTO dto) {
        return departmentService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse deleteDepartment(@PathVariable Integer id) {
        return departmentService.delete(id);
    }


    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ApiResponse getAllDepartments() {
        return departmentService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ApiResponse getDepartmentById(@PathVariable Integer id) {
        return departmentService.getById(id);
    }

    @GetMapping("/byName")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ApiResponse getByName(@RequestParam Optional<String> name) {
        return departmentService.getByName(name);
    }
}