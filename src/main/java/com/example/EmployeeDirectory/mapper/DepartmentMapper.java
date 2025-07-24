package com.example.EmployeeDirectory.mapper;
import com.example.EmployeeDirectory.dto.DepartmentDTO;
import com.example.EmployeeDirectory.entity.Department;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentDTO toDTO(Department department);

    Department toEntity(DepartmentDTO dto);

    List<DepartmentDTO> toDTOList(List<Department> departments);
}
