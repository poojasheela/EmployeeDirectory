package com.example.EmployeeDirectory.mapper;


import com.example.EmployeeDirectory.dto.EmployeeDTO;
import com.example.EmployeeDirectory.entity.Department;
import com.example.EmployeeDirectory.entity.Employee;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class EmployeeMapperTest {
    @InjectMocks
    private final EmployeeMapper mapper = new EmployeeMapperImpl();

    @Test
    void testToDTO() {
        Department dept = new Department(1L, "HR", new ArrayList<>());
        Employee emp = new Employee(1, "Mike", "mike@example.com", dept);

        EmployeeDTO dto = mapper.toDTO(emp);
        assertEquals("Mike", dto.getFullName());
        assertEquals("mike@example.com", dto.getContactEmail());
        assertEquals("HR", dto.getDepartmentName());
    }

    @Test
    void testToDTO_NullDepartment() {
        Employee emp = new Employee(1, "Sara", "sara@example.com", null);

        EmployeeDTO dto = mapper.toDTO(emp);
        assertEquals("Sara", dto.getFullName());
        assertEquals("sara@example.com", dto.getContactEmail());
        assertNull(dto.getDepartmentName());
    }

    @Test
    void testToDTOList() {
        Department dept1 = new Department(1L, "IT", new ArrayList<>());
        Department dept2 = new Department(2L, "Finance", new ArrayList<>());

        Employee emp1 = new Employee(1, "Tom", "tom@example.com", dept1);
        Employee emp2 = new Employee(2, "Jerry", "jerry@example.com", dept2);

        List<EmployeeDTO> dtos = mapper.toDTOList(List.of(emp1, emp2));

        assertEquals(2, dtos.size());
        assertEquals("Tom", dtos.get(0).getFullName());
        assertEquals("Finance", dtos.get(1).getDepartmentName());
    }

    @Test
    void testToDTOList_EmptyList() {
        List<EmployeeDTO> result = mapper.toDTOList(new ArrayList<>());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testToEntity() {
        EmployeeDTO dto = new EmployeeDTO("Anna", "anna@example.com", "HR");
        Employee emp = mapper.toEntity(dto);

        assertEquals("Anna", emp.getName());
        assertEquals("anna@example.com", emp.getEmail());
        assertNull(emp.getDepartment()); // department is assigned manually in service
    }


    @Test
    void testToDTO_NullEmployee() {
        EmployeeDTO dto = mapper.toDTO(null);
        assertNull(dto);
    }

}
