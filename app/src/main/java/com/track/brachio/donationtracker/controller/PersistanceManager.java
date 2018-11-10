package com.track.brachio.donationtracker.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

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
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Manages Persistance
 */
@SuppressWarnings("FeatureEnvy")
public class PersistanceManager {

    private static ThreadPoolExecutor executor;
    private final Activity activity;
    private static boolean threadRunning;

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
        User user = CurrentUser.getInstance().getUser();
        try {
            gatherData(activity);
        } catch (InterruptedException ex) {
            Log.e("Something", "Happened");
        }

    }


    /**
     * gather data
     * @throws InterruptedException ** throws Interrupted Exception**
     */
    private void gatherData(Context context) throws InterruptedException {
        //Get all locations from db so user can view all locations
        FirebaseLocationHandler locHandler = new FirebaseLocationHandler();
        Task task1 = locHandler.getAllLocations();

        task1.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                FirebaseItemHandler itemHandler = new FirebaseItemHandler();
                Task task2 = itemHandler.getAllItems(context);
                task2.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        goToMainPage();
                    }
                });
            }
        });
    }

    /**
     * initializes variables
     */
    private void initializeVariables() {
        int numCores = Runtime.getRuntime().availableProcessors();
        long aliveTime = 60L;
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
        User currentUser = CurrentUser.getInstance().getUser();

        //set what locations user can edit items
        if (currentUser.getUserType() == UserType.Donator) {
            UserLocations.getInstance().setLocations(AllLocations.getInstance().getLocationArray());
        } else if (currentUser.getUserType() == UserType.Admin){
            UserLocations.getInstance().setLocations(AllLocations.getInstance().getLocationArray());
        } else if (currentUser.getUserType() == UserType.Volunteer) {
            UserLocations.getInstance().setLocations(AllLocations.getInstance().getLocationArray());
        } else if (currentUser.getUserType() == UserType.Manager) {
            UserLocations.getInstance().setLocations(AllLocations.getInstance().getLocationArray());

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
        CurrentUser.getInstance().setUser( new User() );
        UserLocations.getInstance().setLocations( new ArrayList<>() );
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
            editItemTask.addOnCompleteListener( new OnCompleteListener<QuerySnapshot>(){

                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    Task updateTask = getAllItems(currentActivity);
                    updateTask.addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            threadRunning = false;
                            Intent intent = new Intent(currentActivity, MainActivity.class);
                            currentActivity.startActivity(intent);
                        }
                    } );

                }
            } );
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

