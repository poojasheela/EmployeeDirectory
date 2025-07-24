package com.example.EmployeeDirectory.service;

import com.example.EmployeeDirectory.dto.DepartmentDTO;
import com.example.EmployeeDirectory.entity.Department;
import com.example.EmployeeDirectory.mapper.DepartmentMapper;
import com.example.EmployeeDirectory.repository.DepartmentRepository;
import com.example.EmployeeDirectory.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentMapper departmentMapper;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDepartment_Success() {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setName("HR");

        Department entity = new Department();
        entity.setId(1L);
        entity.setName("HR");

        when(departmentRepository.existsByNameIgnoreCase("HR")).thenReturn(false);
        when(departmentMapper.toEntity(dto)).thenReturn(entity);
        when(departmentRepository.save(entity)).thenReturn(entity);
        when(departmentMapper.toDTO(entity)).thenReturn(dto);

        DepartmentDTO result = departmentService.createDepartment(dto);

        assertNotNull(result);
        assertEquals("HR", result.getName());
    }

    @Test
    void testCreateDepartment_BlankName() {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setName(" ");

        Exception ex = assertThrows(RuntimeException.class, () -> departmentService.createDepartment(dto));
        assertTrue(ex.getMessage().contains("Department name must not be blank"));
    }

    @Test
    void testCreateDepartment_DuplicateName() {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setName("HR");

        when(departmentRepository.existsByNameIgnoreCase("HR")).thenReturn(true);

        Exception ex = assertThrows(RuntimeException.class, () -> departmentService.createDepartment(dto));
        assertTrue(ex.getMessage().contains("Department with name already exists"));
    }

    @Test
    void testGetAllDepartments_Success() {
        Department dept = new Department();
        dept.setId(1L);
        dept.setName("HR");

        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(1L);
        dto.setName("HR");

        when(departmentRepository.findAll()).thenReturn(List.of(dept));
        when(departmentMapper.toDTOList(List.of(dept))).thenReturn(List.of(dto));

        List<DepartmentDTO> result = departmentService.getAllDepartments();

        assertEquals(1, result.size());
        assertEquals("HR", result.get(0).getName());
    }
}
