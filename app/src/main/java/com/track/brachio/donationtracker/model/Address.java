package com.track.brachio.donationtracker.model;

import android.support.annotation.NonNull;

/**
 * Information Holder for Address
 */
public class Address {
    private String streetAddress;
    private String city;
    private String state;
    private double zip;

    /**
     * Constructor for Address
     * @param address address input
     * @param c city
     * @param s state
     * @param z zip
     */
    public Address(String address, String c, String s, double z){
        streetAddress = address;
        city = c;
        state = s;
        zip = z;
    }

    /**
     * getter - street address
     * @return address
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * getter - city
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * getter - state
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * getter - zip
     * @return zip
     */
    public double getZip() {
        return zip;
    }

    /**
     * setter - address
     * @param streetAddress street address
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     * setter - city
     * @param city city location
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * setter
     * @param state state location
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * setter - cip
     * @param zip zip location
     */
    public void setZip(double zip) {
        this.zip = zip;
    }

    @NonNull
    @Override
    public String toString() {
        return streetAddress + ", " + city + ", " + state + " " + Double.toString(zip);
    }

}
