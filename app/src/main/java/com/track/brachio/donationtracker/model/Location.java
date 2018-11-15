package com.track.brachio.donationtracker.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Information holder for Location
 */
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
    private static final List<LocationType> legalLocationTypes = Arrays.asList(LocationType.values());

    /**
     * Constructor for Location
     */
    public Location (){

    }

    /**
     * Constructor for Location
     * @param i id
     * @param n name
     * @param lat latitude
     * @param lon longitude
     * @param t type
     * @param ph phone
     * @param web website
     * @param add address
     */
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

    /**
     * getter - location type
     * @return location type
     */
    public String getStringLocationType() {
        return stringLocationType;
    }

    /**
     * getter - id
     * @return id
     */
    public String getId(){
        return id;
    }

//    /**
//     * setter - id
//     * @param id id being set
//     */
//    public void setId(String id){
//        this.id = id;
//    }

    /**
     * getter - name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * getter - latitude
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * getter - longitude
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * getter - address
     * @return address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * getter - location type
     * @return location type
     */
    public LocationType getType() {
        return type;
    }

    /**
     * getter- phone
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * getter - website
     * @return website
     */
    public String getWebsite() {
        return website;
    }

//    /**
//     * setter - name
//     * @param name name being set
//     */
//    public void setName(String name) {
//        this.name = name;
//    }

//    /**
//     * setter - latitude
//     * @param latitude latitude being set
//     */
//    public void setLatitude(double latitude) {
//        this.latitude = latitude;
//    }

//    /**
//     * setter- longitude
//     * @param longitude longitude being set
//     */
//    public void setLongitude(double longitude) {
//        this.longitude = longitude;
//    }

//    /**
//     * setter - address
//     * @param address address being set
//     */
//    public void setAddress(Address address) {
//        this.address = address;
//    }

//    /**
//     * setter - Type
//     * @param type type being set
//     */
//    public void setType(LocationType type) {
//        this.type = type;
//    }

//    /**
//     * setter - phone
//     * @param phone phone being set
//     */
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }

//    /**
//     * setter - website
//     * @param website website being set
//     */
//    public void setWebsite(String website) {
//        this.website = website;
//    }

    /**
     * returns type from inputted string
     * @param str str looking for LocationType
     * @return LocationType being found from str
     */
    private LocationType getTypeByString(String str){
        switch (str) {
            case "DropOff" : return LocationType.DropOff;
            case "Store" : return LocationType.Store;
            case "Warehouse" : return LocationType.Warehouse;
        }
        return null;
    }
    public static List<LocationType> getLegalLocationTypes() {
        return Collections.unmodifiableList(legalLocationTypes);
    }

    /**
     * finds Item by the position
     * @param code code of ItemType
     * @return position
     */
    public static int findLocationTypePosition(LocationType code) {
        int i = 0;
        while (i < legalLocationTypes.size()) {
            if (code != null) {
                if (code.equals(legalLocationTypes.get(i))) {
                    return i;
                }
            }
            i++;
        }
        return 0;
    }


}
