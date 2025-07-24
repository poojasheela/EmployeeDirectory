package com.example.EmployeeDirectory.mapper;

import com.example.EmployeeDirectory.dto.DepartmentDTO;
import com.example.EmployeeDirectory.entity.Department;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DepartmentMapperTest {

    private final DepartmentMapper mapper = new DepartmentMapperImpl();

    @Test
    void testToDTO() {
        Department dept = new Department(1L, "IT", null);
        DepartmentDTO dto = mapper.toDTO(dept);

        assertEquals(1L, dto.getId());
        assertEquals("IT", dto.getName());
    }

    @Test
    void testToEntity() {
        DepartmentDTO dto = new DepartmentDTO(1L, "Finance");
        Department dept = mapper.toEntity(dto);

        assertEquals("Finance", dept.getName());
    }
}
