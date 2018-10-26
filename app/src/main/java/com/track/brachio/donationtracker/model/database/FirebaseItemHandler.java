package com.track.brachio.donationtracker.model.database;

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
import com.track.brachio.donationtracker.EditableItemListActivity;
import com.track.brachio.donationtracker.controller.PersistanceManager;
import com.track.brachio.donationtracker.model.Item;
import com.track.brachio.donationtracker.model.singleton.SearchedItems;
/*
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
*/
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.lang.Object;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;


public class FirebaseItemHandler {
    //TODO will complete change to handle item db
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseFirestore mFirestore;
    private String TAG = "FirebaseItemHandler";
    private ArrayList<Item> items = new ArrayList<>();

    public Task getAllItems(){
        // Firestore
        mFirestore = FirebaseFirestore.getInstance();
        items.clear();
        Task task = db.collection("items")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        HashMap<String, HashMap<String, Item>> map = new LinkedHashMap<>(  );
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {

                                Log.d(TAG, document.getId() + " => " + document.getData());

                                String key = document.getId();
                                String name = document.getString("name");
                                Date date = document.getDate("date");
                                String locationID = document.getString("locationID");
                                Double cost = document.getDouble( "cost" );
                                String category = document.getString("category");
                                Item item = new Item(key, name, date, locationID, cost, category);

                                String shortDescript = document.getString("shortDescript");
                                String longDescript = document.getString("longDescript");
                                //TODO How to get comment array and picture?
                                HashMap<String, Item> items;
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

    public void getItembyLocation(Item item) {
        mFirestore = FirebaseFirestore.getInstance();
        db.collection("items")
                .whereEqualTo( "locationID", item.getLocation() )
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {

                                Log.d(TAG, document.getId() + " => " + document.getData());

                                String key = document.getId();
                                String name = document.getString("name");
                                Date date = document.getDate("date");
                                String locationID = document.getString("locationID");
                                Double cost = document.getDouble( "cost" );
                                String category = document.getString("category");
                                Item item = new Item(key, name, date, locationID, cost, category);

                                String shortDescript = document.getString("shortDescript");
                                String longDescript = document.getString("longDescript");
                                //TODO How to get comment array and picture?

                                items.add(item);

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }


    public Task addItem(Item item, ExecutorService executor){

        Map<String, Object> itemMap = new HashMap<>();
        itemMap.put("name", item.getName());
        itemMap.put("date", item.getDateCreated());
        itemMap.put("locationID", item.getLocation());
        itemMap.put("cost", item.getDollarValue());
        itemMap.put("category", item.getCategory().toString());
        itemMap.put("shortDescript", item.getShortDescript());
        itemMap.put("longDescript", item.getLongDescript());

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

    public Task deleteItem(Item item) {
        Task task = db.collection("items").document(item.getKey()).delete();
        return task;
    }

    public Task editItem(Item item){
        DocumentReference doc = db.collection("items").document(item.getKey());
        Map<String, Object> itemMap = new HashMap<>();
        itemMap.put("name", item.getName());
        itemMap.put("date", item.getDateCreated());
        itemMap.put("locationID", item.getLocation());
        itemMap.put("cost", item.getDollarValue());
        itemMap.put("category", item.getCategory().toString());
        itemMap.put("shortDescript", item.getShortDescript());
        itemMap.put("longDescript", item.getLongDescript());

        Task task = doc.set(itemMap);
        return task;
    }

}
