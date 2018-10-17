package com.track.brachio.donationtracker.model.singleton;

import com.track.brachio.donationtracker.model.Item;
import com.track.brachio.donationtracker.model.Location;

public class CurrentItem {
    
    // static variable single_instance of type CurrentUser
    private static CurrentItem single_instance = null;

    // current User
    private Item item;

    // private constructor restricted to this class itself
    private CurrentItem()
    {
        item = new Item();
    }

    // static method to create instance of CurrentUser class
    public static CurrentItem getInstance()
    {
        if (single_instance == null)
            single_instance = new CurrentItem();

        return single_instance;
    }

    public void setItem(Item i) {
        item = i;
    }

    public Item getItem(){
        return item;
    }
}
