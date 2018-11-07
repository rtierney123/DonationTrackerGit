package com.track.brachio.donationtracker.model.singleton;

import com.track.brachio.donationtracker.model.Item;

/**
 * Information holder for CurrentItem
 */
public final class CurrentItem {

    // current User
    private Item item;

    // private constructor restricted to this class itself

    /**
     * Constructor for CurrentItem
     */
    private CurrentItem()
    {
        item = new Item();
    }

    // static method to create instance of CurrentUser class

    private static class Single_instanceHolder {
        private static final CurrentItem single_instance = new CurrentItem();
    }

    /**
     * returns currentInstance being used
     * @return current instance of Item
     */
    public static CurrentItem getInstance()
    {

        return Single_instanceHolder.single_instance;
    }

    /**
     * setter for Item
     * @param i item being set to
     */
    public void setItem(Item i) {
        item = i;
    }

    /**
     * getter for Item
     * @return item being returned
     */
    public Item getItem(){
        return item;
    }
}
