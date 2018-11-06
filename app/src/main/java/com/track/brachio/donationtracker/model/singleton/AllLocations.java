package com.track.brachio.donationtracker.model.singleton;

import com.track.brachio.donationtracker.model.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * AllLocation to put into HashMap
 */
public final class AllLocations {
    // static variable single_instance of type AllLocations
    private static AllLocations single_instance;

    private HashMap<String, Location> locMap;
    // private constructor restricted to this class itself

    /**
     * Constructor for AllLocations
     */
    private AllLocations()
    {
        locMap = new LinkedHashMap<>( );
    }

    // static method to create instance of AllLocations class

    /**
     * returns instance
     * @return the instance of the location
     */
    public static AllLocations getInstance()
    {
        if (single_instance == null) {
            single_instance = new AllLocations();
        }

        return single_instance;
    }

    /**
     * returns locationMap
     * @return map of locations
     */
    public Map<String, Location> getLocationMap(){
        return Collections.unmodifiableMap(locMap);
    }

    /**
     * setter for locationMap
     * @param map map being inputted
     */
    public void setLocationMap(HashMap<String, Location> map){
        locMap = map;
    }

    /**
     * returns locations as an array
     * @return the map in form of an array
     */
    public ArrayList<Location> getLocationArray(){
        Collection<Location> collect = locMap.values();
        return new ArrayList<Location>(collect);
    }
}
