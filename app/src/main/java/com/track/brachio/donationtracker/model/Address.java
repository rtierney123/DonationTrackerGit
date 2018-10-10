package com.track.brachio.donationtracker.model;

public class Address {
    private String streetAddress;
    private String city;
    private String state;
    private double zip;

    public Address(String address, String c, String s, double z){
        streetAddress = address;
        city = c;
        state = s;
        zip = z;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public double getZip() {
        return zip;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(double zip) {
        this.zip = zip;
    }

    public String toString() {
        String address = streetAddress + ", " + city + ", " + state + " " + Double.toString(zip);
        return address;
    }

}
