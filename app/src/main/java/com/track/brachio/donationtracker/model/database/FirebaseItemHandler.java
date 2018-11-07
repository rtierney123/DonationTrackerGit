package com.track.brachio.donationtracker.model.database;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.track.brachio.donationtracker.model.Item;
import com.track.brachio.donationtracker.model.singleton.SearchedItems;
/*
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
*/
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

/**
 * Handler for Items
 */
@SuppressWarnings("FeatureEnvy")
public class FirebaseItemHandler {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseFirestore mFirestore;
    private final String TAG = "FirebaseItemHandler";
    private Collection<Item> items = new ArrayList<Item>();

    /**
     * returns all of the items in Items database
     * @return all Items
     */
    public Task getAllItems(){
        // Firestore
        mFirestore = FirebaseFirestore.getInstance();
        items.clear();
        Task task = db.collection("items")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Map<String, Map<String, Item>> map = new LinkedHashMap<>(  );
                        if (task.isSuccessful()) {

                            for (DocumentSnapshot document :
                                    Objects.requireNonNull(
                                            task.getResult())) //noinspection TodoComment
                                {

                                Log.d(TAG, document.getId() + " => " + document.getData());

                                String key = document.getId();
                                String name = document.getString("name");
                                Date date = document.getDate("date");
                                String locationID = document.getString("locationID");
                                Double cost = document.getDouble( "cost" );
                                String category = document.getString("category");
                                Item item = new Item(key, name, Objects.requireNonNull(date), locationID, cost, category);

                                //convert encoded string to bitmap
                                String encodedImage = document.getString("picture");
                                item.setPicture( encodedImage );


                                String shortDescription = document.getString("shortDescription");
                                String longDescription = document.getString("longDescription");

                                item.setShortDescription( shortDescription );
                                item.setLongDescription( longDescription );
                                //TODO How to get comment array
                                Map<String, Item> items;
                                if (map.get(locationID) == null){
                                    items = new LinkedHashMap<>( );
                                    items.put(key, item);
                                    map.put(locationID, items);
                                } else {
                                    items = map.get(locationID);
                                    items.put(key, item);
                                }
                            }
                            SearchedItems searched = SearchedItems.getInstance();
                            searched.setSearchedMap(map);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        return task;
    }

    /**
     * gets Item through location
     * @param item item being searched for
     */
    public void getItemByLocation(Item item) {
        mFirestore = FirebaseFirestore.getInstance();
        db.collection("items")
                .whereEqualTo( "locationID", item.getLocation() )
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document :
                                    task.getResult()) {

                                Log.d(TAG, document.getId() + " => " + document.getData());

                                String key = document.getId();
                                String name = document.getString("name");
                                Date date = document.getDate("date");
                                String locationID = document.getString("locationID");
                                Double cost = document.getDouble( "cost" );
                                String category = document.getString("category");
                                Item item = new Item(key, name,
                                        Objects.requireNonNull(date), locationID, cost, category);

                                String encodedPic = document.getString("picture");
                                String shortDescription = document.getString("shortDescription");
                                String longDescription = document.getString("longDescription");
                                //TODO How to get comment array and picture?

                                items.add(item);

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    /**
     * item being added to database
     * @param item item being added
     * @param executor executor for database
     * @return task being done
     */
    public Task addItem(Item item, ExecutorService executor){

        Map<String, Object> itemMap = new HashMap<>();
        itemMap.put("name", item.getName());
        itemMap.put("date", item.getDateCreated());
        itemMap.put("locationID", item.getLocation());
        itemMap.put("cost", item.getDollarValue());
        itemMap.put("category", item.getCategory().toString());
        itemMap.put("shortDescription", item.getShortDescription());
        itemMap.put("longDescription", item.getLongDescription());

        //convert bitmap into string to store in db
        Bitmap bitmap = item.getPicture();
        if (bitmap != null){
            String encoded = item.encodePic();
            itemMap.put("picture", encoded);
        } else {
            itemMap.put("picture", null);
        }


        // Add a new document with a generated ID
        Task task = db.collection("items")
                .add(itemMap)
                .addOnSuccessListener(executor, new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding user", e);
                    }
                });
        return task;

    }

    /**
     * deletes item from database
     * @param item item being deleted
     * @return task being returned
     */
    public Task deleteItem(Item item) {
        Task task = db.collection("items").document(item.getKey()).delete();
        return task;
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
        itemMap.put("category", item.getCategory().toString());
        itemMap.put("shortDescription", item.getShortDescription());
        itemMap.put("longDescription", item.getLongDescription());
        itemMap.put("picture", item.encodePic());
        Task task = doc.set(itemMap);
        return task;
    }

}
