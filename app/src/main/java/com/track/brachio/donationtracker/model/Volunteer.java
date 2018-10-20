package com.track.brachio.donationtracker.model;

import java.util.ArrayList;

public class Volunteer extends User {
    ArrayList<Location> locations;

    public Volunteer(){
        locations = new ArrayList<>();
    }

    public Volunteer(String firstName, String lastName, String em, String type, ArrayList<String> loc){
        super(firstName, lastName, em, type);
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }
}
