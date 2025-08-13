package com.example.EmployeeDirectory.audit;

import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.sql.Statement;

import java.sql.SQLException;
import java.sql.Statement;

@Component
public class CustomJdbcTemplate {

    private final JdbcTemplate jdbcTemplate;

    public CustomJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setLoggedUser(String username) {
        jdbcTemplate.execute((ConnectionCallback<Void>) connection -> {
            try (Statement statement = connection.createStatement()) {
                statement.execute("SET @logged_user = '" + username + "'");
            } catch (SQLException e) {
                throw new RuntimeException("Error setting @logged_user: " + e.getMessage(), e);
            }
            return null;
        });
    }

    public void clearLoggedUser() {
        jdbcTemplate.execute((ConnectionCallback<Void>) connection -> {
            try (Statement statement = connection.createStatement()) {
                statement.execute("SET @logged_user = NULL");
            } catch (SQLException e) {
                throw new RuntimeException("Error clearing @logged_user: " + e.getMessage(), e);
            }
            return null;
        });
    }
}
