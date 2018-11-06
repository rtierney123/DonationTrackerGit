package com.track.brachio.donationtracker.model.singleton;

import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.User;

public final class CurrentLocation {
    // static variable single_instance of type CurrentUser
    private static CurrentLocation single_instance;

    // current User
    private Location location;

    // private constructor restricted to this class itself
    private CurrentLocation()
    {
        location = new Location();
    }

    // static method to create instance of CurrentUser class
    public static CurrentLocation getInstance()
    {
        if (single_instance == null) {
            single_instance = new CurrentLocation();
        }

        return single_instance;
    }

    public void setLocation(Location l) {
        location = l;
    }

    public Location getLocation(){
        return location;
    }
}
