package com.example.EmployeeDirectory.service;

import com.example.EmployeeDirectory.dto.DepartmentDTO;
import com.example.EmployeeDirectory.response.ApiResponse;
import java.util.Optional;

public interface DepartmentService {
    ApiResponse create(DepartmentDTO dto);
    ApiResponse update(Integer id, DepartmentDTO dto);
    ApiResponse delete(Integer id);
    ApiResponse getAll();
    ApiResponse getById(Integer id);
    ApiResponse getByName(Optional<String> name);
}
