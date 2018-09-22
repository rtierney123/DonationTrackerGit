package com.track.brachio.donationtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //commenting this out to go to login page right now
        //setContentView( R.layout.activity_main );
        setContentView(R.layout.activity_login);

    }
}
