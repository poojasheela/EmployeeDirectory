package com.example.EmployeeDirectory.controller;


import com.example.EmployeeDirectory.dto.EmployeeDTO;
import com.example.EmployeeDirectory.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.EmployeeDirectory.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/add")
    public Response createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.create(employeeDTO);
    }

    @PutMapping("/{id}")
    public Response updateEmployee(@PathVariable Integer id, @Valid @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.update(id, employeeDTO);
    }

    @DeleteMapping("/{id}")
    public Response deleteEmployee(@PathVariable Integer id) {
        return employeeService.delete(id);
    }

    @GetMapping("/{id}")
    public Response getById(@PathVariable Integer id) {
        return employeeService.getById(id);
    }

    @GetMapping("/filter-by-name")
    public Response getByName(@RequestParam Optional<String> name) {
        return employeeService.getByName(name);
    }

    @GetMapping("/filter-by-email")
    public Response getByEmailDomain(@RequestParam Optional<String> domain) {
        return employeeService.getByEmailDomain(domain);
    }

    @GetMapping
    public Response getAllEmployees() {
        return employeeService.getAll();
    }

    @GetMapping("/filter-by-page")
    public Response getAllEmployees(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size) {
       return employeeService.getAllPaginatedEmployees(page, size);
    }


}
