package com.drivewise.drivewisebackend.model;

public class UserInput {
    private int budget;
    private String monthlyDistance; // low, medium, high
    private String drivingPattern; // City, Highway, Mixed
    private String roadReality; // smooth, rough
    private String travelGroup; // solo, family
    private String ownershipMindset; // cost, comfort, safety, performance
    private String fuelType; // Petrol, Diesel, EV
    private String vehicleType; // HatchBack, SUV, Sedan

    // Constructors
    public UserInput() {
    }

    public UserInput(int budget, String monthlyDistance, String drivingPattern, String roadReality,
                     String travelGroup, String ownershipMindset, String fuelType, String vehicleType) {
        this.budget = budget;
        this.monthlyDistance = monthlyDistance;
        this.drivingPattern = drivingPattern;
        this.roadReality = roadReality;
        this.travelGroup = travelGroup;
        this.ownershipMindset = ownershipMindset;
        this.fuelType = fuelType;
        this.vehicleType = vehicleType;
    }

    // Getters and Setters
    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getMonthlyDistance() {
        return monthlyDistance;
    }

    public void setMonthlyDistance(String monthlyDistance) {
        this.monthlyDistance = monthlyDistance;
    }

    public String getDrivingPattern() {
        return drivingPattern;
    }

    public void setDrivingPattern(String drivingPattern) {
        this.drivingPattern = drivingPattern;
    }

    public String getRoadReality() {
        return roadReality;
    }

    public void setRoadReality(String roadReality) {
        this.roadReality = roadReality;
    }

    public String getTravelGroup() {
        return travelGroup;
    }

    public void setTravelGroup(String travelGroup) {
        this.travelGroup = travelGroup;
    }

    public String getOwnershipMindset() {
        return ownershipMindset;
    }

    public void setOwnershipMindset(String ownershipMindset) {
        this.ownershipMindset = ownershipMindset;
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
}

