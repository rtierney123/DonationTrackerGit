package com.track.brachio.donationtracker.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import java.util.Date;

/**
 * Information holder for User
 */
public class User {
    private String key;
    private String firstName;
    private String lastName;
    private String email;
    private UserType userType;
    private static final List<UserType> legalUserTypes = Arrays.asList(UserType.values());
    @ServerTimestamp
    private Date timestamp;

//    /**
//     * Constructor for User
//     * @param user user being entered
//     * @param type type being entered
//     */
//    public User(UserInfo user, UserType type){
//        this.userId = user.getUid();
//        this.email = user.getEmail();
//        userType = type;
//    }

    /**
     * Constructor for User
     * @param firstName first name of user
     * @param lastName last name of user
     * @param em email of user
     * @param type type of user
     */
    public User(String firstName, String lastName, String em, UserType type){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = em;
        userType = type;
    }

    /**
     * Constructor for User
     * @param firstName first name of user
     * @param lastName last name of user
     * @param em email of user
     * @param type type of user
     */
    public User(String firstName, String lastName, String em, String type){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = em;
        userType = stringToUserType( type );
    }

//    /**
//     * Constructor for User
//     * @param firstName first name of user
//     * @param lastName last name of user
//     * @param em email of user
//     * @param type type of user
//     * @param ids id of user
//     */
//    public User(String firstName, String lastName, String em, String type, List<String> ids){
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = em;
//        userType = stringToUserType( type );
//    }


    /**
     * constructor for user
     */
    public User(){
        //Should probably add first and last name
        //String userId = "";
        email = "";
        firstName= "";
        lastName = "";
        userType = UserType.Guest;
    }

    /**
     * returns the legal user types
     * @return list of user types
     */
    public static List<UserType> getLegalUserTypes() {
        return Collections.unmodifiableList(legalUserTypes);
    }



    /**
     * setter - email
     * @param em email being set
     */
    public void setEmail(String em){
        email = em;
    }


    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    /**
     * getter - email
     * @return email
     */
    public String getEmail(){
        return email;
    }

//    /**
//     * setter - UserType
//     */
//    public void setUserType(UserType type){
//        userType = type;
//    }

    /**
     * getter - UserType
     * @return userType
     */
    public UserType getUserType(){
        return userType;
    }

    /**
     * getter - timestamp
     * @return timestamp
     */
    public Date getTimestamp() {
        return (Date) timestamp.clone();
    }

    /**
     * setter - timestamp
     * @param timestamp sets timestamp
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = (Date) timestamp.clone();
    }

    /**
     * getter - first name
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * setter - first name
     * @param firstName first name being set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * getter - last name
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * setter - last name
     * @param lastName last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public void setUserType(UserType userType) {
        this.userType = userType;
    }



    /**
     * converts string to UserType
     * @param str string being converted
     * @return UserType corresponding to the string
     */
    private UserType stringToUserType(String str){
        if (str != null){
            switch(str){
                case "Donator" : return UserType.Donator;
                case "Volunteer" : return UserType.Volunteer;
                case "Manager" : return UserType.Manager;
                case "Admin" : return UserType.Admin;
            }
        }
        return null;
    }

    /**
     * returns whether or not User is null
     * @return true if null
     */
    public boolean isNull() {
        return (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || (userType == null));
    }
}
