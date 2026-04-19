package com.drivewise.drivewisebackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RootController {

    /**
     * Root endpoint - test if application is working
     */
    @GetMapping("/")
    public Map<String, Object> root() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        response.put("service", "Drivewise AI Backend");
        response.put("version", "0.0.1");
        response.put("message", "Car Recommendation System is running");
        response.put("endpoints", new HashMap<String, String>() {{
            put("health", "/api/cars/health");
            put("recommend", "POST /api/cars/recommend");
            put("root", "/");
        }});
        return response;
    }

    /**
     * Simple test endpoint
     */
    @GetMapping("/test")
    public Map<String, String> test() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "running");
        response.put("message", "Backend is working correctly");
        return response;
    }
}

