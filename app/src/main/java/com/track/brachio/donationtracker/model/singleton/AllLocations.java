package com.track.brachio.donationtracker.model.singleton;

import com.track.brachio.donationtracker.model.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * AllLocation to put into HashMap
 */
public final class AllLocations {

    private Map<String, Location> locMap;
    // private constructor restricted to this class itself

    /**
     * Constructor for AllLocations
     */
    private AllLocations()
    {
        locMap = new LinkedHashMap<>( );
    }

    // static method to create instance of AllLocations class

    private static class Single_instanceHolder {
        private static final AllLocations single_instance = new AllLocations();
    }

    /**
     * returns instance
     * @return the instance of the location
     */
    public static AllLocations getInstance()
    {

        return Single_instanceHolder.single_instance;
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
    public void setLocationMap(Map<String, Location> map){
        locMap = map;
    }

    /**
     * returns locations as an array
     * @return the map in form of an array
     */
    public ArrayList<Location> getLocationArray(){
        Collection<Location> collect = locMap.values();
        return new ArrayList<>(collect);
    }

}
