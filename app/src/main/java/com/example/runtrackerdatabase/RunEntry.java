package com.example.runtrackerdatabase;

public class RunEntry {
    private String name;
    private String distance;
    private String time;

    public RunEntry(String name, String distance ,String time) {
        this.name = name;
        this.distance = distance;
        this.time = time;
    }

    public String toString() {
        return name + ", " + distance + ", " + time + " (hr: min: sec)";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
