package com.example.EmployeeDirectory.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDateTime;


public class DepartmentTest {

    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department(1, "HR", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1, department.getId());
        assertEquals("HR", department.getName());
    }

    @Test
    void testNoArgsConstructor() {
        Department dep = new Department();
        assertNotNull(dep);
    }

    @Test
    void testAllArgsConstructor() {
        Department dep = new Department(2, "IT", LocalDateTime.now(), LocalDateTime.now());
        assertEquals("IT", dep.getName());
    }
}

