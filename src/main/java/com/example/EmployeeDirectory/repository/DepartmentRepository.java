package com.example.EmployeeDirectory.repository;

import com.example.EmployeeDirectory.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String name);
    boolean existsByNameIgnoreCase(String name);
}
