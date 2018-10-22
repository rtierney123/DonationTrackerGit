package com.track.brachio.donationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.track.brachio.donationtracker.controller.MainActivity;
import com.track.brachio.donationtracker.model.database.FirebaseUserHandler;

public class AdminMainActivity extends MainActivity {
    private Button adminMainLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admin_main );
        adminMainLogout = (Button) findViewById(R.id.adminMainLogout);
        setLogoutButton( adminMainLogout );
    }

}
