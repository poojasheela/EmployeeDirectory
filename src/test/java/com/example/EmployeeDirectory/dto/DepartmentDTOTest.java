package com.example.EmployeeDirectory.dto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DepartmentDTOTest {

    @Test
    public void testAllArgsConstructorAndGetters() {
        DepartmentDTO dto = new DepartmentDTO( "HR");

        assertEquals("HR", dto.getName());
    }

    @Test
    public void testNoArgsConstructorAndSetters() {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setName("HR");
        assertEquals("HR", dto.getName());
    }

    }
