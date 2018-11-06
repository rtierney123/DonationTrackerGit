package com.track.brachio.donationtracker.model;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import java.util.Date;

@SuppressWarnings("SpellCheckingInspection")
public class User {
    //Should probably add first and last name
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private UserType userType;
    private static final List<UserType> legalUserTypes = Arrays.asList(UserType.values());
    @ServerTimestamp
    private Date timestamp;
    private ArrayList<String> locationIDs;

    public User(UserInfo user, UserType type){
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

    public User(String firstName, String lastName, String em, String type, ArrayList<String> ids){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = em;
        userType = stringToUserType( type );
        locationIDs = ids;
    }



    public User(){
        userId = "";
        email = "";
        firstName= "";
        lastName = "";
        userType = UserType.Donator;
    }

    public static List<UserType> getLegalUserTypes() {
        return Collections.unmodifiableList(legalUserTypes);
    }
    public static int findUserTypePosition(UserType code) {
        int i = 0;
        while (i < legalUserTypes.size()) {
            if (code.equals(legalUserTypes.get(i))) {
                return i;
            }
            ++i;
        }
        return 0;
    }

    public ArrayList<String> getLocations() {
        return locationIDs;
    }

    public void setLocations(ArrayList<String> locations) {
        this.locationIDs = locations;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private UserType stringToUserType(String str){
        switch(str){
            case "Donator" : return UserType.Donator;
            case "Volunteer" : return UserType.Volunteer;
            case "Manager" : return UserType.Manager;
            case "Admin" : return UserType.Admin;
        }
        return null;
    }

    public boolean isNull() {
        return (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || (userType == null));
    }
}
