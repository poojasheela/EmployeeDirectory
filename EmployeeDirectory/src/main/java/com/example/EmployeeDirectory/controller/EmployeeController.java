package com.example.EmployeeDirectory.controller;


import com.example.EmployeeDirectory.dto.EmployeeDTO;
import com.example.EmployeeDirectory.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
@Slf4j
public class EmployeeController {

    private final EmployeeService service;

    @PostMapping("/add")
    public ResponseEntity<String> createEmployee(@Valid @RequestBody EmployeeDTO dto) {
        EmployeeDTO created = service.createEmployee(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Employee created with name: " + created.getFullName());
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<EmployeeDTO>> fetchAllEmployees() {
        List<EmployeeDTO> employees = service.fetchAllEmployees();
        log.info("Fetched {} employees", employees.size());
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/email/findByDomain/{domain}")
    public ResponseEntity<List<EmployeeDTO>> fetchEmployeesByEmailDomain(@PathVariable String domain) {
        return ResponseEntity.ok(service.fetchEmployeesByEmailDomain(domain));
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<List<EmployeeDTO>> fetchEmployeesByName(@PathVariable String name) {
        return ResponseEntity.ok(service.fetchEmployeesByName(name));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<EmployeeDTO>> fetchPaginatedEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir) {
        return ResponseEntity.ok(service.fetchPaginatedEmployees(page, size, sortBy, sortDir));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable int id, @Valid @RequestBody EmployeeDTO dto) {
        EmployeeDTO updated = service.updateEmployeeById(id, dto);
        return ResponseEntity.ok("Updated details of employee with name " + updated.getFullName());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        service.deleteEmployeeById(id);
        return ResponseEntity.ok("Employee deleted with ID: " + id);
    }
}
