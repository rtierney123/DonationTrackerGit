package com.track.brachio.donationtracker.model.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.track.brachio.donationtracker.ItemEditActivity;
import com.track.brachio.donationtracker.model.Item;
import com.track.brachio.donationtracker.model.singleton.AllItems;
import com.track.brachio.donationtracker.model.ItemType;
/*
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
*/
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Handler for Items
 */
public class FirebaseItemHandler {
    private final FirebaseFirestore db ;

    private final String TAG = "FirebaseItemHandler";

    /**
     * Constructor for FirebaseItemhandler
     * @param db db being passed in
     */
    public FirebaseItemHandler(FirebaseFirestore db){
        this.db = db;
    }
    /**
     * returns all of the items in Items database
     * @return all Items
     */
    public Task getAllItems(){
        // Firestore

        return db.collection("items")
                .get()
                .addOnCompleteListener(task -> {
                    Map<String, Map<String, Item>> map = new LinkedHashMap<>(  );
                    if (task.isSuccessful()) {

                        for (DocumentSnapshot document :
                                Objects.requireNonNull(
                                        task.getResult()))
                            {

                            Log.d(TAG, document.getId() + " => " + document.getData());

                            String key = document.getId();
                            String name = document.getString("name");
                            Date date = document.getDate("date");
                            String locationID = document.getString("locationID");
                            Double cost = document.getDouble( "cost" );
                            String category = document.getString("category");
                            Item item = new Item(Objects.requireNonNull(key),
                                    Objects.requireNonNull(name),
                                    Objects.requireNonNull(date),
                                    Objects.requireNonNull(locationID),
                                    Objects.requireNonNull(cost),
                                    Objects.requireNonNull(category));

                            //convert encoded string to bitmap
                            //String encodedImage = document.getString("picture");
                            //item.setPicture(encodedImage, context);


                            String shortDescription = document.getString("shortDescription");
                            String longDescription = document.getString("longDescription");

                            item.setShortDescription( shortDescription );
                            item.setLongDescription( longDescription );
                            //TODO How to get comment array
                            Map<String, Item> items;
                            if (map.get(locationID) == null){
                                items = new LinkedHashMap<>( );
                                items.put(key, item);
                                map.put(Objects.requireNonNull(locationID), items);
                            } else {
                                items = map.get(locationID);
                                if (items != null){
                                    items.put(key, item);
                                }

                            }
                        }
                        AllItems searched = AllItems.getInstance();
                        searched.setSearchedMap(map);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }

    public Task getPicture(Item item){
        return db.collection("items")
                .document(item.getKey())
                .get( )
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();

                        Blob pic = doc.getBlob("picture");
                        if(pic != null){
                            byte[] byteArray = pic.toBytes();
                            item.bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                        }

                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }


    /**
     * item being added to database
     * @param item item being added
     * @return task being done
     */
    public Task addItem(Item item){

        Map<String, Object> itemMap = new HashMap<>();
        itemMap.put("name", item.getName());
        itemMap.put("date", item.getDateCreated());
        itemMap.put("locationID", item.getLocation());
        itemMap.put("cost", item.getDollarValue());
        ItemType theTypeOfItem = item.getCategory();
        String stringItemType = theTypeOfItem.toString();
        itemMap.put("category", stringItemType);
        itemMap.put("shortDescription", item.getShortDescription());
        itemMap.put("longDescription", item.getLongDescription());

        Bitmap bitmap = item.bitmap;
        if(bitmap != null){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            Blob blob = Blob.fromBytes(byteArray);
            itemMap.put("picture", blob);
        }



        // Add a new document with a generated ID
        return db.collection("items")
                .add(itemMap)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: "
                        + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding user", e));

    }

    /**
     * deletes item from database
     * @param item item being deleted
     * @return task being returned
     */
    public Task deleteItem(Item item) {
        return db.collection("items").document(item.getKey()).delete();
    }

    /**
     * edit item in database
     * @param item item being edited
     * @return current task
     */
    public Task editItem(Item item){
        DocumentReference doc = db.collection("items").document(item.getKey());
        Map<String, Object> itemMap = new HashMap<>();
        itemMap.put("name", item.getName());
        itemMap.put("date", item.getDateCreated());
        itemMap.put("locationID", item.getLocation());
        itemMap.put("cost", item.getDollarValue());
        ItemType itemCategory = item.getCategory();
        String stringItemCategory = itemCategory.toString();
        itemMap.put("category", stringItemCategory);
        itemMap.put("shortDescription", item.getShortDescription());
        itemMap.put("longDescription", item.getLongDescription());



        Bitmap bitmap = item.bitmap;
        if(bitmap != null){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            Blob blob = Blob.fromBytes(byteArray);
            itemMap.put("picture", blob);
        }

        return doc.set(itemMap);

    }

}
