package com.example.EmployeeDirectory.controller;
import com.example.EmployeeDirectory.dto.DepartmentDTO;
import com.example.EmployeeDirectory.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    private DepartmentDTO mockDepartment;

    @BeforeEach
    public void setup() {
        mockDepartment = new DepartmentDTO(1L, "HR");
    }

    @Test
    public void testCreateDepartment_Success() {

        when(departmentService.createDepartment(mockDepartment)).thenReturn(mockDepartment);

        ResponseEntity<String> response = departmentController.createDepartment(mockDepartment);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Department created with ID: 1", response.getBody());
    }

    @Test
    public void testGetAllDepartments() {
        List<DepartmentDTO> list = Arrays.asList(mockDepartment);
        when(departmentService.getAllDepartments()).thenReturn(list);

        ResponseEntity<List<DepartmentDTO>> response = departmentController.getAllDepartments();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }
}
