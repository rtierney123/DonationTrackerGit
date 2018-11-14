package com.track.brachio.donationtracker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Types of ItemType
 */
public enum ItemType {
    Food,
    Clothes,
    Furniture,
    Sport,
    Electronics,
    Miscellaneous;

    /**
     * returns list of enums
     * @return list of enums
     */
    public static List<String> getArrayList(){
        ItemType[] types = ItemType.values();
        List<String> names = new ArrayList<>();
        for(ItemType s : types){
            names.add(s.toString());
        }
        return names;
    }

//    /**
//     * returns size
//     * @return returns size of enums;
//     */
//    public static int getSize(){
//        ItemType[] types = ItemType.values();
//        return types.length;
//    }
}
