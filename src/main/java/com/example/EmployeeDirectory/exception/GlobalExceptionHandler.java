package com.example.EmployeeDirectory.exception;

import com.example.EmployeeDirectory.response.Response;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.nio.file.AccessDeniedException;
import java.util.Map;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Response> handleInvalid(InvalidRequestException ex) {
        return ResponseEntity
                .badRequest()
                .body(Response.error(ex.getMessage(), 400, null));
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Response> handleEmployeeNotFound(EmployeeNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Response.error(ex.getMessage(), 404, null));
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<Response> handleDepartmentNotFound(DepartmentNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Response.error(ex.getMessage(), 404, null));
    }

    @ExceptionHandler(DataConflictException.class)
    public ResponseEntity<Response> handleDataConflict(DataConflictException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Response.error(ex.getMessage(), 409, null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage
                        , (a, b) -> a));
        return ResponseEntity
                .badRequest()
                .body(Response.error("Validation failed", 400, errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response> handleConstraintViolation(ConstraintViolationException ex) {
        return ResponseEntity
                .badRequest()
                .body(Response.error("Validation failed", 400, ex.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Response> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(Response.error("Forbidden: You don't have permission to access this resource.", 403, null));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Response> handleAuthentication(AuthenticationException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Response.error("Unauthorized: Please login to access this resource.", 401, null));
    }

}
