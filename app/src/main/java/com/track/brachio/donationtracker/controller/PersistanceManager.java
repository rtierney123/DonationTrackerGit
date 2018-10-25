package com.track.brachio.donationtracker.controller;

import android.app.Activity;
import android.content.Intent;

import com.track.brachio.donationtracker.AdminMainActivity;
import com.track.brachio.donationtracker.DonatorMainActivity;
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
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PersistanceManager {

    private static ThreadPoolExecutor executor;
    private static boolean isActive = false;
    private static boolean isNew = true;
    private Activity activity;

    public PersistanceManager(Activity currentActivity){
        activity = currentActivity;
        initializeVariables();
    }

    public boolean loadAppOnStart(Activity activity){
        User user = CurrentUser.getInstance().getUser();
        gatherData(user);
        return true;
    }


    private void gatherData(User currentUser){
        //Get all locations from db so user can view all locations
        FirebaseLocationHandler locHandler = new FirebaseLocationHandler();
        locHandler.getAllLocations(this);

    }

    private void initializeVariables(){
        int numCores = Runtime.getRuntime().availableProcessors();
        executor = new ThreadPoolExecutor(numCores * 2, numCores *2,
                60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    }

    public ThreadPoolExecutor getExecutor(){
        return executor;
    }

    public void startExecutor(){
        if (isNew){
            Thread thread = new Thread(new ThreadMonitor());
            thread.start();
        }
    }

    public class ThreadMonitor implements Runnable {
        private volatile boolean running = true;
        public void terminate() {
            running = false;
        }
        public void run(){
            while (running) {
                try {
                    if (executor.getActiveCount() == 0){
                        terminate();
                        User currentUser = CurrentUser.getInstance().getUser();
                        Intent intent = new Intent(activity, DonatorMainActivity.class);;
                        if (currentUser.getUserType() == UserType.Donator) {
                            UserLocations.getInstance().setLocations(AllLocations.getInstance().getLocationArray());
                            intent = new Intent(activity, DonatorMainActivity.class);
                        } else if (currentUser.getUserType() == UserType.Admin){
                            UserLocations.getInstance().setLocations(AllLocations.getInstance().getLocationArray());
                            intent = new Intent(activity, AdminMainActivity.class);
                        } else if (currentUser.getUserType() == UserType.Volunteer) {
                            User user = CurrentUser.getInstance().getUser();
                            HashMap<String, Location> map = AllLocations.getInstance().getLocationMap();
                            ArrayList<Location> array = new ArrayList<>();
                            ArrayList<String> ids = user.getLocations();
                            for (String id : ids) {
                                array.add(map.get(id));
                            }
                            UserLocations.getInstance().setLocations(array);
                            intent = new Intent(activity, VolunteerMainActivity.class);
                        } else if (currentUser.getUserType() == UserType.Manager) {
                            UserLocations.getInstance().setLocations(AllLocations.getInstance().getLocationArray());
                            intent = new Intent(activity, ManagerMainActivity.class);
                        }
                        isActive = false;
                        isNew = true;
                        activity.startActivity(intent);
                    } else {
                        isNew = false;
                    }
                    Thread.sleep((long) 200);
                } catch (InterruptedException e) {
                    running = false;
                }
            }
        }
    }

    public static void setActive(boolean active){
        isActive = active;
    }

    public static void signOut(){
        FirebaseUserHandler handler = new FirebaseUserHandler();
        handler.signOutUser();
        CurrentUser.getInstance().setUser(new User());
        UserLocations.getInstance().setLocations(new ArrayList<Location>());
    }

    public static void deleteItem(Item item){
        FirebaseItemHandler db = new FirebaseItemHandler();
        db.deleteItem( item );
    }

    public static void addItem(Item item){
        FirebaseItemHandler db = new FirebaseItemHandler();
        db.addItem(item);
    }

}

