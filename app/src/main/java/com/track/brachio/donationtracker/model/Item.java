package com.track.brachio.donationtracker.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Arrays;

/**
 * Information holder for Item
 */
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
    private final Collection<String> comments = new ArrayList<>();
    private File picture;
    public Bitmap bitmap;
    private static final int compressed = 50;

    /**
     * Constructor for Item
     */
    public Item() {

    }

    /**
     * Constructor for item
     * @param k key
     * @param n name
     * @param d date created
     * @param l locationID
     * @param val dollarValue
     * @param cat category
     */
    public Item(String k, String n, Date d, String l, double val, String cat){
        key = k;
        name = n;
        dateCreated = (Date) d.clone();
        locationID = l;
        dollarValue = val;
        category = stringToItemType(cat);
    }

    /**
     * returns legal user types in a list
     * @return list of legal user types
     */
    public static List<ItemType> getLegalItemTypes() {
        return Collections.unmodifiableList(legalItemTypes);
    }

    /**
     * finds Item by the position
     * @param code code of ItemType
     * @return position
     */
    public static int findItemTypePosition(ItemType code) {
        int i = 0;
        while (i < legalItemTypes.size()) {
            if (code != null) {
                if (code.equals(legalItemTypes.get(i))) {
                    return i;
                }
            }
            i++;
        }
        return 0;
    }

    /**
     * getter - key
     * @return key
     */
    public String getKey() {
        return key;
    }

//    /**
//     * setter - key
//     * @param key key being set
//     */
//    public void setKey(String key) {
//        this.key = key;
//    }

    /**
     * getter - name
     * @return name
     */
    public String getName() {
        return name;
    }

//    /**
//     * Setter - name
//     * @param name name being set
//     */
//    public void setName(String name) {
//        this.name = name;
//    }

//    /**
//     * setter - date
//     * @param dateCreated date being set
//     */
//    public void setDateCreated(Date dateCreated) {
//        this.dateCreated = (Date) dateCreated.clone();
//    }

    /**
     * setter - location
     * @param locationID location being set
     */
    public void setLocation(String locationID) {
        this.locationID = locationID;
    }

    /**
     * setter - short description
     * @param shortDescription description being set
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * setter - long description
     * @param longDescription description being set
     */
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    /**
     * setter - dollar value
     * @param dollarValue dollar being set
     */
    public void setDollarValue(double dollarValue) {
        this.dollarValue = dollarValue;
    }

    /**
     * setter - category
     * @param category category being set
     */
    public void setCategory(ItemType category) {
        this.category = category;
    }

//    /**
//     * setter - comments
//     * @param comments comments being set
//     */
//    public void setComments(ArrayList<String> comments) {
//        this.comments = comments;
//    }

    /**
     * adds comments
     * @param comment comment being add to list
     */
    public void addComment(String comment) {
        comments.add(comment);
    }

    /**
     * setter - Picture
     * @param file picture being set
     */
    public void setPicture(File file) {
        picture = file;
    }

    /**
     * getter - date
     * @return date
     */
    public Date getDateCreated() {
        if(dateCreated != null){
            return (Date) dateCreated.clone();
        } else {
            return null;
        }
    }

    /**
     * getter -location
     * @return location
     */
    public String getLocation() {
        return locationID;
    }

    /**
     * getter - short description
     * @return short description
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * getter - long description
     * @return long description
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     * getter - dollar value
     * @return dollar value
     */
    public double getDollarValue() {
        return dollarValue;
    }

    /**
     * getter - category
     * @return category
     */
    public ItemType getCategory() {
        return category;
    }

//    /**
//     * getter - comments
//     * @return comments
//     */
//    public ArrayList<String> getComments() {
//        return comments;
//    }

    /**
     * getter - picture
     * @return picture
     */
    public File getPicture() {
        return picture;
    }

    /**
     * gets ItemType by string
     * @param str string being searched for
     * @return ItemType of str
     */
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


    /**
     * setter - picture
     * @param encodedByte the encoded Image of image
     */
    /*
    public void setPicture(List<Byte> encodedByte) {
            try {
                byte[] array = new byte[encodedByte.size()];
                for(int i = 0; i < encodedByte.size(); i++){
                    array[i] = encodedByte.get(i);
                }
                picture = BitmapFactory.decodeByteArray( array, 0, array.length );
            } catch (Exception e) {
                e.getMessage();
            }
    }
    */




    /**
     * encodes picture
     * @return of encoded pic
     */
    /*
    public List<Byte> encodePic(){

        if (picture != null) {
            //ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ByteArrayOutputStream base = new ByteArrayOutputStream();
            picture.compress( Bitmap.CompressFormat.JPEG, 100, base );
            List<Byte> list = new ArrayList<>( );
            byte[] array = base.toByteArray();
            for (byte b : array){
                list.add(b);
            }
            return list;
        } else {
            return null;
        }

    }
    */


}
