package com.track.brachio.donationtracker.model.singleton;

import com.track.brachio.donationtracker.model.User;

/**
 * Singleton for User
 */
public final class CurrentUser {
    // static variable single_instance of type CurrentUser
    private static CurrentUser single_instance;

    // current User
    private User user;

    // private constructor restricted to this class itself

    /**
     * Constructor for CurrentUser
     */
    private CurrentUser()
    {
        user = new User();
    }

    // static method to create instance of CurrentUser class

    /**
     * returns instance of user
     * @return instance being returned
     */
    public static CurrentUser getInstance()
    {
        if (single_instance == null) {
            single_instance = new CurrentUser();
        }

        return single_instance;
    }

    /**
     * setter - user
     * @param u user being set to
     */
    public void setUser(User u) {
        user = u;
    }

    /**
     * getter - user
     * @return current user
     */
    public User getUser(){
        return user;
    }
}
