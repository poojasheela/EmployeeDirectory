package com.example.EmployeeDirectory.security;

import com.example.EmployeeDirectory.entity.Employee;
import com.example.EmployeeDirectory.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Optional;
import static com.example.EmployeeDirectory.constants.Constants.ADMIN;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Optional<Employee> existingAdmin = employeeRepository.findByContactEmail("admin@gmail.com");

            if (existingAdmin.isEmpty()) {
                Employee admin = new Employee();
                admin.setFullName("Admin");
                admin.setContactEmail("admin@gmail.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(ADMIN);

                employeeRepository.save(admin);
                log.info(" Admin user created: admin@gmail.com / admin123");
            } else {
                log.info("Admin user already exists");
            }
        };
    }
}
