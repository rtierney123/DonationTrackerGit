package com.track.brachio.donationtracker.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.track.brachio.donationtracker.R;
import com.track.brachio.donationtracker.model.User;
import com.track.brachio.donationtracker.model.database.FirebaseHandler;

public class StartUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        //used this to test Firebase
        /*
        User user = new User("1", "rtierney", "tierne_r@outlook.com", "blah","Admin");
        FirebaseHandler handler = new FirebaseHandler();
        handler.addUser(user);
        */
    }
}
