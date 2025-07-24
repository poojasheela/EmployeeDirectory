package com.example.EmployeeDirectory.mapper;


import com.example.EmployeeDirectory.dto.EmployeeDTO;
import com.example.EmployeeDirectory.entity.Department;
import com.example.EmployeeDirectory.entity.Employee;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeMapperTest {

    private final EmployeeMapper mapper = new EmployeeMapperImpl();

    @Test
    void testToDTO() {
        Department dept = new Department(1L, "HR", null);
        Employee emp = new Employee(1, "Mike", "mike@example.com", dept);

        EmployeeDTO dto = mapper.toDTO(emp);
        assertEquals("Mike", dto.getFullName());
        assertEquals("mike@example.com", dto.getContactEmail());
        assertEquals("HR", dto.getDepartmentName());
    }

    @Test
    void testToEntity() {
        EmployeeDTO dto = new EmployeeDTO( "Anna", "anna@example.com", "HR");
        Employee emp = mapper.toEntity(dto);

        assertEquals("Anna", emp.getName());
        assertEquals("anna@example.com", emp.getEmail());
        assertNull(emp.getDepartment()); // Since department is set manually in service
    }
}

