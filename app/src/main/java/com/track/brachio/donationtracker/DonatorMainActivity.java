package com.track.brachio.donationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.track.brachio.donationtracker.controller.MainActivity;
import com.track.brachio.donationtracker.model.database.FirebaseUserHandler;

public class DonatorMainActivity extends MainActivity {

    private Button donMainLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_donator_main );
        donMainLogout = (Button) findViewById(R.id.donMainLogout);
        donMainLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FirebaseUserHandler handler = new FirebaseUserHandler();
                handler.signOutUser();
                Intent intent = new Intent(DonatorMainActivity.this, StartUpActivity.class);
                startActivity(intent);
            }
        });
    }

}
