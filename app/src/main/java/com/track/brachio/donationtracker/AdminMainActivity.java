package com.track.brachio.donationtracker;

import android.os.Bundle;
import android.widget.Button;

import com.track.brachio.donationtracker.controller.MainActivity;

public class AdminMainActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admin_main );
        Button adminMainLogout = (Button) findViewById(R.id.adminMainLogout);
        setLogoutButton(adminMainLogout);
    }

}
