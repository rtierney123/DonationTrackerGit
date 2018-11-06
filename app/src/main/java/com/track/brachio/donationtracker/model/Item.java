package com.track.brachio.donationtracker.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Arrays;

public class Item {
    private String key;
    private String name;
    private Date dateCreated;
    private String locationID;
    private String shortDescription;
    private String longDescription;
    private double dollarValue;
    private ItemType category;
    private static final List<ItemType> legalItemTypes = Arrays.asList(ItemType.values());
    private ArrayList<String> comments = new ArrayList();
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

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
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
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.PNG, 100, out);
        this.picture = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public String getLocation() {
        return locationID;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
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

    public void setPicture(String encodedImage){
        if(encodedImage != null){
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            picture = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }

    }

    public String encodePic(){
        if (picture != null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            picture.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        } else {
            return null;
        }
    }

}
