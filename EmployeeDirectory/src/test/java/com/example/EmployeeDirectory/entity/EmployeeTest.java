package com.example.EmployeeDirectory.entity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void testEmployeeConstructorAndGetters() {
        Department dept = new Department(1L, "HR", null);
        Employee emp = new Employee(1, "Mike", "mike@example.com", dept);

        assertEquals(1, emp.getId());
        assertEquals("Mike", emp.getName());
        assertEquals("mike@example.com", emp.getEmail());
        assertEquals("HR", emp.getDepartment().getName());
    }
}
