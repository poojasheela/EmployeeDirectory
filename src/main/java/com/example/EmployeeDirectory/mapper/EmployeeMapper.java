package com.example.EmployeeDirectory.mapper;

import com.example.EmployeeDirectory.dto.EmployeeDTO;
import com.example.EmployeeDirectory.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "fullName", source = "name")
    @Mapping(target = "contactEmail", source = "email")
    @Mapping(target = "departmentName", source = "department.name")
    EmployeeDTO toDTO(Employee employee);

    @Mapping(target = "name", source = "fullName")
    @Mapping(target = "email", source = "contactEmail")
    @Mapping(target = "department", ignore = true)
    Employee toEntity(EmployeeDTO dto);

    List<EmployeeDTO> toDTOList(List<Employee> employees);
}
