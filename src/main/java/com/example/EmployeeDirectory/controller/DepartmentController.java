package com.example.EmployeeDirectory.controller;
import com.example.EmployeeDirectory.dto.DepartmentDTO;
import com.example.EmployeeDirectory.response.Response;
import com.example.EmployeeDirectory.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
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
        public Response createDepartment(@Valid @RequestBody DepartmentDTO dto) {
            log.info("Request received to create department by {}: ", dto);
            return departmentService.create(dto);
        }

        @PutMapping("/{id}")
        public Response updateDepartment(@PathVariable Integer id, @Valid @RequestBody DepartmentDTO dto) {
            log.info("Request received to update department with id {} : {}", id,  dto);
            return departmentService.update(id, dto);
        }

        @GetMapping
        public Response getAllDepartments() {
            log.info("Request received to fetch all departments");
            return departmentService.getAll();
        }

        @GetMapping("/{id}")
        public Response getDepartmentById(@PathVariable Integer id) {
            log.info("Request received to fetch department with ID {}", id);
            return departmentService.getById(id);
        }

        @GetMapping("/byName")
        public Response getByName(@RequestParam Optional<String> name) {
            log.info("Request received to fetch department by name: {}", name.orElse("N/A"));
            return departmentService.getByName(name);
        }

        @DeleteMapping("/{id}")
        public Response deleteDepartment(@PathVariable Integer id) {
            String performedBy = SecurityContextHolder.getContext().getAuthentication().getName();
            log.info("Request received to delete department {} ", id);
            return departmentService.delete(id);
        }
    }
