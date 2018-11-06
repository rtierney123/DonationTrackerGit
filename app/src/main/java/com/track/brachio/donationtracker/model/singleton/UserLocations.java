package com.track.brachio.donationtracker.model.singleton;

import com.track.brachio.donationtracker.model.Location;

import java.util.ArrayList;
import java.util.List;

public final class UserLocations {
    // static variable single_instance of type AllLocations
    private static UserLocations single_instance = null;

    // current User
    private ArrayList<Location> locations;

    // private constructor restricted to this class itself
    private UserLocations()
    {
        locations = new ArrayList<Location>();
    }

    // static method to create instance of AllLocations class
    public static UserLocations getInstance()
    {
        if (single_instance == null)
            single_instance = new UserLocations();

        return single_instance;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }
}
