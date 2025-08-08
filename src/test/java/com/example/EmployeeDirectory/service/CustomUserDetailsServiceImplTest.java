package com.example.EmployeeDirectory.service;

import com.example.EmployeeDirectory.entity.Employee;
import com.example.EmployeeDirectory.repository.EmployeeRepository;
import com.example.EmployeeDirectory.service.impl.CustomUserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
class CustomUserDetailsServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @Test
    void testLoadUserByUsername_WhenUserExists() {
        String email = "mike@example.com";
        Employee employee = new Employee();
        employee.setContactEmail(email);
        employee.setPassword("encodedPassword");
        employee.setRole("USER");

        when(employeeRepository.findByContactEmail(email)).thenReturn(Optional.of(employee));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("encodedPassword", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void testLoadUserByUsername_WhenUserNotFound() {

        String email = "notfound@example.com";
        when(employeeRepository.findByContactEmail(email)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
                customUserDetailsService.loadUserByUsername(email));
    }
}
