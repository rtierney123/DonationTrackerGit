package com.track.brachio.donationtracker.model.singleton;

import com.track.brachio.donationtracker.model.Location;

import java.util.ArrayList;

public class AllLocations {
    // static variable single_instance of type AllLocations
    private static AllLocations single_instance = null;

    // current User
    private ArrayList<Location> locations;

    // private constructor restricted to this class itself
    private AllLocations()
    {
        locations = new ArrayList<Location>();
    }

    // static method to create instance of AllLocations class
    public static AllLocations getInstance()
    {
        if (single_instance == null)
            single_instance = new AllLocations();

        return single_instance;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }
}
