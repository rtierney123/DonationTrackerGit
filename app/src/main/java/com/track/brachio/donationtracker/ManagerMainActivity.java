package com.track.brachio.donationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.track.brachio.donationtracker.controller.MainActivity;
import com.track.brachio.donationtracker.model.database.FirebaseUserHandler;

public class ManagerMainActivity extends MainActivity {
    private Button manGoToLocationsButton;
    private Button manMainLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_manager_main );

        manGoToLocationsButton = (Button) findViewById(R.id.manLocationButton);
        manGoToLocationsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ManagerMainActivity.this, LocationListActivity.class);
                startActivity(intent);
            }
        });

        manMainLogout = (Button) findViewById(R.id.manMainLogout);
        setLogoutButton( manMainLogout );


    }

}
