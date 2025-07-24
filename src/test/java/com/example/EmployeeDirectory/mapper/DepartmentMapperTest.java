package com.example.EmployeeDirectory.mapper;

import com.example.EmployeeDirectory.dto.DepartmentDTO;
import com.example.EmployeeDirectory.entity.Department;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DepartmentMapperTest {

    @InjectMocks
    private DepartmentMapperImpl mapper = new DepartmentMapperImpl();

    @Test
    void testToDTO() {
        Department department = new Department(1L, "HR", new ArrayList<>());
        DepartmentDTO dto = mapper.toDTO(department);

        assertEquals("HR", dto.getName());
        assertEquals(1L, dto.getId());
    }

    @Test
    void testToEntity() {
        DepartmentDTO dto = new DepartmentDTO(1L, "Finance");
        Department entity = mapper.toEntity(dto);

        assertEquals("Finance", entity.getName());
        assertEquals(1L, entity.getId());
    }

    @Test
    void testToDTOList() {
        Department dept1 = new Department(1L, "HR", new ArrayList<>());
        Department dept2 = new Department(2L, "IT", new ArrayList<>());

        List<DepartmentDTO> dtoList = mapper.toDTOList(List.of(dept1, dept2));

        assertEquals(2, dtoList.size());
        assertEquals("HR", dtoList.get(0).getName());
        assertEquals("IT", dtoList.get(1).getName());
    }
}
