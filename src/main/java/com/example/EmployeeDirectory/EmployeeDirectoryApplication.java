package com.example.EmployeeDirectory;

import com.example.EmployeeDirectory.entity.Department;
import com.example.EmployeeDirectory.entity.Employee;
import com.example.EmployeeDirectory.repository.DepartmentRepository;
import com.example.EmployeeDirectory.repository.EmployeeRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.TimeZone;
@Slf4j
@SpringBootApplication
public class EmployeeDirectoryApplication {

	public static void main(String[] args) {

		SpringApplication.run(EmployeeDirectoryApplication.class, args);
	}

}