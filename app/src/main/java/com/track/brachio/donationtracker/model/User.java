package com.track.brachio.donationtracker.model;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class User {
    //Should probably add first and last name
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private UserType userType;
    private @ServerTimestamp Date timestamp;

    public User(FirebaseUser user, UserType type){
        this.userId = user.getUid();
        this.email = user.getEmail();
        userType = type;
    }

    public User(String firstName, String lastName, String em, UserType type){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = em;
        userType = type;
    }

    public User(String firstName, String lastName, String em, String type){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = em;
        userType = stringToUserType( type );
    }


    public User(){
        userId = "";
        email = "";
        firstName= "";
        lastName = "";
        userType = UserType.Donator;
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

    public String getFirstname() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastName;
    }

    public void setLastName(String lastNameName) {
        this.lastName = lastName;
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
