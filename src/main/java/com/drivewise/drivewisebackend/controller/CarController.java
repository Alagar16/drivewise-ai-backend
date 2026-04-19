package com.drivewise.drivewisebackend.controller;

import com.drivewise.drivewisebackend.model.CarResponse;
import com.drivewise.drivewisebackend.model.UserInput;
import com.drivewise.drivewisebackend.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CarController {

    @Autowired
    private CarService carService;

    /**
     * Get car recommendations based on user input
     */
    @PostMapping("/recommend")
    public List<CarResponse> getRecommendations(@RequestBody UserInput userInput) {
        return carService.getRecommendations(userInput);
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public String health() {
        return "Drivewise AI backend is running!";
    }

    /**
     * Root endpoint for debugging
     */
    @GetMapping("")
    public String root() {
        return "Drivewise AI API - Use /api/cars/health or /api/cars/recommend";
    }
}

