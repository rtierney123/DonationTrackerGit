package com.track.brachio.donationtracker.controller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.track.brachio.donationtracker.LoginActivity;
import com.track.brachio.donationtracker.model.database.FirebaseUserHandler;

/**
 * Activity for the main page
 */
@SuppressWarnings("SpellCheckingInspection")
public class MainActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        FirebaseUserHandler handler = new FirebaseUserHandler();
        if (!handler.isUserSignedIn()) {
            handler.signOutUser();
        }
    }

    /**
     * sets listener on logout button
     * @param button button for the logout button
     */
    protected void setLogoutButton(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersistanceManager manager = new PersistanceManager(MainActivity.this);
                PersistanceManager.signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void logOut(){
        PersistanceManager manager = new PersistanceManager(MainActivity.this);
        PersistanceManager.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
