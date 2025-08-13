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
import com.example.EmployeeDirectory.response.Response;
import com.example.EmployeeDirectory.service.EmployeeService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper mapper;
    private final EntityManager entityManager;



    @Transactional(rollbackFor = {InvalidRequestException.class, DataConflictException.class})
    @Override
    public Response create(EmployeeDTO dto) {

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
        return Response.success("Employee created", mapper.toDTO(saved));
    }

    @Transactional(rollbackFor = {EmployeeNotFoundException.class, InvalidRequestException.class})
    @Override
    public Response update(Integer id, EmployeeDTO dto) {

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
        return Response.success("Employee updated", mapper.toDTO(updated));
    }

    @Transactional(rollbackFor = EmployeeNotFoundException.class)
    @Override
    public Response delete(Integer id) {

        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        employeeRepository.delete(emp);
        return Response.success("Employee deleted", null);
    }

    @Transactional(rollbackFor = EmployeeNotFoundException.class)
    @Override
    public Response getById(Integer id) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        return Response.success("Employee fetched", mapper.toDTO(emp));
    }

    @Transactional(rollbackFor = EmployeeNotFoundException.class)
    @Override
    public Response getByEmailDomain(Optional<String> domain) {
        if (domain.isPresent()) {
            List<Employee> employees = employeeRepository.findByEmailDomain(domain.get());
            if (employees.isEmpty()) {
                throw new EmployeeNotFoundException("No employees found with domain: " + domain.get());
            }
            return Response.success("Employees fetched by domain", mapper.toDTOList(employees));
        } else {
            List<Employee> employees = employeeRepository.findAll();
            return Response.success("All employees fetched", mapper.toDTOList(employees));
        }
    }

    @Transactional(rollbackFor = EmployeeNotFoundException.class)
    @Override
    public Response getByName(Optional<String> name) {
        if (name.isPresent() && !name.get().isBlank()) {
            Employee employee = employeeRepository.findByFullName(name.get())
                    .orElseThrow(() -> new EmployeeNotFoundException("No employee with this name"));
            return Response.success("Employee fetched", mapper.toDTO(employee));
        } else {
            List<Employee> employees = employeeRepository.findAll();
            return Response.success("All employees fetched", mapper.toDTOList(employees));
        }
    }

    @Override
    public Response getAll() {
        List<Employee> employees = employeeRepository.findAll();
        return Response.success("All employees fetched", mapper.toDTOList(employees));
    }

    @Override
    public Response getAllPaginatedEmployees(Optional<Integer> pageOpt, Optional<Integer> sizeOpt) {
        List<Employee> employees;
        long totalItems;
        int totalPages;
        int currentPage;

        if (pageOpt.isPresent() && sizeOpt.isPresent()) {
            int page = pageOpt.get();
            int size = sizeOpt.get();

            Pageable pageable = PageRequest.of(page, size);
            Page<Employee> pageResult = employeeRepository.findAll(pageable);

            employees = pageResult.getContent();
            totalItems = pageResult.getTotalElements();
            totalPages = pageResult.getTotalPages();
            currentPage = page + 1;
        } else {
            employees = employeeRepository.findAll();
            totalItems = employees.size();
            totalPages = 1;
            currentPage = 1;
        }

        List<EmployeeDTO> employeeDTOs = employees.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());

        Map<String, Object> data = new HashMap<>();
        data.put("employees", employeeDTOs);
        data.put("totalItems", totalItems);
        data.put("totalPages", totalPages);
        data.put("currentPage", currentPage);

        return new Response(LocalDateTime.now(), 200, "Employee list fetched", data);
    }


}
