package com.track.brachio.donationtracker.model.singleton;

import com.track.brachio.donationtracker.model.Item;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Information for searched items
 */
public final class SearchedItems {

    // current User
    private Map<String, Map<String, Item>> map;

    // private constructor restricted to this class itself

    /**
     * constructor for SearchedItems
     */
    private SearchedItems()
    {
        map = new LinkedHashMap<>(  );
    }

    // static method to create instance of AllLocations class

    @SuppressWarnings("UtilityClass")
    private static class Single_instanceHolder {
        private static final SearchedItems single_instance = new SearchedItems();
    }

    /**
     * returns instance
     * @return instance of searched items
     */
    public static SearchedItems getInstance()
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
