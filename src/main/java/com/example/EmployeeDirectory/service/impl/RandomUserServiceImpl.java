package com.example.EmployeeDirectory.service.impl;


import com.example.EmployeeDirectory.service.RandomUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
@Service
public class RandomUserServiceImpl implements RandomUserService {

    private final WebClient webClient;
    private final boolean enabled;

    public RandomUserServiceImpl(WebClient.Builder webClientBuilder,
                                 @Value("${randomuser.base-url}") String baseUrl,
                                 @Value("${randomuser.enabled}") boolean enabled) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.enabled = enabled;
    }

    @Override
    public String getRandomUsers(int count) {
        if (!enabled) {
            return "RandomUser API is disabled in configuration.";
        }
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("results", count).build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
