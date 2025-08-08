package com.example.EmployeeDirectory.service;

import com.example.EmployeeDirectory.dto.DepartmentDTO;
import com.example.EmployeeDirectory.entity.Department;
import com.example.EmployeeDirectory.exception.DataConflictException;
import com.example.EmployeeDirectory.exception.DepartmentNotFoundException;
import com.example.EmployeeDirectory.exception.EmployeeNotFoundException;
import com.example.EmployeeDirectory.repository.DepartmentRepository;
import com.example.EmployeeDirectory.response.Response;
import com.example.EmployeeDirectory.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

    @Mock private DepartmentRepository departmentRepository;
    @InjectMocks private DepartmentServiceImpl departmentService;

    private Department department;
    private DepartmentDTO dto;

    @BeforeEach
    void setUp() {
        department = new Department(9, "HR", LocalDateTime.now(), LocalDateTime.now());
        dto = new DepartmentDTO("HR");
    }

    @Test
    void testCreate_Success() {
        when(departmentRepository.existsByName("HR")).thenReturn(false);
        when(departmentRepository.save(any())).thenReturn(department);

        Response res = departmentService.create(dto);

        assertEquals("Department created with name: HR", res.getMessage());
        verify(departmentRepository).save(any());
    }

    @Test
    void testCreate_throwConflict() {
        when(departmentRepository.existsByName("HR")).thenReturn(true);
        assertThrows(DataConflictException.class, () -> departmentService.create(dto));
    }

    @Test
    void testUpdate_Success() {
        when(departmentRepository.findById(9)).thenReturn(Optional.of(department));
        when(departmentRepository.save(any())).thenReturn(department);

        Response res = departmentService.update(9, dto);
        assertEquals("Department updated with name: HR", res.getMessage());
    }


    @Test
    void testUpdate_IfEmployeeNotFound() {
        when(departmentRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(DepartmentNotFoundException.class, () -> departmentService.update(99, dto));
    }

    @Test
    void testUpdate_IfNameAlreadyExists() {
        Department existing = new Department(10, "Finance", LocalDateTime.now(), LocalDateTime.now());

        when(departmentRepository.findById(9)).thenReturn(Optional.of(department));
        when(departmentRepository.existsByName("Finance")).thenReturn(true);

        DepartmentDTO newDTO = new DepartmentDTO("Finance");
        assertThrows(DataConflictException.class, () -> departmentService.update(9, newDTO));
    }


    @Test
    void testDelete_Success() {
        when(departmentRepository.findById(9)).thenReturn(Optional.of(department));
        Response res = departmentService.delete(9);
        assertEquals("Department deleted with ID: 9", res.getMessage());
        verify(departmentRepository).delete(department);
    }

    @Test
    void testDelete_IfEmployeeNotFound() {
        when(departmentRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> departmentService.delete(100));
    }

    @Test
    void testGetById_Success() {
        when(departmentRepository.findById(9)).thenReturn(Optional.of(department));
        Response res = departmentService.getById(9);
        assertEquals("Department fetched with ID: 9", res.getMessage());
    }

    @Test
    void testGetById_IfEmployeeNotFound() {
        when(departmentRepository.findById(101)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> departmentService.getById(101));
    }


    @Test
    void testGetAll_success() {
        when(departmentRepository.findAll()).thenReturn(List.of(department));
        Response res = departmentService.getAll();
        assertEquals("All departments fetched", res.getMessage());
    }

    @Test
    void testGetByName_success() {
        when(departmentRepository.findByNameIgnoreCase("HR")).thenReturn(Optional.of(department));
        Response res = departmentService.getByName(Optional.of("HR"));
        assertEquals("Department found by name", res.getMessage());
    }


    @Test
    void testGetByName_IfEmpty() {
        when(departmentRepository.findAll()).thenReturn(List.of(department));
        Response res = departmentService.getByName(Optional.empty());
        assertEquals("All departments fetched", res.getMessage());
    }
}
