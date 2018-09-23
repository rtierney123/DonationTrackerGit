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
import com.track.brachio.donationtracker.model.User;
import com.track.brachio.donationtracker.model.singleton.UserHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseItemHandler {
    //will complete change to handle item db
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseFirestore mFirestore;
    private String TAG = "FirebaseItemHandler";

    public ArrayList<User> getUsers(){
        // Firestore
        mFirestore = FirebaseFirestore.getInstance();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        UserHolder holder = UserHolder.getInstance();
                        ArrayList<User> users = holder.getUsers();
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                /*
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String userId = document.getId();
                                String username = document.getString("username");
                                String email = document.getString("email");
                                String password = document.getString("password");
                                String userType = document.getString("usertype");
                                User user = new User(userId, username, email, password, userType);
                                users.add(user);
                                */
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        return UserHolder.getInstance().getUsers();
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
