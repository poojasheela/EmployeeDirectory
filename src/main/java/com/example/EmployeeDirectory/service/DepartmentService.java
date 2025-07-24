package com.example.EmployeeDirectory.service;

import com.example.EmployeeDirectory.dto.DepartmentDTO;
import com.example.EmployeeDirectory.entity.Department;
import java.util.List;

public interface DepartmentService {
    DepartmentDTO createDepartment(DepartmentDTO dto);
    List<DepartmentDTO> getAllDepartments();
}
