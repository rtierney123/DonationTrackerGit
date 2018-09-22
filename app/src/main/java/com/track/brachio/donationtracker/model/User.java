package com.track.brachio.donationtracker.model;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class User {
    //Should probably add first and last name
    private String userId;
    private String username;
    private String password;
    private String email;
    private UserType userType;
    private @ServerTimestamp Date timestamp;

    public User(FirebaseUser user, String pass, UserType type){
        this.userId = user.getUid();
        this.username = user.getDisplayName();
        this.email = user.getEmail();

        password = pass;
        userType = type;
    }

    public User(String id, String name, String em, String pass, UserType type){
        this.userId = id;
        this.username = name;
        this.email = em;

        password = pass;
        userType = type;
    }

    public User(String id, String name, String em, String pass, String type){
        this.userId = id;
        this.username = name;
        this.email = em;

        password = pass;
        userType = stringToUserType( type );
    }


    public User(){
        userId = "";
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    private UserType stringToUserType(String str){
        UserType ret = UserType.Donator;
        switch(str){
            case "Donator" : ret = UserType.Donator;
            case "Volunteer" : ret = UserType.Volunteer;
            case "Manager" : ret = UserType.Manager;
            case "Admin" : ret = UserType.Admin;
        }
        return ret;
    }
}
