package com.example.EmployeeDirectory.controller;
import com.example.EmployeeDirectory.dto.EmployeeDTO;
import com.example.EmployeeDirectory.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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


        private EmployeeDTO employeeDTO;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
            employeeDTO = new EmployeeDTO("Mike", "mike@example.com", "HR");
        }

        @Test
        void testCreateEmployee() {
            when(employeeService.createEmployee(employeeDTO)).thenReturn(employeeDTO);

            ResponseEntity<String> response = employeeController.createEmployee(employeeDTO);

            assertEquals(201, response.getStatusCodeValue());
            assertEquals("Employee created with name: Mike", response.getBody());
            verify(employeeService, times(1)).createEmployee(employeeDTO);
        }

        @Test
        void testFetchAllEmployees() {
            List<EmployeeDTO> employees = Arrays.asList(employeeDTO);
            when(employeeService.fetchAllEmployees()).thenReturn(employees);

            ResponseEntity<List<EmployeeDTO>> response = employeeController.fetchAllEmployees();

            assertEquals(200, response.getStatusCodeValue());
            assertEquals(1, response.getBody().size());
            verify(employeeService, times(1)).fetchAllEmployees();
        }

        @Test
        void testFetchByEmailDomain() {
            when(employeeService.fetchEmployeesByEmailDomain("example.com"))
                    .thenReturn(List.of(employeeDTO));

            ResponseEntity<List<EmployeeDTO>> response = employeeController.fetchEmployeesByEmailDomain("example.com");

            assertEquals(200, response.getStatusCodeValue());
            assertEquals("Mike", response.getBody().get(0).getFullName());
            verify(employeeService).fetchEmployeesByEmailDomain("example.com");
        }

        @Test
        void testFetchByName() {
            when(employeeService.fetchEmployeesByName("Mike"))
                    .thenReturn(List.of(employeeDTO));

            ResponseEntity<List<EmployeeDTO>> response = employeeController.fetchEmployeesByName("Mike");

            assertEquals(200, response.getStatusCodeValue());
            assertEquals("Mike", response.getBody().get(0).getFullName());
            verify(employeeService).fetchEmployeesByName("Mike");
        }


        @Test
        void testUpdateEmployee() {
            when(employeeService.updateEmployeeById(1, employeeDTO)).thenReturn(employeeDTO);

            ResponseEntity<String> response = employeeController.updateEmployee(1, employeeDTO);

            assertEquals(200, response.getStatusCodeValue());
            assertEquals("Updated details of employee with name Mike", response.getBody());
            verify(employeeService).updateEmployeeById(1, employeeDTO);
        }

        @Test
        void testDeleteEmployee() {
            doNothing().when(employeeService).deleteEmployeeById(1);

            ResponseEntity<String> response = employeeController.deleteEmployee(1);

            assertEquals(200, response.getStatusCodeValue());
            assertEquals("Employee deleted with ID: 1", response.getBody());
            verify(employeeService).deleteEmployeeById(1);
        }
    }