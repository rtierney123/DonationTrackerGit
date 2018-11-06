package com.track.brachio.donationtracker.controller;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;
import com.track.brachio.donationtracker.AdminMainActivity;
import com.track.brachio.donationtracker.DonatorMainActivity;
import com.track.brachio.donationtracker.ItemListActivity;
import com.track.brachio.donationtracker.ManagerMainActivity;
import com.track.brachio.donationtracker.VolunteerMainActivity;
import com.track.brachio.donationtracker.model.Item;
import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.User;
import com.track.brachio.donationtracker.model.UserType;
import com.track.brachio.donationtracker.model.database.FirebaseItemHandler;
import com.track.brachio.donationtracker.model.database.FirebaseLocationHandler;
import com.track.brachio.donationtracker.model.database.FirebaseUserHandler;
import com.track.brachio.donationtracker.model.singleton.AllLocations;
import com.track.brachio.donationtracker.model.singleton.CurrentUser;
import com.track.brachio.donationtracker.model.singleton.UserLocations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PersistanceManager {

    private static ThreadPoolExecutor executor;
    private Activity activity;
    private static boolean threadRunning;

    public PersistanceManager(Activity currentActivity) {
        activity = currentActivity;
        initializeVariables();
    }

    public boolean loadAppOnStart(Activity activity) {
        User user = CurrentUser.getInstance().getUser();
        try {
            gatherData();
        } catch (InterruptedException ex) {

        }

        return true;
    }


    private void gatherData() throws InterruptedException {
        //Get all locations from db so user can view all locations
        FirebaseLocationHandler locHandler = new FirebaseLocationHandler();
        Task task1 = locHandler.getAllLocations();

        task1.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                FirebaseItemHandler itemHandler = new FirebaseItemHandler();
                Task task2 = itemHandler.getAllItems();
                task2.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        goToMainPage();
                    }
                });
            }
        });

        //startExecutor();
    }

    private void initializeVariables() {
        int numCores = Runtime.getRuntime().availableProcessors();
        executor = new ThreadPoolExecutor( numCores * 2, numCores * 2,
                60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>() );
    }

    public ThreadPoolExecutor getExecutor() {
        return executor;
    }


    public void goToMainPage(){
        User currentUser = CurrentUser.getInstance().getUser();
        Intent intent = new Intent(activity, DonatorMainActivity.class);;
        if (currentUser.getUserType() == UserType.Donator) {
            UserLocations.getInstance().setLocations(AllLocations.getInstance().getLocationArray());
            intent = new Intent(activity, DonatorMainActivity.class);
        } else if (currentUser.getUserType() == UserType.Admin){
            UserLocations.getInstance().setLocations(AllLocations.getInstance().getLocationArray());
            intent = new Intent(activity, AdminMainActivity.class);
        } else if (currentUser.getUserType() == UserType.Volunteer) {
                            /*
                            User user = CurrentUser.getInstance().getUser();
                            HashMap<String, Location> map =
                            AllLocations.getInstance().getLocationMap();
                            ArrayList<Location> array = new ArrayList<>();
                            ArrayList<String> ids = user.getLocations();
                            for (String id : ids) {
                                array.add(map.get(id));
                            }
                            */
            UserLocations.getInstance().setLocations(AllLocations.getInstance().getLocationArray());
            intent = new Intent(activity, VolunteerMainActivity.class);
        } else if (currentUser.getUserType() == UserType.Manager) {
            UserLocations.getInstance().setLocations(AllLocations.getInstance().getLocationArray());
            intent = new Intent(activity, ManagerMainActivity.class);
        }
        activity.startActivity(intent);
    }


    public static void signOut() {
        FirebaseUserHandler handler = new FirebaseUserHandler();
        handler.signOutUser();
        CurrentUser.getInstance().setUser( new User() );
        UserLocations.getInstance().setLocations( new ArrayList<Location>() );
    }

    public void deleteItem(Item item, Activity activity) {
        FirebaseItemHandler db = new FirebaseItemHandler();
        Task task = db.deleteItem( item );
        startUpdate(task, activity);
    }

    public void addItem(Item item, Activity activity) {
        FirebaseItemHandler db = new FirebaseItemHandler();
        Task task = db.addItem( item, executor );
        startUpdate(task, activity);
    }

    public void editItem(Item item, Activity activity) {
        FirebaseItemHandler db = new FirebaseItemHandler();
        Task task = db.editItem( item);
        startUpdate(task, activity);
    }

    public void startUpdate(Task editItemTask, Activity currentActivity) {

        if (!threadRunning) {
            threadRunning = true;
            editItemTask.addOnCompleteListener( new OnCompleteListener<QuerySnapshot>(){

                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    Task updateTask = getAllItems();
                    updateTask.addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            threadRunning = false;
                            Intent intent = new Intent(currentActivity, ItemListActivity.class);
                            currentActivity.startActivity(intent);
                        }
                    } );

                }
            } );
        }

    }

    public Task getAllItems() {
        FirebaseItemHandler itemHandler = new FirebaseItemHandler();
        return itemHandler.getAllItems();
    }


    public static boolean getThreadRunning() {
        return threadRunning;
    }


}

