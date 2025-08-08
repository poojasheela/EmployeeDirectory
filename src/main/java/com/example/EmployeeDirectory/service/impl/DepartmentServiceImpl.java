package com.example.EmployeeDirectory.service.impl;
import com.example.EmployeeDirectory.dto.DepartmentDTO;
import com.example.EmployeeDirectory.entity.Department;
import com.example.EmployeeDirectory.exception.DataConflictException;
import com.example.EmployeeDirectory.exception.DepartmentNotFoundException;
import com.example.EmployeeDirectory.exception.EmployeeNotFoundException;
import com.example.EmployeeDirectory.repository.DepartmentRepository;
import com.example.EmployeeDirectory.response.Response;
import com.example.EmployeeDirectory.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor

public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Transactional(rollbackFor = DataConflictException.class)
    @Override
    public Response create(DepartmentDTO dto) {
        try {
            if (departmentRepository.existsByName(dto.getName())) {
                throw new DataConflictException("Department already exists with name: " + dto.getName());
            }

            Department department = new Department();
            department.setName(dto.getName());

            Department saved = departmentRepository.save(department);

            DepartmentDTO responseDto = new DepartmentDTO();
            responseDto.setName(saved.getName());

            return Response.success("Department created with name: " + saved.getName(), responseDto);

        } catch (DataConflictException ex) {
            throw ex;
        }
    }

    @Transactional(rollbackFor = {EmployeeNotFoundException.class, DataConflictException.class})
    @Override
    public Response update(Integer id, DepartmentDTO dto) {
        try {
            Department department = departmentRepository.findById(id)
                    .orElseThrow(() -> new DepartmentNotFoundException("Department not found with ID: " + id));

            if (!department.getName().equalsIgnoreCase(dto.getName()) &&
                    departmentRepository.existsByName(dto.getName())) {
                throw new DataConflictException("Another department with name '" + dto.getName() + "' already exists.");
            }

            department.setName(dto.getName());

            Department updated = departmentRepository.save(department);

            DepartmentDTO responseDto = new DepartmentDTO( updated.getName());
            log.info("Updating department with ID: {}", id);

            return Response.success("Department updated with name: " + updated.getName(), responseDto);
        } catch (DepartmentNotFoundException e) {
            log.warn("Department not found: ", e);
            throw e;
        } catch (DataConflictException e) {
            log.warn("Duplicate department name: ", e);
            throw e;
        }
    }

    @Transactional(rollbackFor = EmployeeNotFoundException.class)
    @Override
    public Response delete(Integer id) {
        try {
            Department department = departmentRepository.findById(id)
                    .orElseThrow(() -> new EmployeeNotFoundException("Department not found with ID: " + id));
            departmentRepository.delete(department);
            return Response.success("Department deleted with ID: " + id, null);
        } catch (EmployeeNotFoundException e) {
            log.warn("Employee not found: ", e);
            throw e;
        }
    }

    @Override
    public Response getAll() {
        List<Department> list = departmentRepository.findAll();
        return Response.success("All departments fetched", list);
    }

    @Transactional(rollbackFor = EmployeeNotFoundException.class)
    @Override
    public Response getById(Integer id) {
        try {
            Department department = departmentRepository.findById(id)
                    .orElseThrow(() -> new EmployeeNotFoundException("Department not found with ID: " + id));

            return Response.success("Department fetched with ID: " + id, department);
        } catch (EmployeeNotFoundException e) {
            log.warn("Employee not found: ", e);
            throw e;
        }
    }

    @Transactional(rollbackFor = EmployeeNotFoundException.class)
    @Override
    public Response getByName(Optional<String> name) {
        try {
            if (name.isPresent()) {
                Department dept = departmentRepository.findByNameIgnoreCase(name.get())
                        .orElseThrow(() -> new EmployeeNotFoundException("Department not found with name: " + name.get()));
                return Response.success("Department found by name", dept);
            } else {
                return getAll();
            }
        } catch (EmployeeNotFoundException e) {
            log.warn("Employee not found: ", e);
            throw e;
        }
    }
}