package com.track.brachio.donationtracker.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Arrays;

public class Item {
    private String key;
    private String name;
    private Date dateCreated;
    private String locationID;
    private String shortDescript;
    private String longDescript;
    private double dollarValue;
    private ItemType category;
    private static List<ItemType> legalItemTypes = Arrays.asList(ItemType.values());
    private ArrayList<String> comments;
    private Bitmap picture;

    public Item() {

    }

    public Item(String k, String n, Date d, String l, double val, String cat){
        key = k;
        name = n;
        dateCreated = d;
        locationID = l;
        dollarValue = val;
        category = stringToItemType(cat);
    }

    public static List<ItemType> getLegalItemTypes() {
        return legalItemTypes;
    }

    public static int findItemTypePosition(ItemType code) {
        int i = 0;
        while (i < legalItemTypes.size()) {
            if (code != null) {
                if (code.equals(legalItemTypes.get(i))) {
                    return i;
                }
                i++;
            }
        }
        return 0;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setLocation(String locationID) {
        this.locationID = locationID;
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

    public void setCategory(ItemType category) {
        this.category = category;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public void addComment(String comment) {
        comments.add(comment);
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public String getLocation() {
        return locationID;
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

    public ItemType getCategory() {
        return category;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public Bitmap getPicture() {
        return picture;
    }

    private ItemType stringToItemType(String str) {
        switch (str) {
            case "Food":
                return ItemType.Food;
            case "Clothes":
                return ItemType.Clothes;
            case "Furniture":
                return ItemType.Furniture;
            case "Sport":
                return ItemType.Sport;
            case "Electronics":
                return ItemType.Electronics;
            case "Miscellaneous":
                return ItemType.Miscellaneous;
        }
        return null;
    }

}
