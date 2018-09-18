package com.track.brachio.donationtracker.model;

public class User {
    private String username;
    private String password;
    private String email;
    private UserType userType;

    public User(String user, String pass, String em, UserType type){
        username = user;
        password = pass;
        email = em;
        userType = type;
    }

    public User(){
        username = "";
        password = "";
        email = "";
        userType = UserType.Donator;
    }

    public void setUsername(String user) {
        username = user;
    }

    public String getUsername(){
        return username;
    }

    public void setPassword(String pass){
        password = pass;
    }

    public String getPassword(){
        return password;
    }

    public void setEmail(String em){
        email = em;
    }

    public String getEmail(){
        return email;
    }

    public void setUserType(UserType type){
        userType = type;
    }

    public UserType getUserType(){
        return userType;
    }
}
