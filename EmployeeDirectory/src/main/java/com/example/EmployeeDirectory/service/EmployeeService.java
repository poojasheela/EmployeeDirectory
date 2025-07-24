package com.example.EmployeeDirectory.service;
import com.example.EmployeeDirectory.dto.EmployeeDTO;
import org.springframework.data.domain.Page;

import java.util.List;
public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO dto);
    List<EmployeeDTO> fetchAllEmployees();
    List<EmployeeDTO> fetchEmployeesByName(String name);
    List<EmployeeDTO> fetchEmployeesByEmailDomain(String domain);
    Page<EmployeeDTO> fetchPaginatedEmployees(int page, int size, String sortBy, String sortDir);
    EmployeeDTO updateEmployeeById(int id, EmployeeDTO dto);
    void deleteEmployeeById(int id);
}
