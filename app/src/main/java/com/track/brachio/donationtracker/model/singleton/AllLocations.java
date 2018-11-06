package com.track.brachio.donationtracker.model.singleton;

import com.track.brachio.donationtracker.model.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public final class AllLocations {
    // static variable single_instance of type AllLocations
    private static AllLocations single_instance;

    private HashMap<String, Location> locMap;
    // private constructor restricted to this class itself
    private AllLocations()
    {
        locMap = new LinkedHashMap<>( );
    }

    // static method to create instance of AllLocations class
    public static AllLocations getInstance()
    {
        if (single_instance == null) {
            single_instance = new AllLocations();
        }

        return single_instance;
    }


    public Map<String, Location> getLocationMap(){
        return locMap;
    }

    public void setLocationMap(HashMap<String, Location> map){
        locMap = map;
    }

    public ArrayList<Location> getLocationArray(){
        Collection<Location> collect = locMap.values();
        return new ArrayList<Location>(collect);
    }
}
