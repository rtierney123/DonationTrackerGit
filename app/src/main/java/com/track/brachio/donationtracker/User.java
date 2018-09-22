package com.track.brachio.donationtracker;

public class User {
    private String _password;
    private String _username;
    private UserType _typeOfUser;

    //for now can create a User[] to hold user data
    //we can then loop through to check
    //from registration page, you create User
    //since no registration page, should we just create a user onCreate?
    public User(String password, String username, UserType typeOfUser) {
        _password = password;
        _username = username;
        _typeOfUser = typeOfUser;
    }

    //reset password
    public void setPassword(String password) {
        _password = password;
    }

    //to know access bounds
    public UserType getTypeOfUser() {
        return _typeOfUser;
    }

    public String getPassword() {
        return _password;
    }

    public String getUsername() {
        return _username;
    }

    public boolean checkUsername(String userEntered) {
        if (userEntered.equals(_username)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPassword(String passwordEntered) {
        if (passwordEntered.equals(_password)) {
            return true;
        } else {
            return false;
        }
    }




}
