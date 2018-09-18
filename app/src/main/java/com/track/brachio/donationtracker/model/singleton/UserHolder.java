package com.track.brachio.donationtracker.model.singleton;

import com.track.brachio.donationtracker.model.User;

import java.util.ArrayList;

public class UserHolder {
    //This class is used to hold all users from FireBase callback

    // static variable single_instance of type UserHolder
    private static UserHolder single_instance = null;

    // current User
    private ArrayList<User> users;

    // private constructor restricted to this class itself
    private UserHolder()
    {
        users = new ArrayList<>();
    }

    // static method to create instance of UserHolder class
    public static UserHolder getInstance()
    {
        if (single_instance == null)
            single_instance = new UserHolder();

        return single_instance;
    }

    public ArrayList getUsers() {
        return users;
    }

    public void clear(){
        users.clear();
    }
}
