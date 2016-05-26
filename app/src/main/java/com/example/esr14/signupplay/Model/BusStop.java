package com.example.esr14.signupplay.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


/**
 * Created by Rustam Kamberov on 01/05/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusStop{

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusStop busStop = (BusStop) o;

        if (id != null ? !id.equals(busStop.id) : busStop.id != null) return false;
        if (stopName != null ? !stopName.equals(busStop.stopName) : busStop.stopName != null)
            return false;
        if (location != null ? !location.equals(busStop.location) : busStop.location != null)
            return false;
        if (lines != null ? !lines.equals(busStop.lines) : busStop.lines != null) return false;
        if (schedule != null ? !schedule.equals(busStop.schedule) : busStop.schedule != null)
            return false;
        return _links != null ? _links.equals(busStop._links) : busStop._links == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (stopName != null ? stopName.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (lines != null ? lines.hashCode() : 0);
        result = 31 * result + (schedule != null ? schedule.hashCode() : 0);
        result = 31 * result + (_links != null ? _links.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BusStop{" +
                "id='" + id + '\'' +
                ", stopName='" + stopName + '\'' +
                ", location=" + location +
                ", lines=" + lines +
                ", schedule=" + schedule +
                ", _links='" + _links + '\'' +
                '}';
    }
}
