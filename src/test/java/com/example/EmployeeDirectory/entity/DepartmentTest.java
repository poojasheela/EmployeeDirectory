package com.example.EmployeeDirectory.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collections;

class DepartmentTest {

    @Test
    void testDepartmentConstructorAndGetters() {
        Department dept = new Department(1L, "Finance", Collections.emptyList());

        assertEquals(1L, dept.getId());
        assertEquals("Finance", dept.getName());
        assertTrue(dept.getEmployees().isEmpty());
    }
}
