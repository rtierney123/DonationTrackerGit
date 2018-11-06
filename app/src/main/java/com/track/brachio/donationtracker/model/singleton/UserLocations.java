package com.track.brachio.donationtracker.model.singleton;

import com.track.brachio.donationtracker.model.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Information holder for user locations
 */
public final class UserLocations {
    // static variable single_instance of type AllLocations
    private static UserLocations single_instance;

    // current User
    private List<Location> locations;

    // private constructor restricted to this class itself

    /**
     * constructor for UserLocations
     */
    private UserLocations()
    {
        locations = new ArrayList<Location>();
    }

    // static method to create instance of AllLocations class

    /**
     * returns the current instance
     * @return current instance
     */
    public static UserLocations getInstance()
    {
        if (single_instance == null) {
            single_instance = new UserLocations();
        }

        return single_instance;
    }

    /**
     * returns list of all locations
     * @return list of all locations
     */
    public List<Location> getLocations() {
        return Collections.unmodifiableList(locations);
    }

    /**
     * sets list of locations
     * @param locations location list being set
     */
    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
