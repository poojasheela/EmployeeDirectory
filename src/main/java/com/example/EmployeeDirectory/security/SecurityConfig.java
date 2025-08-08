package com.example.EmployeeDirectory.security;

import com.example.EmployeeDirectory.service.impl.CustomUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import static com.example.EmployeeDirectory.constants.Constants.*;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomAuthenticationEntryPoint customAuthEntryPoint;
    private final CustomUserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, EMPLOYEE_ADD, DEPARTMENT_ADD).hasRole(ADMIN)
                        .requestMatchers(HttpMethod.PUT, EMPLOYEE_BASE,DEPARTMENT_BASE).hasRole(ADMIN)
                        .requestMatchers(HttpMethod.DELETE, EMPLOYEE_BASE,DEPARTMENT_BASE).hasRole(ADMIN)
                        .requestMatchers(HttpMethod.GET,EMPLOYEE_BASE,DEPARTMENT_BASE).hasAnyRole(ADMIN, USER)
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()).exceptionHandling(ex -> ex
                .authenticationEntryPoint(customAuthEntryPoint));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
