package com.track.brachio.donationtracker.model;

public class Location {
    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private LocationType type;
    private String stringLocationType;
    private String phone;
    private String website;
    private Address address;

    public Location (){

    }

    public Location (String i, String n, double lat, double lon,
                     String t, String ph, String web, Address add){
        id = i;
        name = n;
        latitude = lat;
        longitude = lon;
        type = getTypeByString( t );
        stringLocationType = t;
        phone = ph;
        website = web;
        address = add;
    }

    public String getStringLocationType() {
        return stringLocationType;
    }
    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Address getAddress() {
        return address;
    }

    public LocationType getType() {
        return type;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setType(LocationType type) {
        this.type = type;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    private LocationType getTypeByString(String str){
        switch (str) {
            case "DropOff" : return LocationType.DropOff;
            case "Store" : return LocationType.Store;
            case "Warehouse" : return LocationType.Warehouse;
        }
        return null;
    }



}
