package com.track.brachio.donationtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class VolunteerMainActivity extends AppCompatActivity {
    private Button logoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_volunteer_main );

        logoutButton = (Button) findViewById(R.id.logoutButton1);
        
    }
}
