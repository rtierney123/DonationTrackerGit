package com.track.brachio.donationtracker.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public enum ItemType {
    Food,
    Clothes,
    Furniture,
    Sport,
    Electronics,
    Miscellaneous;

    public static ArrayList<String> getArrayList(){
        ItemType[] types = ItemType.values();
        ArrayList<String> names = new ArrayList();
        for(ItemType s : types){
            names.add(s.toString());
        }
        return names;
    }

    public static int getSize(){
        ItemType[] types = ItemType.values();
        return types.length;
    }
}
