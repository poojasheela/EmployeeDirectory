package com.example.EmployeeDirectory.audit;

import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.sql.Statement;

@Component
public class CustomJdbcTemplate {

    private final JdbcTemplate jdbcTemplate;

    public CustomJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setLoggedUser(String username) {
        jdbcTemplate.update("SET @logged_user = ?", username);
    }

    public void clearLoggedUser() {
        jdbcTemplate.update("SET @logged_user = NULL");
    }
}
