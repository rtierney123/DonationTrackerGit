package com.track.brachio.donationtracker.model.singleton;

import com.track.brachio.donationtracker.model.Location;

/**
 * Singleton for Location
 */
public final class CurrentLocation {
    // static variable single_instance of type CurrentUser
    private static CurrentLocation single_instance;

    // current User
    private Location location;

    // private constructor restricted to this class itself

    /**
     * Constructor for CurrentLocation
     */
    private CurrentLocation()
    {
        location = new Location();
    }

    // static method to create instance of CurrentUser class

    /**
     * Returns the instance of CurrentLocation
     * @return the current instance
     */
    public static CurrentLocation getInstance()
    {
        if (single_instance == null) {
            single_instance = new CurrentLocation();
        }

        return single_instance;
    }

    /**
     * sets location of CurrentLocation
     * @param l location being set to
     */
    public void setLocation(Location l) {
        location = l;
    }

    /**
     * getter - location
     * @return location being returned
     */
    public Location getLocation(){
        return location;
    }
}
