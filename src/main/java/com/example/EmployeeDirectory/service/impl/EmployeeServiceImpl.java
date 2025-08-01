package com.example.EmployeeDirectory.service.impl;

import com.example.EmployeeDirectory.dto.EmployeeDTO;
import com.example.EmployeeDirectory.entity.Department;
import com.example.EmployeeDirectory.entity.Employee;
import com.example.EmployeeDirectory.exception.DataConflictException;
import com.example.EmployeeDirectory.exception.EmployeeNotFoundException;
import com.example.EmployeeDirectory.exception.InvalidRequestException;
import com.example.EmployeeDirectory.repository.DepartmentRepository;
import com.example.EmployeeDirectory.repository.EmployeeRepository;
import com.example.EmployeeDirectory.response.ApiResponse;
import com.example.EmployeeDirectory.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional(rollbackFor = InvalidRequestException.class)
    @Override
    public ApiResponse create(@Valid EmployeeDTO dto) {
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

            return ApiResponse.success("Employee created", employeeRepository.save(employee));
        } catch (InvalidRequestException e){
            log.warn("Invalid Request :", e);
            throw e;
        } catch(DataConflictException e) {
            log.warn("Conflicts when creating employee", e);
            throw e;
        }
        catch (Exception e) {
            log.error("Error while creating employee", e);
            throw new RuntimeException("Unexpected error occurred while creating employee");
        }
    }

    @Override
    public ApiResponse update(Integer id, @Valid EmployeeDTO dto) {
       try{ Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        emp.setFullName(dto.getName());
        emp.setContactEmail(dto.getEmail().toLowerCase());
        emp.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        emp.setRole(dto.getRole());

        if (dto.getDepartmentName() != null) {
            Department dept = departmentRepository.findByNameIgnoreCase(dto.getDepartmentName())
                    .orElseThrow(() -> new InvalidRequestException("Department not found"));
            emp.setDepartment(dept);
        }

        return ApiResponse.success("Employee updated", employeeRepository.save(emp));

        } catch (EmployeeNotFoundException  e) {
            log.warn("Employee with this id not found", e);
            throw e;
        } catch (InvalidRequestException e) {
           log.warn("Invalid Request :", e);
           throw e;
       }
        catch (Exception e) {
            log.error("Error while updating employee", e);
            throw new RuntimeException("Unexpected error occurred while creating employee");
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
            log.error("Error while deleting employee", e);
            throw new RuntimeException("Unexpected error occurred while deleting employee");
        }
    }

    @Override
    public ApiResponse getById(Integer id) {
        try {
            Employee emp = employeeRepository.findById(id)
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
            return ApiResponse.success("Employee fetched", emp);
        } catch (EmployeeNotFoundException e) {
            log.warn("Employee not found", e);
            throw e;
        } catch (Exception e) {
            log.error("Error while fetching employees by email domain", e);
            throw new RuntimeException("Unexpected error occurred while fetching by domain");
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
               return ApiResponse.success("Employees fetched by domain", employees);
           } else {
               List<Employee> allEmployees = employeeRepository.findAll();
               return ApiResponse.success("All employees fetched", allEmployees);
           }
       }catch (EmployeeNotFoundException e) {
        log.warn("Employee not found", e);
        throw e;
    } catch (Exception e) {
        log.error("Error while fetching employees by email domain", e);
        throw new RuntimeException("Unexpected error occurred while fetching by domain");
    }
    }


    @Override
    public ApiResponse getByName(Optional<String> name) {
        try {
            if (name.isPresent()) {
                Employee employee = employeeRepository.findByFullName(name.get())
                        .orElseThrow(() -> new EmployeeNotFoundException("No employee with this name"));
                return ApiResponse.success("Employee fetched", employee);
            } else {
                List<Employee> allEmployees = employeeRepository.findAll();
                return ApiResponse.success("All employees fetched", allEmployees);
            }

        } catch (EmployeeNotFoundException e) {
            log.warn("Employee not found", e);
            throw e;
        } catch (Exception e) {
            log.error("Error while fetching employees by email domain", e);
            throw new RuntimeException("Unexpected error occurred while fetching by domain");
        }
    }

    @Override
    public ApiResponse getAll() {
        try {
            return ApiResponse.success("All employees fetched", employeeRepository.findAll());
        } catch (Exception e) {
            log.error("Error while fetching all employees", e);
            throw new RuntimeException("Unexpected error occurred while fetching all employees");
        }
    }
}
