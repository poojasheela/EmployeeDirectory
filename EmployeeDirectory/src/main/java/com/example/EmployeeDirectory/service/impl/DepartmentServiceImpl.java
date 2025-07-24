package com.example.EmployeeDirectory.service.impl;

import com.example.EmployeeDirectory.dto.DepartmentDTO;
import com.example.EmployeeDirectory.entity.Department;
import com.example.EmployeeDirectory.entity.Employee;
import com.example.EmployeeDirectory.exception.DataConflictException;
import com.example.EmployeeDirectory.exception.InvalidRequestException;
import com.example.EmployeeDirectory.mapper.DepartmentMapper;
import com.example.EmployeeDirectory.repository.DepartmentRepository;
import com.example.EmployeeDirectory.repository.EmployeeRepository;
import com.example.EmployeeDirectory.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    DepartmentMapper departmentMapper;
    @Override
    public DepartmentDTO createDepartment(DepartmentDTO dto) {
        try {
            if (dto.getName() == null || dto.getName().isBlank()) {
                throw new InvalidRequestException("Department name must not be blank");
            }

            if (departmentRepository.existsByNameIgnoreCase(dto.getName())) {
                throw new DataConflictException("Department with name already exists: " + dto.getName());
            }

            Department department = departmentMapper.toEntity(dto);
            Department saved = departmentRepository.save(department);

            return departmentMapper.toDTO(saved);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create department: " + e.getMessage());
        }
    }


    @Override
     public List<DepartmentDTO> getAllDepartments() {
            try {
                List<Department> departments = departmentRepository.findAll();
                return departmentMapper.toDTOList(departments);
            } catch (Exception e) {
                log.error("Error while fetching departments", e);
                throw new RuntimeException("Unexpected error occurred while fetching departments");
            }
        }
    }
