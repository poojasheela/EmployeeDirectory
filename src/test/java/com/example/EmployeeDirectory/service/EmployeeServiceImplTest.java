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
import com.example.EmployeeDirectory.response.Response;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock private EmployeeRepository employeeRepository;
    @Mock private DepartmentRepository departmentRepository;
    @Mock private EmployeeMapper mapper;

    @InjectMocks private EmployeeServiceImpl employeeService;

    private Employee employee;
    private Department department;
    private EmployeeDTO dto;

    @BeforeEach
    void init() {
        department = new Department(9, "HR", LocalDateTime.now(), LocalDateTime.now());

        employee = new Employee(6, "Mike", "mike@qrs.com", "encoded123", "USER", department,
                LocalDateTime.now(), LocalDateTime.now());

        dto = new EmployeeDTO(null, "Mike", "mike@qrs.com", "rawpass", "USER", "HR", null, null);
    }

    @Test
    void testCreate_Success() {
        when(employeeRepository.findByContactEmail(any())).thenReturn(Optional.empty());
        when(departmentRepository.findByNameIgnoreCase("HR")).thenReturn(Optional.of(department));
        when(employeeRepository.save(any())).thenReturn(employee);
        when(mapper.toDTO(any())).thenReturn(dto);

        Response res = employeeService.create(dto);

        assertEquals("Employee created", res.getMessage());
        verify(employeeRepository).save(any());
    }

    @Test
    void testCreate_ThrowConflict() {
        when(employeeRepository.findByContactEmail(any())).thenReturn(Optional.of(employee));
        assertThrows(DataConflictException.class, () -> employeeService.create(dto));
    }


    @Test
    void testUpdate_Success() {
        when(employeeRepository.findById(6)).thenReturn(Optional.of(employee));
        when(departmentRepository.findByNameIgnoreCase("HR")).thenReturn(Optional.of(department));
        when(employeeRepository.save(any())).thenReturn(employee);
        when(mapper.toDTO(any())).thenReturn(dto);

        Response res = employeeService.update(6, dto);

        assertEquals("Employee updated", res.getMessage());
    }

    @Test
    void testUpdate_ThrowIfNotFound() {
        when(employeeRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.update(99, dto));
    }

    @Test
    void testUpdate_ThrowIfDeptMissing() {
        when(employeeRepository.findById(6)).thenReturn(Optional.of(employee));
        when(departmentRepository.findByNameIgnoreCase("HR")).thenReturn(Optional.empty());
        assertThrows(InvalidRequestException.class, () -> employeeService.update(6, dto));
    }

    @Test
    void testDelete_Success() {
        when(employeeRepository.findById(6)).thenReturn(Optional.of(employee));
        Response res = employeeService.delete(6);
        assertEquals("Employee deleted", res.getMessage());
        verify(employeeRepository).delete(employee);
    }

    @Test
    void testDelete_ThrowIfNotFound() {
        when(employeeRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.delete(123));
    }

    @Test
    void testGetById_Success() {
        when(employeeRepository.findById(6)).thenReturn(Optional.of(employee));
        when(mapper.toDTO(any())).thenReturn(dto);
        Response res = employeeService.getById(6);
        assertEquals("Employee fetched", res.getMessage());
    }

    @Test
    void testGetById_ThrowIfNotFound() {
        when(employeeRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getById(101));
    }

    @Test
    void testGetByEmailDomain_Success() {
        when(employeeRepository.findByEmailDomain("qrs.com")).thenReturn(List.of(employee));
        when(mapper.toDTOList(any())).thenReturn(List.of(dto));
        Response res = employeeService.getByEmailDomain(Optional.of("qrs.com"));
        assertEquals("Employees fetched by domain", res.getMessage());
    }

    @Test
    void testGetByEmailDomain_IfDomainEmpty() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        when(mapper.toDTOList(any())).thenReturn(List.of(dto));
        Response res = employeeService.getByEmailDomain(Optional.empty());
        assertEquals("All employees fetched", res.getMessage());
    }

    @Test
    void testGetByEmailDomain_shouldThrowIfNotFound() {
        when(employeeRepository.findByEmailDomain("xyz.com")).thenReturn(List.of());
        assertThrows(EmployeeNotFoundException.class, () ->
                employeeService.getByEmailDomain(Optional.of("xyz.com")));
    }

    @Test
    void testGetByName_shouldSucceed() {
        when(employeeRepository.findByFullName("Mike")).thenReturn(Optional.of(employee));
        when(mapper.toDTO(any())).thenReturn(dto);
        Response res = employeeService.getByName(Optional.of("Mike"));
        assertEquals("Employee fetched", res.getMessage());
    }

    @Test
    void testGetByName_shouldReturnAllIfBlank() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        when(mapper.toDTOList(any())).thenReturn(List.of(dto));
        Response res = employeeService.getByName(Optional.of(" "));
        assertEquals("All employees fetched", res.getMessage());
    }

    @Test
    void testGetByName_IfNotFound() {
        when(employeeRepository.findByFullName("Unknown")).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () ->
                employeeService.getByName(Optional.of("Unknown")));
    }

    @Test
    void testGetAll_Success() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        when(mapper.toDTOList(any())).thenReturn(List.of(dto));
        Response res = employeeService.getAll();
        assertEquals("All employees fetched", res.getMessage());
    }

    @Test
    void testGetAllPaginatedEmployees_Success() {
        Page<Employee> page = new PageImpl<>(List.of(employee));
        when(employeeRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(mapper.toDTO(any())).thenReturn(dto);
        Response res = employeeService.getAllPaginatedEmployees(Optional.of(0), Optional.of(10));
        assertEquals(200, res.getStatus());
    }

    @Test
    void testGetAllPaginatedEmployees_withoutPagination() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        when(mapper.toDTO(any())).thenReturn(dto);
        Response res = employeeService.getAllPaginatedEmployees(Optional.empty(), Optional.empty());
        assertEquals(200, res.getStatus());
    }
}
