package com.example.EmployeeDirectory.exception;

import com.example.EmployeeDirectory.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiResponse> handleInvalid(InvalidRequestException ex) {
        return ResponseEntity.badRequest().body(ApiResponse.error(ex.getMessage(), 400, null));
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiResponse> notFound(EmployeeNotFoundException ex) {
        return ResponseEntity.status(404).body(ApiResponse.error(ex.getMessage(), 404, null));
    }

    @ExceptionHandler(DataConflictException.class)
    public ResponseEntity<ApiResponse> conflict(DataConflictException ex) {
        return ResponseEntity.status(409).body(ApiResponse.error(ex.getMessage(), 409, null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> validation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return ResponseEntity.badRequest().body(ApiResponse.error("Validation failed", 400, errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> generic(Exception ex) {
        return ResponseEntity.status(500).body(ApiResponse.error("Internal server error", 500, null));
    }
}
