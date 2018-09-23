package com.track.brachio.donationtracker.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.track.brachio.donationtracker.R;

public class StartUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        //use to for login registion page
        /*
        User user = new User("Rachel", "Tierney","tierne_r@outlook.com", "Admin");
        FirebaseUserHandler handler = new FirebaseUserHandler();
        handler.createNewUser( user, "blahblahblah123", this);
        */


        //use for login page
        /*
        User user = new User("Rachel", "Tierney", "tierne_r@outlook.com", "Admin");
        FirebaseUserHandler handler = new FirebaseUserHandler();
        handler.signInUser(user, "blahblahblah123", this);
        boolean signedIn = handler.isUserSignedIn();
        */

        //use to logout
        /*
        FirebaseUserHandler handler = new FirebaseUserHandler();
        hander.signOutUser();
         */
    }
}
