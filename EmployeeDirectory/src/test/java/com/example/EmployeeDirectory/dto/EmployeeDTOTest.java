package com.example.EmployeeDirectory.dto;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeDTOTest {

    @Test
    void testDTOFields() {
        EmployeeDTO dto = new EmployeeDTO( "John Doe", "john@example.com", "IT");

        assertEquals("John Doe", dto.getFullName());
        assertEquals("john@example.com", dto.getContactEmail());
        assertEquals("IT", dto.getDepartmentName());
    }
}
