package com.track.brachio.donationtracker;

public class User {
    private String password;
    private String username;
    private UserType typeOfUser;

    //for now can create a User[] to hold user data
    //we can then loop through to check
    //from registration page, you create User
    //since no registration page, should we just create a user onCreate?
    public User(String password, String username, UserType typeOfUser) {
        this.password = password;
        this.username = username;
        this.typeOfUser = typeOfUser;
    }

    //reset password
    public void setPassword(String password) {
        this.password = password;
    }

    //to know access bounds
    public UserType getTypeOfUser {
        return typeOfUser;
    }

    public boolean checkUsername(String userEntered) {
        if (userEntered.equals(username)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPassword(String passwordEntered) {
        if (passwordEntered.equals(password)) {
            return true;
        } else {
            return false;
        }
    }




}
