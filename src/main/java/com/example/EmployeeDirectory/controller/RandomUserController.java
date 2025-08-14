package com.example.EmployeeDirectory.controller;

import com.example.EmployeeDirectory.service.RandomUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/random-users")
public class RandomUserController {

    private final RandomUserService randomUserService;

    public RandomUserController(RandomUserService randomUserService) {
        this.randomUserService = randomUserService;
    }

    @GetMapping
    public ResponseEntity<String> getRandomUsers(@RequestParam(defaultValue = "1") int count) {
        String users = randomUserService.getRandomUsers(count);
        return ResponseEntity.ok(users);
    }
}
