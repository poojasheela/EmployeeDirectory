package com.example.EmployeeDirectory.exception;
import com.example.EmployeeDirectory.response.Response;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler handler;

    @Test
    public void testHandleInvalidRequestException() {
        InvalidRequestException ex = new InvalidRequestException("Invalid data");
        ResponseEntity<Response> response = handler.handleInvalid(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid data", response.getBody().getMessage());
    }

    @Test
    public void testEmployeeNotFoundException() {
        EmployeeNotFoundException ex = new EmployeeNotFoundException("Employee not found");
        ResponseEntity<Response> response = handler.handleEmployeeNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Employee not found", response.getBody().getMessage());
    }

    @Test
    public void testDepartmentNotFoundException(){
        DepartmentNotFoundException ex=new DepartmentNotFoundException("Department not found");
        ResponseEntity<Response>response=handler.handleDepartmentNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals("Department not found",response.getBody().getMessage());
    }

    @Test
    public void testDataConflictException(){
        DataConflictException ex=new DataConflictException("Duplicate entry");
        ResponseEntity<Response>response=handler.handleDataConflict(ex);

        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        assertEquals("Duplicate entry",response.getBody().getMessage());

    }

    @Test
    void testMethodArgumentNotValidException() {
        FieldError fieldError = new FieldError("employeeDTO", "email", "must be valid");
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        MethodArgumentNotValidException exception =
                new MethodArgumentNotValidException(mock(MethodParameter.class), bindingResult);

        ResponseEntity<Response> response = handler.handleValidationErrors(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation failed", response.getBody().getMessage());

        Map<String, String> errors = (Map<String, String>) response.getBody().getData();
        assertTrue(errors.containsKey("email"));
        assertEquals("must be valid", errors.get("email"));
    }

    @Test
    void testConstraintViolationException() {
        ConstraintViolationException ex = new ConstraintViolationException("Validation failed", null);
        ResponseEntity<Response> response = handler.handleConstraintViolation(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation failed", response.getBody().getMessage());
    }

    @Test
    void testHandleAccessDeniedException() {
        AccessDeniedException ex = new AccessDeniedException("Forbidden access");
        ResponseEntity<Response> response = handler.handleAccessDenied(ex);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Forbidden: You don't have permission to access this resource.", response.getBody().getMessage());
    }

    @Test
    void testHandleAuthenticationException() {
        AuthenticationException ex = mock(AuthenticationException.class);
        ResponseEntity<Response> response = handler.handleAuthentication(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Unauthorized: Please login to access this resource.", response.getBody().getMessage());
    }

}
