package com.example.EmployeeDirectory.controller;
import com.example.EmployeeDirectory.dto.DepartmentDTO;
import com.example.EmployeeDirectory.response.Response;
import com.example.EmployeeDirectory.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

public class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    private DepartmentDTO dto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        dto = new DepartmentDTO("HR");
    }

    @Test
    public void testCreate_success() {
        Response expected = Response.success("Created", dto);
        when(departmentService.create(dto)).thenReturn(expected);

        Response actual = departmentController.createDepartment(dto);

        assertEquals(200, actual.getStatus());
        assertEquals("Created", actual.getMessage());
        assertEquals(dto, actual.getData());
    }

    @Test
    public void testCreate_IfNameNull() {
        DepartmentDTO invalidDto = new DepartmentDTO(null);
        Response expected = Response.error("Department name cannot be null", 400, null);
        when(departmentService.create(invalidDto)).thenReturn(expected);

        Response actual = departmentController.createDepartment(invalidDto);

        assertEquals(400, actual.getStatus());
        assertEquals("Department name cannot be null", actual.getMessage());
    }


    @Test
    public void testUpdate_success() {
        Response expected = Response.success("Updated", dto);
        when(departmentService.update(1, dto)).thenReturn(expected);

        Response actual = departmentController.updateDepartment(1, dto);

        assertEquals(200, actual.getStatus());
        assertEquals("Updated", actual.getMessage());
        assertEquals(dto, actual.getData());
    }

    @Test
    public void testUpdate_IfInvalidId() {
        Response expected = Response.error("Department not found", 404, null);
        when(departmentService.update(99, dto)).thenReturn(expected);

        Response actual = departmentController.updateDepartment(99, dto);

        assertEquals(404, actual.getStatus());
        assertEquals("Department not found", actual.getMessage());
    }

    @Test
    public void testGetById_success() {
        Response expected = Response.success("Found", dto);
        when(departmentService.getById(1)).thenReturn(expected);

        Response actual = departmentController.getDepartmentById(1);

        assertEquals(200, actual.getStatus());
        assertEquals("Found", actual.getMessage());
    }

    @Test
    public void testGetById_IfInvalidId() {
        Response expected = Response.error("Department not found", 404, null);
        when(departmentService.getById(99)).thenReturn(expected);

        Response actual = departmentController.getDepartmentById(99);

        assertEquals(404, actual.getStatus());
        assertEquals("Department not found", actual.getMessage());
    }

    @Test
    public void testGetByName_success() {
        Response expected = Response.success("Found", dto);
        when(departmentService.getByName(Optional.of("HR"))).thenReturn(expected);

        Response actual = departmentController.getByName(Optional.of("HR"));

        assertEquals(200, actual.getStatus());
    }

    @Test
    public void testGetByName_IfOptionalEmpty() {
        Response expected = Response.success("All departments fetched", Collections.singletonList(dto));
        when(departmentService.getByName(Optional.empty())).thenReturn(expected);

        Response actual = departmentController.getByName(Optional.empty());

        assertEquals(200, actual.getStatus());
        assertEquals("All departments fetched", actual.getMessage());
    }

    @Test
    public void testGetAllDepartments_Success() {
        Response expected = Response.success("List", Collections.singletonList(dto));
        when(departmentService.getAll()).thenReturn(expected);

        Response actual = departmentController.getAllDepartments();

        assertEquals(200, actual.getStatus());
        assertEquals("List", actual.getMessage());

    }


    @Test
    public void testGetAllDepartments_IfEmpty() {
        Response expected = Response.success("Empty", Collections.emptyList());
        when(departmentService.getAll()).thenReturn(expected);

        Response actual=departmentController.getAllDepartments();

        assertEquals(200,actual.getStatus());
        assertEquals("Empty",actual.getMessage());
    }

    @Test
    public void testDelete_Success(){
        Response expected = Response.success("Department deleted",null);
        when(departmentService.delete(1)).thenReturn(expected);

        Response actual=departmentController.deleteDepartment(1);

        assertEquals(200,actual.getStatus());
        assertEquals("Department deleted",actual.getMessage());

    }


    @Test
    public void testDelete_IfInvalidId() {
        Response expected = Response.error("Department not found", 404, null);
        when(departmentService.delete(99)).thenReturn(expected);

        Response actual = departmentController.deleteDepartment(99);

        assertEquals(404, actual.getStatus());
        assertEquals("Department not found", actual.getMessage());
    }
    }