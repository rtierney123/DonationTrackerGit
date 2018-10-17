package com.track.brachio.donationtracker.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Date;

public class Item {
    private Date dateCreated;
    private Location location;
    private String shortDescript;
    private String longDescript;
    private double dollarValue;
    private String category;
    private ArrayList<String> comments;
    private Bitmap picture;

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setShortDescript(String shortDescript) {
        this.shortDescript = shortDescript;
    }

    public void setLongDescript(String longDescript) {
        this.longDescript = longDescript;
    }

    public void setDollarValue(double dollarValue) {
        this.dollarValue = dollarValue;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Location getLocation() {
        return location;
    }

    public String getShortDescript() {
        return shortDescript;
    }

    public String getLongDescript() {
        return longDescript;
    }

    public double getDollarValue() {
        return dollarValue;
    }

    public String getCategory() {
        return category;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public Bitmap getPicture() {
        return picture;
    }

}
