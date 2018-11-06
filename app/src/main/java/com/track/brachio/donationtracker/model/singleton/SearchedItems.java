package com.track.brachio.donationtracker.model.singleton;

import com.track.brachio.donationtracker.model.Item;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public final class SearchedItems {
    // static variable single_instance of type SearchedItems
    private static SearchedItems single_instance;

    // current User
    private Map<String, Map<String, Item>> map;

    // private constructor restricted to this class itself
    private SearchedItems()
    {
        map = new LinkedHashMap<>(  );
    }

    // static method to create instance of AllLocations class
    public static SearchedItems getInstance()
    {
        if (single_instance == null) {
            single_instance = new SearchedItems();
        }

        return single_instance;
    }

    public Map<String, Map<String, Item>> getSearchedMap() {
        return Collections.unmodifiableMap(map);
    }

    public void setSearchedMap(Map<String, Map<String, Item>> m) {
        map = m;
    }

}
