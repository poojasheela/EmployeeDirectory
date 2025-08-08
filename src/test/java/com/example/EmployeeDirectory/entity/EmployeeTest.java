package com.example.EmployeeDirectory.entity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.time.LocalDateTime;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    private Employee employee;
    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department();
        department.setId(1);
        department.setName("HR");

        employee = new Employee();
        employee.setId(1);
        employee.setFullName("Mike");
        employee.setContactEmail("mike@example.com");
        employee.setPassword("secret");
        employee.setRole("ADMIN");
        employee.setDepartment(department);
        employee.setCreatedTimestamp(LocalDateTime.now());
        employee.setLastUpdatedTimestamp(LocalDateTime.now());
    }

    @Test
    void testEmployeeFields() {
        assertEquals(1, employee.getId());
        assertEquals("Mike", employee.getFullName());
        assertEquals("mike@example.com", employee.getContactEmail());
        assertEquals("secret", employee.getPassword());
        assertEquals("ADMIN", employee.getRole());
        assertEquals("HR", employee.getDepartment().getName());
        assertNotNull(employee.getCreatedTimestamp());
        assertNotNull(employee.getLastUpdatedTimestamp());
    }

    @Test
    void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = employee.getAuthorities();
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    void testUserDetailsMethods() {
        assertEquals("mike@example.com", employee.getUsername());
        assertTrue(employee.isAccountNonExpired());
        assertTrue(employee.isAccountNonLocked());
        assertTrue(employee.isCredentialsNonExpired());
        assertTrue(employee.isEnabled());
    }
}
