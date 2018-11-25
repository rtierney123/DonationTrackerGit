package com.track.brachio.donationtracker.model.singleton;

import com.track.brachio.donationtracker.model.Item;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Information for searched items
 */
public final class AllItems {

    // current User
    private Map<String, Map<String, Item>> map;

    // private constructor restricted to this class itself

    /**
     * constructor for SearchedItems
     */
    private AllItems()
    {
        map = new LinkedHashMap<>(  );
    }

    // static method to create instance of AllLocations class

    private static class Single_instanceHolder {
        private static final AllItems single_instance = new AllItems();
    }

    /**
     * returns instance
     * @return instance of searched items
     */
    public static AllItems getInstance()
    {

        return Single_instanceHolder.single_instance;
    }

    /**
     * returns searches map
     * @return map of searches
     */
    public Map<String, Map<String, Item>> getSearchedMap() {
        return Collections.unmodifiableMap(map);
    }

    /**
     * sets map of searches
     * @param m new map
     */
    public void setSearchedMap(Map<String, Map<String, Item>> m) {
        map = m;
    }

}
