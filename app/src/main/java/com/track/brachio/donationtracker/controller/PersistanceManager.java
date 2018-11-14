package com.track.brachio.donationtracker.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Manages Persistance
 */
@SuppressWarnings({"FeatureEnvy", "ClassWithTooManyDependencies"})
public class PersistanceManager {

    private static ThreadPoolExecutor executor;
    private final Activity activity;
    private static boolean threadRunning;
    private static final long aliveTime = 60L;

    /**
     * Constructor for Persistance Manager
     * @param currentActivity current activity
     */
    public PersistanceManager(Activity currentActivity) {
        activity = currentActivity;
        initializeVariables();
    }

    /**
     * loads app on start
     * @param activity current activity
     */
    public void loadAppOnStart(Activity activity) {
        CurrentUser userInstance = CurrentUser.getInstance();
        User user = userInstance.getUser();
        gatherData(activity);

    }


    /**
     * gather data
     */
    private void gatherData(Context contezt){

        //Get all locations from db so user can view all locations
        FirebaseLocationHandler locHandler = new FirebaseLocationHandler();
        Task task1 = locHandler.getAllLocations();

        //noinspection unchecked
        task1.addOnSuccessListener((OnSuccessListener<QuerySnapshot>) queryDocumentSnapshots -> {
            FirebaseItemHandler itemHandler = new FirebaseItemHandler();
            Task task2 = itemHandler.getAllItems(contezt);
            //noinspection unchecked
            task2.addOnSuccessListener((OnSuccessListener<QuerySnapshot>) queryDocumentSnapshots1 -> goToMainPage());
        });
    }

    /**
     * initializes variables
     */
    private void initializeVariables() {
        int numCores = Runtime.getRuntime().availableProcessors();

        executor = new ThreadPoolExecutor( numCores * 2, numCores * 2,
                aliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue<>() );
    }

    /**
     * returns executor
     * @return current executor
     */
    public ThreadPoolExecutor getExecutor() {
        return executor;
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
    }

    /**
     * deletes item
     * @param item item being deleted
     * @param activity current activity
     */
    public void deleteItem(Item item, Activity activity) {
        FirebaseItemHandler db = new FirebaseItemHandler();
        Task task = db.deleteItem( item );
        startUpdate(task, activity);
    }

    /**
     * adds item
     * @param item item being added
     * @param activity current activity
     */
    public void addItem(Item item, Activity activity) {
        FirebaseItemHandler db = new FirebaseItemHandler();
        Task task = db.addItem( item, executor );
        startUpdate(task, activity);
    }

    /**
     * edits item
     * @param item item being edited
     * @param activity current activity
     */
    public void editItem(Item item, Activity activity) {
        FirebaseItemHandler db = new FirebaseItemHandler();
        Task task = db.editItem( item);
        startUpdate(task, activity);
    }

    /**
     * starts update
     * @param editItemTask task being updated
     * @param currentActivity current activity
     */
    private void startUpdate(Task editItemTask, Activity currentActivity) {

        if (!threadRunning) {
            threadRunning = true;
            //noinspection unchecked
            editItemTask.addOnCompleteListener((OnCompleteListener<QuerySnapshot>) task -> {
                Task updateTask = getAllItems(currentActivity);
                //noinspection unchecked
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
    private Task getAllItems(Context context) {
        FirebaseItemHandler itemHandler = new FirebaseItemHandler();
        return itemHandler.getAllItems(context);
    }

    /**
     * gets thread running
     * @return true if thread is running
     */
    public static boolean getThreadRunning() {
        return threadRunning;
    }


}

