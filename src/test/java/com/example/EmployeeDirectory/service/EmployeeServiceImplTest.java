package com.example.EmployeeDirectory.service;


import com.example.EmployeeDirectory.dto.EmployeeDTO;
import com.example.EmployeeDirectory.entity.Department;
import com.example.EmployeeDirectory.entity.Employee;
import com.example.EmployeeDirectory.exception.DataConflictException;
import com.example.EmployeeDirectory.exception.EmployeeNotFoundException;
import com.example.EmployeeDirectory.exception.InvalidRequestException;
import com.example.EmployeeDirectory.mapper.EmployeeMapper;
import com.example.EmployeeDirectory.repository.DepartmentRepository;
import com.example.EmployeeDirectory.repository.EmployeeRepository;
import com.example.EmployeeDirectory.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeMapper mapper;

    private Employee employee;
    private EmployeeDTO employeeDTO;
    private Department department;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        department = new Department();
        department.setId(1L);
        department.setName("HR");

        employee = new Employee();
        employee.setId(1);
        employee.setName("Mike");
        employee.setEmail("mike@example.com");
        employee.setDepartment(department);

        employeeDTO = new EmployeeDTO();
        employeeDTO.setFullName("Mike");
        employeeDTO.setContactEmail("mike@example.com");
        employeeDTO.setDepartmentName("HR");
    }

    @Test
    void testCreateEmployee_Success() {
        when(employeeRepository.existsByEmail("mike@example.com")).thenReturn(false);
        when(departmentRepository.findByName("HR")).thenReturn(Optional.of(department));
        when(mapper.toEntity(employeeDTO)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(mapper.toDTO(employee)).thenReturn(employeeDTO);

        EmployeeDTO saved = employeeService.createEmployee(employeeDTO);
        assertNotNull(saved);
        assertEquals("Mike", saved.getFullName());
    }

    @Test
    void testCreateEmployee_EmailConflict() {
        when(employeeRepository.existsByEmail("mike@example.com")).thenReturn(true);
        assertThrows(DataConflictException.class, () -> employeeService.createEmployee(employeeDTO));
    }

    @Test
    void testCreateEmployee_DepartmentNotFound() {
        when(employeeRepository.existsByEmail("mike@example.com")).thenReturn(false);
        when(departmentRepository.findByName("HR")).thenReturn(Optional.empty());
        assertThrows(InvalidRequestException.class, () -> employeeService.createEmployee(employeeDTO));
    }

    @Test
    void testFetchAllEmployees_Success() {
        List<Employee> list = List.of(employee);
        when(employeeRepository.findAll()).thenReturn(list);
        when(mapper.toDTOList(list)).thenReturn(List.of(employeeDTO));

        List<EmployeeDTO> result = employeeService.fetchAllEmployees();
        assertEquals(1, result.size());
    }

    @Test
    void testFetchEmployeesByName_Found() {
        when(employeeRepository.findEmployeesByName("Mike")).thenReturn(List.of(employee));
        when(mapper.toDTOList(List.of(employee))).thenReturn(List.of(employeeDTO));

        List<EmployeeDTO> result = employeeService.fetchEmployeesByName("Mike");
        assertEquals(1, result.size());
    }

    @Test
    void testFetchEmployeesByName_NotFound() {
        when(employeeRepository.findEmployeesByName("Unknown")).thenReturn(Collections.emptyList());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.fetchEmployeesByName("Unknown"));
    }

    @Test
    void testFetchEmployeesByEmailDomain_Found() {
        when(employeeRepository.findEmployeesByEmailDomain("example.com")).thenReturn(List.of(employee));
        when(mapper.toDTOList(List.of(employee))).thenReturn(List.of(employeeDTO));

        List<EmployeeDTO> result = employeeService.fetchEmployeesByEmailDomain("example.com");
        assertEquals(1, result.size());
    }

    @Test
    void testFetchEmployeesByEmailDomain_NotFound() {
        when(employeeRepository.findEmployeesByEmailDomain("missing.com")).thenReturn(Collections.emptyList());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.fetchEmployeesByEmailDomain("missing.com"));
    }

    @Test
    void testUpdateEmployeeById_Success() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(employeeRepository.existsByEmail("mike@example.com")).thenReturn(false);
        when(departmentRepository.findByName("HR")).thenReturn(Optional.of(department));
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(mapper.toDTO(employee)).thenReturn(employeeDTO);

        EmployeeDTO updated = employeeService.updateEmployeeById(1, employeeDTO);
        assertEquals("Mike", updated.getFullName());
    }

    @Test
    void testUpdateEmployeeById_NotFound() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.updateEmployeeById(1, employeeDTO));
    }

    @Test
    void testDeleteEmployeeById_Success() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(employee);

        assertDoesNotThrow(() -> employeeService.deleteEmployeeById(1));
    }

    @Test
    void testDeleteEmployeeById_NotFound() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployeeById(1));
    }
}
