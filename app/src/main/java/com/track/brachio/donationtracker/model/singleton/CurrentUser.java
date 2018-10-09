package com.track.brachio.donationtracker.model.singleton;

import com.track.brachio.donationtracker.model.User;

public class CurrentUser {
    // static variable single_instance of type CurrentUser
    private static CurrentUser single_instance = null;

    // current User
    private User user;

    // private constructor restricted to this class itself
    private CurrentUser()
    {
        user = new User();
    }

    // static method to create instance of CurrentUser class
    public static CurrentUser getInstance()
    {
        if (single_instance == null)
            single_instance = new CurrentUser();

        return single_instance;
    }

    public void setUser(User u) {
        user = u;
    }

    public User getUser(){
        return user;
    }
}
