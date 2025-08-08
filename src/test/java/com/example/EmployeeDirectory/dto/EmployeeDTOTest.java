package com.example.EmployeeDirectory.dto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import jakarta.validation.*;
import java.util.Set;

public class EmployeeDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidEmployeeDTO() {
        EmployeeDTO dto = new EmployeeDTO(
                1,
                "Mike",
                "mike@example.com",
                "password123",
                "USER",
                "HR",
                null,
                null
        );

        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Should not have any constraint violations");
    }

    @Test
    void testInvalidEmailFormat() {
        EmployeeDTO dto = new EmployeeDTO(
                1,
                "Mike",
                "Mike@Example.com", // not lowercase
                "password123",
                "USER",
                "HR",
                null,
                null
        );

        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Should have violations due to invalid email");
    }

    @Test
    void testBlankFields() {
        EmployeeDTO dto = new EmployeeDTO();

        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(dto);
        assertEquals(5, violations.size(), "Should have 5 violations for missing required fields");
    }
}
