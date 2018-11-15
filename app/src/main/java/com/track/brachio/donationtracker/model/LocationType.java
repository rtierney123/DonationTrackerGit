package com.track.brachio.donationtracker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum of different LocationTypes
 */
public enum LocationType {
    DropOff,
    Store,
    Warehouse;

    public static List<String> getArrayList(){
        LocationType[] types = LocationType.values();
        List<String> names = new ArrayList<>();
        for(LocationType s : types){
            names.add(s.toString());
        }
        return names;
    }
}
