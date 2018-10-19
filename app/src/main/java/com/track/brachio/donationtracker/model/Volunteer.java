package com.track.brachio.donationtracker.model;

import java.util.ArrayList;

public class Volunteer extends User {
    ArrayList<Location> locations;

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }
}
