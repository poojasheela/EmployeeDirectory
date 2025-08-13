package com.example.EmployeeDirectory.config;

import com.example.EmployeeDirectory.audit.AuditingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuditingInterceptor auditingInterceptor;

    public WebConfig(AuditingInterceptor auditingInterceptor) {
        this.auditingInterceptor = auditingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditingInterceptor).addPathPatterns("/**");
    }
}
