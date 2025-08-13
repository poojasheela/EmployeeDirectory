package com.example.EmployeeDirectory.controller;
import com.example.EmployeeDirectory.dto.EmployeeDTO;
import com.example.EmployeeDirectory.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
@Slf4j
@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
@Tag(name = "Employee API", description = "Endpoints for managing employees")
public class EmployeeController {

        private final EmployeeService employeeService;

        @PostMapping("/add")
        @Operation(summary = "Create a new employee", description = "Adds a new employee to the database")
        public Response createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
            log.info("Employee creation : {}",  employeeDTO);
            return employeeService.create(employeeDTO);
        }

        @PutMapping("/{id}")
        @Operation(summary = "Update an employee", description = "Updates employee details by ID")
        public Response updateEmployee(@PathVariable Integer id, @Valid @RequestBody EmployeeDTO employeeDTO) {
            log.info("Employee update requested for ID {}: {}",  id, employeeDTO);
            return employeeService.update(id, employeeDTO);
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Delete an employee", description = "Deletes employee by ID")
        public Response deleteEmployee(@PathVariable Integer id) {
            log.info("Employee delete requested for ID {}",  id);
            return employeeService.delete(id);
        }

        @GetMapping("/{id}")
        @Operation(summary = "Get employee by ID", description = "Fetches employee details by ID")
        public Response getById(@PathVariable Integer id) {
            log.info("Fetching employee with ID {}", id);
            return employeeService.getById(id);
        }

        @GetMapping("/filter-by-name")
        @Operation(summary = "Filter by name", description = "Fetch employees matching the given name")
        public Response getByName(@RequestParam Optional<String> name) {
            log.info("Fetching employee(s) by name: {}", name.orElse("N/A"));
            return employeeService.getByName(name);
        }

        @GetMapping("/filter-by-email")
        @Operation(summary = "Filter by email domain", description = "Fetch employees having email ending with the given domain")
        public Response getByEmailDomain(@RequestParam Optional<String> domain) {
            log.info("Fetching employee(s) by email domain: {}", domain.orElse("N/A"));
            return employeeService.getByEmailDomain(domain);
        }

        @GetMapping
        @Operation(summary = "Get all employees", description = "Fetch all employees")
        public Response getAllEmployees() {
            log.info("Fetching all employees");
            return employeeService.getAll();
        }

        @GetMapping("/filter-by-page")
        @Operation(summary = "Paginated employee list", description = "Fetch employees with pagination")
        public Response getAllEmployees(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size) {
            log.info("Fetching employees with pagination. Page: {}, Size: {}", page.orElse(null), size.orElse(null));
            return employeeService.getAllPaginatedEmployees(page, size);
        }
    }
