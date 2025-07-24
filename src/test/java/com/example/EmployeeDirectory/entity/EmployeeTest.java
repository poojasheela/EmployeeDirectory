package com.example.EmployeeDirectory.entity;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

        @Test
        void testNoArgsConstructor() {
            Employee emp = new Employee();
            assertNotNull(emp);
        }

        @Test
        void testAllArgsConstructor() {
            Department dept = new Department(1L, "HR", new ArrayList<>());
            Employee emp = new Employee(1, "Mike", "mike@example.com", dept);

            assertEquals(1, emp.getId());
            assertEquals("Mike", emp.getName());
            assertEquals("mike@example.com", emp.getEmail());
            assertEquals(dept, emp.getDepartment());
        }

        @Test
        void testSettersAndGetters() {
            Department dept = new Department(2L, "Finance", new ArrayList<>());
            Employee emp = new Employee();

            emp.setId(10);
            emp.setName("Alice");
            emp.setEmail("alice@example.com");
            emp.setDepartment(dept);

            assertEquals(10, emp.getId());
            assertEquals("Alice", emp.getName());
            assertEquals("alice@example.com", emp.getEmail());
            assertEquals(dept, emp.getDepartment());
        }

        @Test
        void testWithNullDepartment() {
            Employee emp = new Employee(3, "Tom", "tom@example.com", null);

            assertEquals(3, emp.getId());
            assertEquals("Tom", emp.getName());
            assertEquals("tom@example.com", emp.getEmail());
            assertNull(emp.getDepartment());
        }
    }


