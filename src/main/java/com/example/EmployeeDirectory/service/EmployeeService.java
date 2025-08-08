package com.example.EmployeeDirectory.service;
import com.example.EmployeeDirectory.dto.EmployeeDTO;
import com.example.EmployeeDirectory.response.Response;
import java.util.Optional;
public interface EmployeeService {
    Response create(EmployeeDTO dto);
    Response update(Integer id, EmployeeDTO dto);
    Response delete(Integer id);
    Response getById(Integer id);
    Response getByName(Optional<String> name);
    Response getAll();
    Response getByEmailDomain(Optional<String> domain);
    Response getAllPaginatedEmployees(Optional<Integer> page, Optional<Integer> size);

}


