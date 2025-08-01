package com.example.EmployeeDirectory.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private Object data;

    public static ApiResponse success(String message, Object data) {
        return new ApiResponse(LocalDateTime.now(), 200, message, data);
    }

    public static ApiResponse error(String message, int status, Object data) {
        return new ApiResponse(LocalDateTime.now(), status, message, data);
    }

}
