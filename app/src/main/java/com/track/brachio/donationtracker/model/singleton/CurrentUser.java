package com.track.brachio.donationtracker.model.singleton;

import com.track.brachio.donationtracker.model.User;

/**
 * Singleton for User
 */
public final class CurrentUser {

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

    @SuppressWarnings("UtilityClass")
    private static class Single_instanceHolder {
        private static final CurrentUser single_instance = new CurrentUser();
    }

    /**
     * returns instance of user
     * @return instance being returned
     */
    public static CurrentUser getInstance()
    {

        return Single_instanceHolder.single_instance;
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
