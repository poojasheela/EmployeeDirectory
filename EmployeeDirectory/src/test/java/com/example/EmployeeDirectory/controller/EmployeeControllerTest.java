package com.example.EmployeeDirectory.controller;
import com.example.EmployeeDirectory.dto.EmployeeDTO;
import com.example.EmployeeDirectory.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private EmployeeDTO mockEmployee;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockEmployee = new EmployeeDTO("Mike", "mike@example.com", "HR");
    }

    @Test
    public void testCreateEmployee_Success() {
        when(employeeService.createEmployee(mockEmployee)).thenReturn(mockEmployee);

        ResponseEntity<String> response = employeeController.createEmployee(mockEmployee);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Employee created with ID: 1", response.getBody());
        verify(employeeService, times(1)).createEmployee(mockEmployee);
    }

    @Test
    public void testGetAllEmployees() {
        List<EmployeeDTO> list = Arrays.asList(mockEmployee);
        when(employeeService.fetchAllEmployees()).thenReturn(list);

        ResponseEntity<List<EmployeeDTO>> response = employeeController.fetchAllEmployees();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }
    @Test
    public void testUpdateEmployee() {
        when(employeeService.updateEmployeeById(1, mockEmployee)).thenReturn(mockEmployee);

        ResponseEntity<String> response = employeeController.updateEmployee(1, mockEmployee);

        assertEquals("Employee updated with ID: 1", response.getBody());
    }

    @Test
    public void testDeleteEmployee() {
        doNothing().when(employeeService).deleteEmployeeById(1);

        ResponseEntity<String> response = employeeController.deleteEmployee(1);

        assertEquals("Employee deleted with ID: 1", response.getBody());
    }
}
