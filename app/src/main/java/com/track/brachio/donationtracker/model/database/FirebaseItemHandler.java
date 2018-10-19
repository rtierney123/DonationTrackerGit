package com.track.brachio.donationtracker.model.database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.track.brachio.donationtracker.model.Item;
import com.track.brachio.donationtracker.model.User;
/*
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
*/
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FirebaseItemHandler {
    //TODO will complete change to handle item db
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseFirestore mFirestore;
    private String TAG = "FirebaseItemHandler";
    private ArrayList<Item> items;

    public void getAllItems(){
        // Firestore
        mFirestore = FirebaseFirestore.getInstance();
        db.collection("items")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {

                                Log.d(TAG, document.getId() + " => " + document.getData());

                                Date date = document.getDate("date");
                                String locationID = document.getString("locationID");
                                Double cost = document.getDouble( "cost" );
                                String category = document.getString("category");
                                Item item = new Item(date, locationID, cost, category);

                                String shortDescript = document.getString("shortDescript");
                                String longDescript = document.getString("longDescript");
                                //TODO How to get array and picture?

                                items.add(item);

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }


    public void addUser(User user){
        /*
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("username", user.getUsername());
        userMap.put("password", user.getPassword());
        userMap.put("email", user.getEmail());
        userMap.put("usertype", user.getUserType().name());

        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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
                */
    }
}
