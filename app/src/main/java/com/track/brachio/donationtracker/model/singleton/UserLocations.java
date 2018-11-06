package com.track.brachio.donationtracker.model.singleton;

import com.track.brachio.donationtracker.model.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class UserLocations {
    // static variable single_instance of type AllLocations
    private static UserLocations single_instance;

    // current User
    private List<Location> locations;

    // private constructor restricted to this class itself
    private UserLocations()
    {
        locations = new ArrayList<Location>();
    }

    // static method to create instance of AllLocations class
    public static UserLocations getInstance()
    {
        if (single_instance == null) {
            single_instance = new UserLocations();
        }

        return single_instance;
    }

    public List<Location> getLocations() {
        return Collections.unmodifiableList(locations);
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
