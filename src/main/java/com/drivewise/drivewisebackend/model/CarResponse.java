package com.drivewise.drivewisebackend.model;

public class CarResponse {
    private String name;
    private int price;
    private String fuelType;
    private String vehicleType;
    private String reason;

    // Constructors
    public CarResponse() {
    }

    public CarResponse(String name, int price, String fuelType, String vehicleType, String reason) {
        this.name = name;
        this.price = price;
        this.fuelType = fuelType;
        this.vehicleType = vehicleType;
        this.reason = reason;
    }

    // Getters and Setters
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

