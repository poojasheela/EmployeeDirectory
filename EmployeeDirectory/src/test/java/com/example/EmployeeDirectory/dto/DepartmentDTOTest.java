package com.example.EmployeeDirectory.dto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DepartmentDTOTest {

    @Test
    void testDepartmentDTO() {
        DepartmentDTO dto = new DepartmentDTO(1L, "Engineering");

        assertEquals(1L, dto.getId());
        assertEquals("Engineering", dto.getName());
    }
}
