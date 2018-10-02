package com.track.brachio.donationtracker.controller;

import android.app.Activity;

import com.google.firebase.auth.FirebaseUser;
import com.track.brachio.donationtracker.model.database.FirebaseUserHandler;

public class MainActivity extends Activity {
    @Override
    public void onBackPressed() {
        FirebaseUserHandler handler = new FirebaseUserHandler();
        if (!handler.isUserSignedIn()) {
            handler.signOutUser();
        }
    }
}
