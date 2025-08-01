package com.example.EmployeeDirectory.repository;

import com.example.EmployeeDirectory.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByContactEmail(String email);
    @Query("SELECT e FROM Employee e WHERE e.contactEmail LIKE %:domain")
    List<Employee> findByEmailDomain(@Param("domain") String domain);

    Optional<Employee> findByFullName(String name);
}
