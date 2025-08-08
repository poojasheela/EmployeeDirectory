package com.example.EmployeeDirectory.controller;
import com.example.EmployeeDirectory.dto.EmployeeDTO;
import com.example.EmployeeDirectory.response.Response;
import com.example.EmployeeDirectory.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private EmployeeDTO employeeDTO;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeDTO= new EmployeeDTO(1, "Mike", "mike@example.com", "password123",
                "USER", "HR", null, null);
    }


    @Test
    public void testCreate_success() {
        Response expected = Response.success("Created", employeeDTO);
        when(employeeService.create(employeeDTO)).thenReturn(expected);

        Response actual = employeeController.createEmployee(employeeDTO);

        assertEquals(200, actual.getStatus());
        assertEquals("Created", actual.getMessage());
        assertEquals(employeeDTO, actual.getData());
    }

    @Test
    public void testCreate_invalidInput() {

        EmployeeDTO invalidDTO = new EmployeeDTO(1, "Mike", "", "password123",
                "USER", "HR", null, null);

        Response expected = Response.error("Invalid data", 400, null);
        when(employeeService.create(invalidDTO)).thenReturn(expected);

        Response actual = employeeController.createEmployee(invalidDTO);

        assertEquals(400, actual.getStatus());
        assertEquals("Invalid data", actual.getMessage());
        assertNull(actual.getData());
    }

    @Test
    public void testUpdate_success() {
        Response expected = Response.success("Updated", employeeDTO);
        when(employeeService.update(1, employeeDTO)).thenReturn(expected);

        Response actual = employeeController.updateEmployee(1,employeeDTO);

        assertEquals(200, actual.getStatus());
        assertEquals("Updated", actual.getMessage());
        assertEquals(employeeDTO, actual.getData());
    }


    @Test
    public void testUpdate_ifInvalidId() {
        Response expected = Response.error("Employee not found", 404, null);
        when(employeeService.update(99, employeeDTO)).thenReturn(expected);

        Response actual = employeeController.updateEmployee(99, employeeDTO);

        assertEquals(404, actual.getStatus());
        assertEquals("Employee not found", actual.getMessage());
    }


    @Test
    public void testGetAllEmployees_success() {
        Response expected = Response.success("List", Collections.singletonList(employeeDTO));
        when(employeeService.getAll()).thenReturn(expected);

        Response actual = employeeController.getAllEmployees();

        assertEquals(200, actual.getStatus());
        assertEquals("List", actual.getMessage());

    }


    @Test
    public void testGetAllEmployees_ifEmpty() {
        Response expected = Response.success("Empty", Collections.emptyList());
        when(employeeService.getAll()).thenReturn(expected);

        Response actual=employeeController.getAllEmployees();

        assertEquals(200,actual.getStatus());
        assertEquals("Empty",actual.getMessage());
    }


    @Test
    public void testGetById_success() {
        Response expected = Response.success("Found", employeeDTO);
        when(employeeService.getById(1)).thenReturn(expected);

        Response actual = employeeController.getById(1);

        assertEquals(200, actual.getStatus());
        assertEquals("Found", actual.getMessage());
    }

    @Test
    public void testGetById_ifInvalidId() {
        Response expected = Response.error("Employee not found", 404, null);
        when(employeeService.getById(99)).thenReturn(expected);

        Response actual = employeeController.getById(99);

        assertEquals(404, actual.getStatus());
        assertEquals("Employee not found", actual.getMessage());
    }


    @Test
    public void testGetByName_success() {
        Response expected = Response.success("Found", employeeDTO);
        when(employeeService.getByName(Optional.of("Mike"))).thenReturn(expected);

        Response actual = employeeController.getByName(Optional.of("Mike"));

        assertEquals(200, actual.getStatus());
    }

    @Test
    public void testGetByName_ifOptionalEmpty() {
        Response expected = Response.success("Fetched all employees", employeeDTO);
        when(employeeService.getByName(Optional.empty())).thenReturn(expected);

        Response actual = employeeController.getByName(Optional.empty());

        assertEquals(200, actual.getStatus());
        assertEquals("Fetched all employees", actual.getMessage());
    }

    @Test
    public void testGetByEmailDomain_success() {
        Response expected = Response.success("Found", employeeDTO);
        when(employeeService.getByEmailDomain(Optional.of("@example.com"))).thenReturn(expected);

        Response actual = employeeController.getByEmailDomain(Optional.of("@example.com"));

        assertEquals(200, actual.getStatus());
    }

    @Test
    public void testGetByEmailDomain_ifOptionalEmpty() {
        Response expected = Response.success("Fetched all employees", employeeDTO);
        when(employeeService.getByName(Optional.empty())).thenReturn(expected);

        Response actual = employeeController.getByName(Optional.empty());

        assertEquals(200, actual.getStatus());
        assertEquals("Fetched all employees", actual.getMessage());
    }


    @Test
    public void testGetAllEmployeesPaginated_success() {
        Response expected = Response.success("Page fetched", Collections.singletonList(employeeDTO));
        when(employeeService.getAllPaginatedEmployees(Optional.of(0), Optional.of(5))).thenReturn(expected);

        Response actual = employeeController.getAllEmployees(Optional.of(0), Optional.of(5));

        assertEquals(200, actual.getStatus());
        assertEquals("Page fetched", actual.getMessage());
        assertEquals(Collections.singletonList(employeeDTO), actual.getData());
    }

    @Test
    public void testGetAllEmployeesPaginated_ifPageEmpty() {
        Response expected = Response.success("Default page fetched", Collections.singletonList(employeeDTO));
        when(employeeService.getAllPaginatedEmployees(Optional.empty(), Optional.empty())).thenReturn(expected);

        Response actual = employeeController.getAllEmployees(Optional.empty(), Optional.empty());

        assertEquals(200, actual.getStatus());
        assertEquals("Default page fetched", actual.getMessage());
    }

    @Test
    public void testGetAllEmployeesPaginated_invalidPage() {
        Response expected = Response.error("Invalid page parameters", 400, null);
        when(employeeService.getAllPaginatedEmployees(Optional.of(-1), Optional.of(0))).thenReturn(expected);

        Response actual = employeeController.getAllEmployees(Optional.of(-1), Optional.of(0));

        assertEquals(400, actual.getStatus());
        assertEquals("Invalid page parameters", actual.getMessage());
    }


    @Test
    public void testDelete_success(){
        Response expected = Response.success("Employee deleted",null);
        when(employeeService.delete(1)).thenReturn(expected);

        Response actual=employeeController.deleteEmployee(1);

        assertEquals(200,actual.getStatus());
        assertEquals("Employee deleted",actual.getMessage());

    }

    @Test
    public void testDelete_ifInvalidId() {
        Response expected = Response.error("Employee not found", 404, null);
        when(employeeService.delete(99)).thenReturn(expected);

        Response actual = employeeController.deleteEmployee(99);

        assertEquals(404, actual.getStatus());
        assertEquals("Employee not found", actual.getMessage());
    }
}