package com.example.esr14.signupplay.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


/**
 * Created by Rustam Kamberov on 01/05/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusStop {

    private String id;
    private String stopName;
    private Location location;
    List<String> lines;
    List<String> schedule;

    public BusStop() {

    }

    @JsonIgnore
    private String _links;

    public BusStop(String stopName, Location location, List<String> lines, List<String> schedule) {
        this.stopName = stopName;
        this.location = location;
        this.lines = lines;
        this.schedule = schedule;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public List<String> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<String> schedule) {
        this.schedule = schedule;
    }
}
