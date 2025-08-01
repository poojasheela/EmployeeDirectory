package com.example.EmployeeDirectory.service;
import com.example.EmployeeDirectory.dto.EmployeeDTO;
import com.example.EmployeeDirectory.entity.Employee;
import com.example.EmployeeDirectory.response.ApiResponse;
import java.util.Optional;
public interface EmployeeService {
    ApiResponse create(EmployeeDTO dto);
    ApiResponse update(Integer id, EmployeeDTO dto);
    ApiResponse delete(Integer id);
    ApiResponse getById(Integer id);
    ApiResponse getByName(Optional<String> name);
    ApiResponse getAll();
    ApiResponse getByEmailDomain(Optional<String> domain);
}


