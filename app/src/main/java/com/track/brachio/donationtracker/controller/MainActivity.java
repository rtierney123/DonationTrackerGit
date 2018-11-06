package com.track.brachio.donationtracker.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;
import com.track.brachio.donationtracker.ManagerMainActivity;
import com.track.brachio.donationtracker.StartUpActivity;
import com.track.brachio.donationtracker.model.database.FirebaseUserHandler;

@SuppressWarnings("SpellCheckingInspection")
public class MainActivity extends Activity {
    @Override
    public void onBackPressed() {
        FirebaseUserHandler handler = new FirebaseUserHandler();
        if (!handler.isUserSignedIn()) {
            handler.signOutUser();
        }
    }

    protected void setLogoutButton(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PersistanceManager manager = new PersistanceManager(MainActivity.this);
                PersistanceManager.signOut();
                Intent intent = new Intent(MainActivity.this, StartUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
