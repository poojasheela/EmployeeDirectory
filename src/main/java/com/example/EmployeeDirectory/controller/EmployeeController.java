package com.example.EmployeeDirectory.controller;


import com.example.EmployeeDirectory.dto.EmployeeDTO;
import com.example.EmployeeDirectory.response.ApiResponse;
import com.example.EmployeeDirectory.service.EmployeeService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add")
    public ApiResponse createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.create(employeeDTO);
    }

    @PutMapping("/{id}")
    public ApiResponse updateEmployee(@PathVariable Integer id, @Valid @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.update(id, employeeDTO);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteEmployee(@PathVariable Integer id) {
        return employeeService.delete(id);
    }

    @GetMapping("/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return employeeService.getById(id);
    }

    @GetMapping("/findByName")
    public ApiResponse getByName(@RequestParam Optional<String> name) {
        return employeeService.getByName(name);
    }

    @GetMapping("/findByEmail")
    public ApiResponse getByEmailDomain(@RequestParam Optional<String> domain) {
        return employeeService.getByEmailDomain(domain);
    }

    @GetMapping
    public ApiResponse getAllEmployees() {
        return employeeService.getAll();
    }
}
