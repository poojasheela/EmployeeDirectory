package com.example.EmployeeDirectory.service;

import com.example.EmployeeDirectory.dto.EmployeeDTO;
import com.example.EmployeeDirectory.entity.Department;
import com.example.EmployeeDirectory.entity.Employee;
import com.example.EmployeeDirectory.exception.EmployeeNotFoundException;
import com.example.EmployeeDirectory.mapper.EmployeeMapper;
import com.example.EmployeeDirectory.repository.DepartmentRepository;
import com.example.EmployeeDirectory.repository.EmployeeRepository;
import com.example.EmployeeDirectory.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeMapper mapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Department department;
    private Employee employee;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee(1, "Mike", "mike@example.com", department);
        employeeDTO = new EmployeeDTO("Mike", "mike@example.com", "HR");
    }

    @Test
    public void testCreateEmployee_Success() {
        when(departmentRepository.findByName("HR")).thenReturn(Optional.of(department));
        when(mapper.toEntity(employeeDTO)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(mapper.toDTO(employee)).thenReturn(employeeDTO);

        EmployeeDTO result = employeeService.createEmployee(employeeDTO);

        assertEquals("Mike", result.getFullName());
        assertEquals("mike@example.com", result.getContactEmail());
        assertEquals("HR", result.getDepartmentName());
    }

    @Test
    public void testCreateEmployee_DepartmentNotFound() {
        when(departmentRepository.findByName("HR")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            employeeService.createEmployee(employeeDTO);
        });

        assertTrue(exception.getMessage().contains("Department not found"));
    }

    @Test
    public void testGetEmployeeById_Success() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(mapper.toDTO(employee)).thenReturn(employeeDTO);

        EmployeeDTO result = employeeService.get;

        assertEquals("Mike", result.getFullName());
    }

    @Test
    public void testGetEmployeeById_NotFound() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(1));
    }

    @Test
    public void testDeleteEmployee_Success() {
        when(employeeRepository.existsById(1)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(1);

        String message = employeeService.deleteEmployee(1);

        assertEquals("Employee deleted with ID: 1", message);
    }

    @Test
    public void testDeleteEmployee_NotFound() {
        when(employeeRepository.existsById(1)).thenReturn(false);

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployee(1));
    }
}
