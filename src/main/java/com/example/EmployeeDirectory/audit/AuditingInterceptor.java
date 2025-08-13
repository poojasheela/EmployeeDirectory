package com.example.EmployeeDirectory.audit;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuditingInterceptor implements HandlerInterceptor {

    private final CustomJdbcTemplate customJdbcTemplate;

    public AuditingInterceptor(CustomJdbcTemplate customJdbcTemplate) {
        this.customJdbcTemplate = customJdbcTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "anonymous";

        if (authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getName())) {
            username = authentication.getName();
        }

        // Set MySQL session variable @logged_user
        customJdbcTemplate.setLoggedUser(username);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // Clear the session variable after request
        customJdbcTemplate.clearLoggedUser();
    }
}
