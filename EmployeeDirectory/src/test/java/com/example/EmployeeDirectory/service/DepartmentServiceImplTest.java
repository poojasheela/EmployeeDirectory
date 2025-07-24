package com.example.EmployeeDirectory.service;

import com.example.EmployeeDirectory.dto.DepartmentDTO;
import com.example.EmployeeDirectory.entity.Department;
import com.example.EmployeeDirectory.exception.DataConflictException;
import com.example.EmployeeDirectory.mapper.DepartmentMapper;
import com.example.EmployeeDirectory.repository.DepartmentRepository;
import com.example.EmployeeDirectory.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

class DepartmentServiceImplTest {

    private DepartmentRepository repo;
    private DepartmentMapper mapper;
    private DepartmentServiceImpl service;

    @BeforeEach
    void setUp() {
        repo = mock(DepartmentRepository.class);
        mapper = mock(DepartmentMapper.class);
      //  service = new DepartmentServiceImpl(repo, mapper);
    }

    @Test
    void testCreateDepartmentSuccess() {
        DepartmentDTO dto = new DepartmentDTO(null, "Sales");
        Department entity = new Department(1L, "Sales", null);

        when(repo.findByName("Sales")).thenReturn(Optional.empty());
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(new DepartmentDTO(1L, "Sales"));

        DepartmentDTO result = service.createDepartment(dto);

        assertEquals("Sales", result.getName());
        verify(repo).save(entity);
    }

    @Test
    void testCreateDepartmentConflict() {
        DepartmentDTO dto = new DepartmentDTO(null, "HR");

        when(repo.findByName("HR")).thenReturn(Optional.of(new Department()));

        assertThrows(DataConflictException.class, () -> service.createDepartment(dto));
    }
}
