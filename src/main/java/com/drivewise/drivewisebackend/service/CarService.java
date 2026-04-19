package com.drivewise.drivewisebackend.service;

import com.drivewise.drivewisebackend.model.Car;
import com.drivewise.drivewisebackend.model.CarResponse;
import com.drivewise.drivewisebackend.model.UserInput;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarService {

    private static final List<Car> carDatabase = new ArrayList<>();

    // Initialize in-memory dataset
    static {
        initializeCarDatabase();
    }

    /**
     * Initialize the in-memory car dataset
     */
    private static void initializeCarDatabase() {
        // Hatchbacks
        carDatabase.add(new Car("h1", "Maruti Swift", 500000, "Petrol", "HatchBack", 3.5, 170, 20.5));
        carDatabase.add(new Car("h2", "Hyundai i20", 550000, "Petrol", "HatchBack", 3.8, 165, 19.0));
        carDatabase.add(new Car("h3", "Tata Nexon EV", 800000, "EV", "HatchBack", 4.2, 200, 150));

        // SUVs
        carDatabase.add(new Car("s1", "Mahindra XUV500", 1200000, "Diesel", "SUV", 4.5, 210, 16.5));
        carDatabase.add(new Car("s2", "Kia Sonet", 1100000, "Petrol", "SUV", 4.3, 200, 18.2));
        carDatabase.add(new Car("s3", "BYD Atto 3", 1500000, "EV", "SUV", 4.8, 220, 180));
        carDatabase.add(new Car("s4", "Hyundai Creta", 1150000, "Diesel", "SUV", 4.4, 205, 17.0));

        // Sedans
        carDatabase.add(new Car("sd1", "Honda Accord", 1800000, "Petrol", "Sedan", 4.6, 155, 16.0));
        carDatabase.add(new Car("sd2", "Skoda Superb", 2200000, "Diesel", "Sedan", 4.9, 160, 15.5));
        carDatabase.add(new Car("sd3", "Tesla Model 3", 3500000, "EV", "Sedan", 5.0, 150, 200));
    }

    /**
     * Get recommendations based on user input
     */
    public List<CarResponse> getRecommendations(UserInput userInput) {
        // Filter cars within budget
        List<Car> availableCars = carDatabase.stream()
                .filter(car -> car.getPrice() <= userInput.getBudget())
                .collect(Collectors.toList());

        if (availableCars.isEmpty()) {
            return new ArrayList<>(); // Return empty if no cars match budget
        }

        // Calculate score for each car
        Map<Car, Double> carScores = new HashMap<>();
        Map<Car, List<String>> carReasons = new HashMap<>();

        for (Car car : availableCars) {
            double score = calculateScore(car, userInput);
            List<String> reasons = buildReason(car, userInput);

            carScores.put(car, score);
            carReasons.put(car, reasons);
        }

        // Sort by score and get top 3
        List<CarResponse> topRecommendations = carScores.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .limit(3)
                .map(entry -> new CarResponse(
                        entry.getKey().getName(),
                        entry.getKey().getPrice(),
                        entry.getKey().getFuelType(),
                        entry.getKey().getVehicleType(),
                        formatReasons(carReasons.get(entry.getKey()))
                ))
                .collect(Collectors.toList());

        return topRecommendations;
    }

    /**
     * Calculate score for a car based on user input and preferences
     */
    private double calculateScore(Car car, UserInput userInput) {
        double score = 0.0;

        // Base score
        score += 100;

        // Monthly distance factor - prioritize mileage
        score += calculateDistanceScore(car, userInput);

        // Road reality factor - prioritize ground clearance for rough roads
        score += calculateRoadScore(car, userInput);

        // Travel group factor - prioritize safety for family
        score += calculateTravelGroupScore(car, userInput);

        // Fuel type matching bonus
        score += calculateFuelTypeBonus(car, userInput);

        // Vehicle type matching bonus
        score += calculateVehicleTypeBonus(car, userInput);

        // Ownership mindset logic
        score += calculateOwnershipMindsetScore(car, userInput);

        return score;
    }

    /**
     * Calculate score based on monthly distance and mileage
     */
    private double calculateDistanceScore(Car car, UserInput userInput) {
        double mileageScore = 0.0;

        if ("high".equalsIgnoreCase(userInput.getMonthlyDistance())) {
            // High distance requires good mileage
            mileageScore = car.getMileage() * 3; // Higher weight
        } else if ("medium".equalsIgnoreCase(userInput.getMonthlyDistance())) {
            mileageScore = car.getMileage() * 2;
        } else {
            mileageScore = car.getMileage();
        }

        return mileageScore;
    }

    /**
     * Calculate score based on road reality and ground clearance
     */
    private double calculateRoadScore(Car car, UserInput userInput) {
        if ("rough".equalsIgnoreCase(userInput.getRoadReality())) {
            // Rough roads need higher ground clearance - heavily prioritize
            return car.getGroundClearance() * 0.5;
        }
        return car.getGroundClearance() * 0.2;
    }

    /**
     * Calculate score based on travel group and safety
     */
    private double calculateTravelGroupScore(Car car, UserInput userInput) {
        if ("family".equalsIgnoreCase(userInput.getTravelGroup())) {
            // Family travel heavily prioritizes safety
            return car.getSafetyRating() * 50;
        }
        return car.getSafetyRating() * 10;
    }

    /**
     * Bonus score if fuel type matches user preference
     */
    private double calculateFuelTypeBonus(Car car, UserInput userInput) {
        if (car.getFuelType().equalsIgnoreCase(userInput.getFuelType())) {
            return 30;
        }
        return 0;
    }

    /**
     * Bonus score if vehicle type matches user preference
     */
    private double calculateVehicleTypeBonus(Car car, UserInput userInput) {
        if (car.getVehicleType().equalsIgnoreCase(userInput.getVehicleType())) {
            return 25;
        }
        return 0;
    }

    /**
     * Calculate score based on ownership mindset
     */
    private double calculateOwnershipMindsetScore(Car car, UserInput userInput) {
        double mindsetScore = 0.0;

        switch (userInput.getOwnershipMindset().toLowerCase()) {
            case "safety":
                mindsetScore = car.getSafetyRating() * 40;
                break;
            case "cost":
                // Lower price is better for cost-conscious buyers
                mindsetScore = (1000000 - car.getPrice()) / 10000;
                break;
            case "performance":
                // Performance bonus - higher mileage treated as performance efficiency
                mindsetScore = car.getMileage() * 2;
                break;
            case "comfort":
                // Safety and mileage indicate comfort
                mindsetScore = (car.getSafetyRating() * 20) + (car.getMileage() * 1.5);
                break;
            default:
                mindsetScore = 0;
        }

        return mindsetScore;
    }

    /**
     * Build reasons for recommendation based on multiple factors
     */
    private List<String> buildReason(Car car, UserInput userInput) {
        List<String> reasons = new ArrayList<>();

        // Check fuel type match
        if (car.getFuelType().equalsIgnoreCase(userInput.getFuelType())) {
            reasons.add("matches your preferred " + car.getFuelType() + " fuel type");
        }

        // Check vehicle type match
        if (car.getVehicleType().equalsIgnoreCase(userInput.getVehicleType())) {
            reasons.add("exactly matches your " + car.getVehicleType() + " preference");
        }

        // Mileage reason
        if ("high".equalsIgnoreCase(userInput.getMonthlyDistance())) {
            if (car.getMileage() > 18) {
                reasons.add("excellent mileage (" + car.getMileage() + " km/l) for high monthly distance");
            } else {
                reasons.add("good mileage (" + car.getMileage() + " km/l) for your needs");
            }
        }

        // Road reality reason
        if ("rough".equalsIgnoreCase(userInput.getRoadReality())) {
            if (car.getGroundClearance() > 200) {
                reasons.add("excellent ground clearance (" + car.getGroundClearance() + "mm) for rough roads");
            } else if (car.getGroundClearance() > 180) {
                reasons.add("good ground clearance (" + car.getGroundClearance() + "mm) for rough terrain");
            }
        }

        // Safety reason
        if ("family".equalsIgnoreCase(userInput.getTravelGroup())) {
            if (car.getSafetyRating() >= 4.5) {
                reasons.add("high safety rating (" + car.getSafetyRating() + "/5) perfect for family");
            } else if (car.getSafetyRating() >= 4.0) {
                reasons.add("good safety rating for family travel");
            }
        }

        // Ownership mindset reasons
        if ("safety".equalsIgnoreCase(userInput.getOwnershipMindset())) {
            reasons.add("strong safety credentials");
        } else if ("cost".equalsIgnoreCase(userInput.getOwnershipMindset())) {
            reasons.add("value for money within budget");
        } else if ("performance".equalsIgnoreCase(userInput.getOwnershipMindset())) {
            reasons.add("reliable performance and efficiency");
        }

        // Default reason if no strong matches
        if (reasons.isEmpty()) {
            reasons.add("well balanced for your needs");
        }

        return reasons;
    }

    /**
     * Format reasons as a readable string
     */
    private String formatReasons(List<String> reasons) {
        if (reasons.isEmpty()) {
            return "well balanced option";
        }
        // Join first 2 reasons with 'and'
        if (reasons.size() == 1) {
            return reasons.get(0);
        }
        return String.join(" and ", reasons.stream().limit(2).collect(Collectors.toList()));
    }
}

