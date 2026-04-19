package com.drivewise.drivewisebackend.model;

public class Car {
    private String id;
    private String name;
    private int price;
    private String fuelType; // Petrol, Diesel, EV
    private String vehicleType; // HatchBack, SUV, Sedan
    private double safetyRating; // 1-5
    private int groundClearance; // in mm
    private double mileage; // km/l or km/charge

    public Car(String id, String name, int price, String fuelType, String vehicleType, 
               double safetyRating, int groundClearance, double mileage) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.fuelType = fuelType;
        this.vehicleType = vehicleType;
        this.safetyRating = safetyRating;
        this.groundClearance = groundClearance;
        this.mileage = mileage;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public double getSafetyRating() {
        return safetyRating;
    }

    public void setSafetyRating(double safetyRating) {
        this.safetyRating = safetyRating;
    }

    public int getGroundClearance() {
        return groundClearance;
    }

    public void setGroundClearance(int groundClearance) {
        this.groundClearance = groundClearance;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }
}

