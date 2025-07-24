package com.example.EmployeeDirectory.dto;
import com.example.EmployeeDirectory.dto.EmployeeDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeDTOTest {

    @Test
    public void testAllArgsConstructorAndGetters() {
        EmployeeDTO dto = new EmployeeDTO("Mike", "mike@example.com", "HR");

        assertEquals("Mike", dto.getFullName());
        assertEquals("mike@example.com", dto.getContactEmail());
        assertEquals("HR", dto.getDepartmentName());
    }

    @Test
    public void testNoArgsConstructorAndSetters() {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setFullName("Mike");
        dto.setContactEmail("mike@example.com");
        dto.setDepartmentName("HR");

        assertEquals("Mike", dto.getFullName());
        assertEquals("mike@example.com", dto.getContactEmail());
        assertEquals("HR", dto.getDepartmentName());
    }

        @Test
        public void testToString() {
            EmployeeDTO dto = new EmployeeDTO("Mike", "mike@example.com", "HR");
            String str = dto.toString();

            assertTrue(str.contains("Mike"));
            assertTrue(str.contains("mike@example.com"));
            assertTrue(str.contains("HR"));
        }

        @Test
        public void testInequality() {
            EmployeeDTO dto1 = new EmployeeDTO("Mike", "mike@example.com", "HR");
            EmployeeDTO dto2 = new EmployeeDTO("John", "john@example.com", "Finance");

            assertNotEquals(dto1, dto2);
        }
    }


