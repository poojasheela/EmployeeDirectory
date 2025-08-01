package com.example.EmployeeDirectory;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class EmployeeDirectoryApplication {

	public static void main(String[] args) {

		SpringApplication.run(EmployeeDirectoryApplication.class, args);
	}

}
