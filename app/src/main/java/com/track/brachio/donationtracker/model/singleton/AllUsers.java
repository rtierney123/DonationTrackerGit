package com.track.brachio.donationtracker.model.singleton;

import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Holds all the user for the admin to manage
 */
public class AllUsers {
    // current User
    private List<User> users;

    // private constructor restricted to this class itself

    /**
     * constructor for AllUsers
     */
    private AllUsers()
    {
        users = new ArrayList<>();
    }

    // static method to create instance of AllUsers class
    private static class Single_instanceHolder {
        private static final AllUsers single_instance = new AllUsers();
    }

    /**
     * returns the current instance
     * @return current instance
     */
    public static AllUsers getInstance()
    {

        return AllUsers.Single_instanceHolder.single_instance;
    }

    /**
     * returns list of all users
     * @return list of all users
     */
    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    /**
     * sets list of users
     * @param  users list being set
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }
}
