package com.track.brachio.donationtracker.controller;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.track.brachio.donationtracker.AdminMainActivity;
import com.track.brachio.donationtracker.DonatorMainActivity;
import com.track.brachio.donationtracker.LoginActivity;
import com.track.brachio.donationtracker.ManagerMainActivity;
import com.track.brachio.donationtracker.VolunteerMainActivity;
import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.User;
import com.track.brachio.donationtracker.model.UserType;
import com.track.brachio.donationtracker.model.database.FirebaseLocationHandler;
import com.track.brachio.donationtracker.model.singleton.CurrentUser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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

    public static ThreadPoolExecutor getExecutor(){
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
                            intent = new Intent(activity, DonatorMainActivity.class);
                        } else if (currentUser.getUserType() == UserType.Admin){
                            intent = new Intent(activity, AdminMainActivity.class);
                        } else if (currentUser.getUserType() == UserType.Volunteer) {
                            intent = new Intent(activity, VolunteerMainActivity.class);
                        } else if (currentUser.getUserType() == UserType.Manager) {
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

}

