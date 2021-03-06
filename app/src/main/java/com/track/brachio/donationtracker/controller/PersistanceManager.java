package com.track.brachio.donationtracker.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.track.brachio.donationtracker.MainActivity;
import com.track.brachio.donationtracker.model.Item;
import com.track.brachio.donationtracker.model.User;
import com.track.brachio.donationtracker.model.UserType;
import com.track.brachio.donationtracker.model.database.FirebaseItemHandler;
import com.track.brachio.donationtracker.model.database.FirebaseLocationHandler;
import com.track.brachio.donationtracker.model.database.FirebaseUserHandler;
import com.track.brachio.donationtracker.model.singleton.AllLocations;
import com.track.brachio.donationtracker.model.singleton.CurrentUser;
import com.track.brachio.donationtracker.model.singleton.UserLocations;
import com.track.brachio.donationtracker.model.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages Persistence
 */
public class PersistanceManager {

    private final Activity activity;
    private static boolean threadRunning;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    /**
     * Constructor for Persistance Manager
     * @param currentActivity current activity
     */
    public PersistanceManager(Activity currentActivity) {
        activity = currentActivity;

    }

    /**
     * loads app on start
     */
    public void loadAppOnStart() {
        gatherData();

    }


    /**
     * gather data
     */
    private void gatherData(){

        //Get all locations from db so user can view all locations
        FirebaseLocationHandler locHandler = new FirebaseLocationHandler(db);
        Task task1 = locHandler.getAllLocations();

        task1.addOnSuccessListener((OnSuccessListener<QuerySnapshot>) queryDocumentSnapshots -> {
            FirebaseItemHandler itemHandler = new FirebaseItemHandler(db);
            Task task2 = itemHandler.getAllItems();
            task2.addOnSuccessListener(
                    (OnSuccessListener<QuerySnapshot>) queryDocumentSnapshots1 -> {
                        FirebaseUserHandler userHandler = new FirebaseUserHandler();
                        User currentUser = CurrentUser.getInstance().getUser();
                        if (currentUser.getUserType() == UserType.Admin){
                            Task task3 = userHandler.getAllUsers();
                            task3.addOnSuccessListener((OnSuccessListener<QuerySnapshot>) queryDocumentSnapshots2 -> {
                                goToMainPage();
                            });
                        } else {
                            goToMainPage();
                        }
                    } );
        });
    }




    /**
     * goes to main page
     */
    private void goToMainPage(){
        CurrentUser userInstance = CurrentUser.getInstance();
        User currentUser = userInstance.getUser();

        //set what locations user can edit items
        UserLocations currentLocation = UserLocations.getInstance();
        AllLocations currentAllLocations = AllLocations.getInstance();
        List<Location> locationArray = currentAllLocations.getLocationArray();
        if (currentUser.getUserType() == UserType.Donator) {
            currentLocation.setLocations(locationArray);
        } else if (currentUser.getUserType() == UserType.Admin){
            currentLocation.setLocations(locationArray);
        } else if (currentUser.getUserType() == UserType.Volunteer) {
            currentLocation.setLocations(locationArray);
        } else if (currentUser.getUserType() == UserType.Manager) {
            currentLocation.setLocations(locationArray);
        } else if (currentUser.getUserType() == UserType.Guest) {
            currentLocation.setLocations(locationArray);
        }
        Intent intent = new Intent(activity, MainActivity.class );
        activity.startActivity(intent);
    }


    /**
     * sign out user
     */
    public static void signOut() {
        FirebaseUserHandler handler = new FirebaseUserHandler();
        handler.signOutUser();
        CurrentUser currentUserInstance = CurrentUser.getInstance();
        currentUserInstance.setUser( new User() );
        UserLocations currentLocationInstance = UserLocations.getInstance();
        currentLocationInstance.setLocations( new ArrayList<>() );

        //sign out google client
        handler.googleSignOut();

        //sign out of facebook client
        LoginManager.getInstance().logOut();
    }

    /**
     * deletes item
     * @param item item being deleted
     * @param activity current activity
     */
    public void deleteItem(Item item, Activity activity) {
        FirebaseItemHandler handler = new FirebaseItemHandler(db);
        Task task = handler.deleteItem( item );
        startItemUpdate(task, activity);
    }

    /**
     * adds item
     * @param item item being added
     * @param activity current activity
     */
    public void addItem(Item item, Activity activity) {
        FirebaseItemHandler handler = new FirebaseItemHandler(db);
        Task task = handler.addItem( item );
        startItemUpdate(task, activity);
    }

    /**
     * edits item
     * @param item item being edited
     * @param activity current activity
     */
    public void editItem(Item item, Activity activity) {
        FirebaseItemHandler handler = new FirebaseItemHandler(db);
        Task task = handler.editItem( item);
        //Bitmap pic = null;
        //item.setPicture(pic);
        startItemUpdate(task, activity);
    }

    public void updateUser(User user, Activity activity){
        FirebaseUserHandler handler = new FirebaseUserHandler();
        Task task = handler.updateUser( user );
        startUserUpdate(task, activity);
    }

    /**
     * starts update
     * @param editItemTask task being updated
     * @param currentActivity current activity
     */
    private void startItemUpdate(Task editItemTask, Activity currentActivity) {

        if (!threadRunning) {
            threadRunning = true;
            editItemTask.addOnCompleteListener((OnCompleteListener<QuerySnapshot>) task -> {
                Task updateTask = getAllItems();
                updateTask.addOnCompleteListener((OnCompleteListener<QuerySnapshot>) task1 -> {
                    threadRunning = false;
                    Intent intent = new Intent(currentActivity, MainActivity.class);
                    currentActivity.startActivity(intent);
                });

            });
        }

    }

    private void startUserUpdate(Task editUserTask, Activity currentActivity){
        if (!threadRunning) {
            threadRunning = true;
            editUserTask.addOnCompleteListener((OnCompleteListener<QuerySnapshot>) task -> {
                Task updateTask = getAllUsers();
                updateTask.addOnCompleteListener((OnCompleteListener<QuerySnapshot>) task1 -> {
                    threadRunning = false;
                    Intent intent = new Intent(currentActivity, MainActivity.class);
                    currentActivity.startActivity(intent);
                });

            });
        }
    }

    /**
     * returns all items
     * @return task with items
     */
    private Task getAllItems() {
        FirebaseItemHandler itemHandler = new FirebaseItemHandler(db);
        return itemHandler.getAllItems();
    }
    /**
     * returns all users
     * @return task with users
     */
    private Task getAllUsers(){
        FirebaseUserHandler userHandler = new FirebaseUserHandler();
        return userHandler.getAllUsers();
    }

    public void getPicture(Item item, Class nextActivity){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseItemHandler handler = new FirebaseItemHandler(db);
        handler.getPicture( item )
                .addOnCompleteListener(task -> {
                Intent intent = new Intent(activity, nextActivity);
                activity.startActivity(intent);
        });
    }



}

