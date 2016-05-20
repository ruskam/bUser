package com.example.esr14.signupplay.Model;

import java.util.Arrays;

/**
 * Created by Rustam Kamberov on 20/05/16.
 */
public class Location {

    private double x;
    private double y;
    private String type;
    private String[] coordinates;

    public Location() {
    }

    public Location(double x, double y, String type, String[] coordinates) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.coordinates = coordinates;

    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", type='" + type + '\'' +
                ", coordinates=" + Arrays.toString(coordinates) +
                '}';
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String[] coordinates) {
        this.coordinates = coordinates;
    }


}
