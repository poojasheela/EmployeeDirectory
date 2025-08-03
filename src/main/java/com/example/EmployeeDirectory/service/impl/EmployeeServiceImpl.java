package com.example.EmployeeDirectory.service.impl;

import com.example.EmployeeDirectory.dto.EmployeeDTO;
import com.example.EmployeeDirectory.entity.Department;
import com.example.EmployeeDirectory.entity.Employee;
import com.example.EmployeeDirectory.exception.DataConflictException;
import com.example.EmployeeDirectory.exception.EmployeeNotFoundException;
import com.example.EmployeeDirectory.exception.InvalidRequestException;
import com.example.EmployeeDirectory.mapper.EmployeeMapper;
import com.example.EmployeeDirectory.repository.DepartmentRepository;
import com.example.EmployeeDirectory.repository.EmployeeRepository;
import com.example.EmployeeDirectory.response.ApiResponse;
import com.example.EmployeeDirectory.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper mapper;

    @Override
    public ApiResponse create(EmployeeDTO dto) {
        try {
            if (employeeRepository.findByContactEmail(dto.getEmail()).isPresent()) {
                throw new DataConflictException("Email already in use.");
            }

            Employee employee = new Employee();
            employee.setFullName(dto.getName());
            employee.setContactEmail(dto.getEmail().toLowerCase());
            employee.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
            employee.setRole(dto.getRole());

            if (dto.getDepartmentName() != null) {
                Department dept = departmentRepository.findByNameIgnoreCase(dto.getDepartmentName())
                        .orElseThrow(() -> new InvalidRequestException("Department not found"));
                employee.setDepartment(dept);
            }

            Employee saved = employeeRepository.save(employee);
            EmployeeDTO responseDTO = mapper.toDTO(saved);
            return ApiResponse.success("Employee created", responseDTO);

        } catch (DataConflictException | InvalidRequestException e) {
            log.warn("Validation error: ", e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during employee creation", e);
            throw new RuntimeException("Unexpected error occurred while creating employee");
        }
    }

    @Override
    public ApiResponse update(Integer id, EmployeeDTO dto) {
        try {
            Employee existing = employeeRepository.findById(id)
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

            existing.setFullName(dto.getName());
            existing.setContactEmail(dto.getEmail().toLowerCase());
            existing.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
            existing.setRole(dto.getRole());

            if (dto.getDepartmentName() != null) {
                Department dept = departmentRepository.findByNameIgnoreCase(dto.getDepartmentName())
                        .orElseThrow(() -> new InvalidRequestException("Department not found"));
                existing.setDepartment(dept);
            }

            Employee updated = employeeRepository.save(existing);
            EmployeeDTO responseDTO = mapper.toDTO(updated);
            return ApiResponse.success("Employee updated", responseDTO);

        } catch (EmployeeNotFoundException | InvalidRequestException e) {
            log.warn("Validation error: ", e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during employee update", e);
            throw new RuntimeException("Unexpected error occurred while updating employee");
        }
    }

    @Override
    public ApiResponse delete(Integer id) {
        try {
            Employee emp = employeeRepository.findById(id)
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
            employeeRepository.delete(emp);
            return ApiResponse.success("Employee deleted", null);
        } catch (EmployeeNotFoundException e) {
            log.warn("Delete failed: ", e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during delete", e);
            throw new RuntimeException("Unexpected error occurred while deleting employee");
        }
    }

    @Override
    public ApiResponse getById(Integer id) {
        try {
            Employee emp = employeeRepository.findById(id)
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
            return ApiResponse.success("Employee fetched", mapper.toDTO(emp));
        } catch (EmployeeNotFoundException e) {
            log.warn("Employee not found: ", e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during getById", e);
            throw new RuntimeException("Unexpected error occurred while fetching employee");
        }
    }

    @Override
    public ApiResponse getByEmailDomain(Optional<String> domain) {
        try {
            if (domain.isPresent()) {
                List<Employee> employees = employeeRepository.findByEmailDomain(domain.get());
                if (employees.isEmpty()) {
                    throw new EmployeeNotFoundException("No employees found with domain: " + domain.get());
                }
                return ApiResponse.success("Employees fetched by domain", mapper.toDTOList(employees));
            } else {
                List<Employee> employees = employeeRepository.findAll();
                return ApiResponse.success("All employees fetched", mapper.toDTOList(employees));
            }
        } catch (EmployeeNotFoundException e) {
            log.warn("No employees found for domain: ", e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during domain search", e);
            throw new RuntimeException("Unexpected error occurred while fetching by domain");
        }
    }

    @Override
    public ApiResponse getByName(Optional<String> name) {
        try {
            if (name.isPresent()) {
                Employee employee = employeeRepository.findByFullName(name.get())
                        .orElseThrow(() -> new EmployeeNotFoundException("No employee with this name"));
                return ApiResponse.success("Employee fetched", mapper.toDTO(employee));
            } else {
                List<Employee> employees = employeeRepository.findAll();
                return ApiResponse.success("All employees fetched", mapper.toDTOList(employees));
            }
        } catch (EmployeeNotFoundException e) {
            log.warn("Name search failed: ", e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during name search", e);
            throw new RuntimeException("Unexpected error occurred while fetching by name");
        }
    }

    @Override
    public ApiResponse getAll() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            return ApiResponse.success("All employees fetched", mapper.toDTOList(employees));
        } catch (Exception e) {
            log.error("Unexpected error during getAll", e);
            throw new RuntimeException("Unexpected error occurred while fetching all employees");
        }
    }
}
