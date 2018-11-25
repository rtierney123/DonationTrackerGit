package com.track.brachio.donationtracker.model.singleton;

import com.track.brachio.donationtracker.model.User;

public class SelectedUser {
    // current User
    private User user;

    // private constructor restricted to this class itself

    /**
     * Constructor for CurrentUser
     */
    private SelectedUser()
    {
        user = new User();
    }

    // static method to create instance of CurrentUser class

    private static class Single_instanceHolder {
        private static final SelectedUser single_instance = new SelectedUser();
    }

    /**
     * returns instance of user
     * @return instance being returned
     */
    public static SelectedUser getInstance()
    {

        return SelectedUser.Single_instanceHolder.single_instance;
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
