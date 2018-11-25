package com.track.brachio.donationtracker.model.singleton;

import com.track.brachio.donationtracker.model.Location;

/**
 * Singleton for Location
 */
public final class SelectedLocation {

    // current User
    private Location location;

    // private constructor restricted to this class itself

    /**
     * Constructor for CurrentLocation
     */
    private SelectedLocation()
    {
        location = new Location();
    }

    // static method to create instance of CurrentUser class

    private static class Single_instanceHolder {
        private static final SelectedLocation single_instance = new SelectedLocation();
    }

    /**
     * Returns the instance of CurrentLocation
     * @return the current instance
     */
    public static SelectedLocation getInstance()
    {

        return Single_instanceHolder.single_instance;
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
