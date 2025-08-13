package com.example.EmployeeDirectory.service;

import com.example.EmployeeDirectory.dto.DepartmentDTO;
import com.example.EmployeeDirectory.response.Response;
import java.util.Optional;

public interface DepartmentService {
        Response create(DepartmentDTO dto);
        Response update(Integer id, DepartmentDTO dto);
        Response delete(Integer id);
        Response getAll();
        Response getById(Integer id);
        Response getByName(Optional<String> name);
    }


