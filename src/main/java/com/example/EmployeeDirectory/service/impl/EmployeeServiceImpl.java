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
import com.example.EmployeeDirectory.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service

public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
     DepartmentRepository departmentRepository;
    @Autowired
     EmployeeMapper mapper;

    @Override
    public EmployeeDTO createEmployee(@Valid EmployeeDTO dto) {
        try {
            if (employeeRepository.existsByEmail(dto.getContactEmail())) {
                throw new DataConflictException("Email already exists");
            }

            Department department = departmentRepository.findByName(dto.getDepartmentName())
                    .orElseThrow(() -> new InvalidRequestException("Department not found"));

            Employee employee = mapper.toEntity(dto);
            employee.setDepartment(department);
            Employee saved = employeeRepository.save(employee);

            log.info("Employee created with ID: {}", saved.getId());
            return mapper.toDTO(saved);
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
    public List<EmployeeDTO> fetchAllEmployees() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            return mapper.toDTOList(employees);
        } catch (Exception e) {
            log.error("Error while fetching all employees", e);
            throw new RuntimeException("Unexpected error occurred while fetching all employees");
        }
    }

    @Override
    public List<EmployeeDTO> fetchEmployeesByName(String name) {
        try {
            List<Employee> list = employeeRepository.findEmployeesByName(name);
            if (list.isEmpty()) {
                throw new EmployeeNotFoundException("No employees found with name: " + name);
            }
            return mapper.toDTOList(list);
        } catch (EmployeeNotFoundException e) {
            log.warn("Employee not found: ", e);
            throw e;
        } catch (Exception e) {
            log.error("Error while fetching employees by name", e);
            throw new RuntimeException("Unexpected error occurred while fetching by name");
        }
    }

    @Override
    public List<EmployeeDTO> fetchEmployeesByEmailDomain(String domain) {
        try {
            List<Employee> list = employeeRepository.findEmployeesByEmailDomain(domain.toLowerCase());
            if (list.isEmpty()) {
                throw new EmployeeNotFoundException("No employees found with domain: " + domain);
            }
            return mapper.toDTOList(list);
        } catch (EmployeeNotFoundException e) {
            log.warn("Employee not found", e);
            throw e;
        } catch (Exception e) {
            log.error("Error while fetching employees by email domain", e);
            throw new RuntimeException("Unexpected error occurred while fetching by domain");
        }
    }

    @Override
    public EmployeeDTO updateEmployeeById(int id, @Valid EmployeeDTO dto) {
        try {
            Employee existing = employeeRepository.findById(id)
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

            if (!existing.getEmail().equals(dto.getContactEmail()) &&
                    employeeRepository.existsByEmail(dto.getContactEmail())) {
                throw new DataConflictException("Email already exists for another employee");
            }

            Department dept = departmentRepository.findByName(dto.getDepartmentName())
                    .orElseThrow(() -> new InvalidRequestException("Department not found"));

            existing.setName(dto.getFullName());
            existing.setEmail(dto.getContactEmail());
            existing.setDepartment(dept);

            Employee updated = employeeRepository.save(existing);
            log.info("Employee updated with ID: {}", updated.getId());

            return mapper.toDTO(updated);
        } catch (EmployeeNotFoundException  e) {
            log.warn("Employee with this id not found", e);
            throw e;
        } catch (InvalidRequestException e){
            log.warn("Invalid Request :", e);
            throw e;
        } catch(DataConflictException e) {
            log.warn("Conflicts when updating employee", e);
            throw e;
        }
        catch (Exception e) {
            log.error("Error while updating employee", e);
            throw new RuntimeException("Unexpected error occurred while creating employee");
        }
    }

    @Override
    public void deleteEmployeeById(int id) {
        try {
            Employee emp = employeeRepository.findById(id)
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
            employeeRepository.delete(emp);
            log.info("Deleted employee with ID: {}", id);
        } catch (EmployeeNotFoundException e) {
            log.warn("Delete failed: ", e);
            throw e;
        } catch (Exception e) {
            log.error("Error while deleting employee", e);
            throw new RuntimeException("Unexpected error occurred while deleting employee");
        }
    }
}


