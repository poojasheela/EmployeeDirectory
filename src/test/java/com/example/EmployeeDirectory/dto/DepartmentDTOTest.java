package com.example.EmployeeDirectory.dto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DepartmentDTOTest {

    @Test
    public void testAllArgsConstructorAndGetters() {
        DepartmentDTO dto = new DepartmentDTO(1L, "HR");

        assertEquals(1L, dto.getId());
        assertEquals("HR", dto.getName());
    }

    @Test
    public void testNoArgsConstructorAndSetters() {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(1L);
        dto.setName("HR");

        assertEquals(1L, dto.getId());
        assertEquals("HR", dto.getName());
    }

    }
