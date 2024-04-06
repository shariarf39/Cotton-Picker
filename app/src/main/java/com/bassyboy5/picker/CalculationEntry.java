package com.bassyboy5.picker;

public class CalculationEntry {
    private double width;
    private double yield;
    private double speed;
    private double targetBalesPerHour;
    private double targetSpeed;

    public CalculationEntry() {
        // Default constructor
    }

    public CalculationEntry(double width, double yield, double speed, double targetBalesPerHour, double targetSpeed) {
        this.width = width;
        this.yield = yield;
        this.speed = speed;
        this.targetBalesPerHour = targetBalesPerHour;
        this.targetSpeed = targetSpeed;
    }

    // Setters
    public void setWidth(double width) {
        this.width = width;
    }

    public void setYield(double yield) {
        this.yield = yield;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setTargetBalesPerHour(double targetBalesPerHour) {
        this.targetBalesPerHour = targetBalesPerHour;
    }

    public void setTargetSpeed(double targetSpeed) {
        this.targetSpeed = targetSpeed;
    }

    // Getters
    public double getWidth() {
        return width;
    }

    public double getYield() {
        return yield;
    }

    public double getSpeed() {
        return speed;
    }

    public double getTargetBalesPerHour() {
        return targetBalesPerHour;
    }

    public double getTargetSpeed() {
        return targetSpeed;
    }
}

