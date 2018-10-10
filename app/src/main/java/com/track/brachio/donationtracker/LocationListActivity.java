package com.track.brachio.donationtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.database.FirebaseLocationHandler;

import java.util.ArrayList;

public class LocationListActivity extends AppCompatActivity {
    private ArrayList<Location> locations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_location_list );

        FirebaseLocationHandler handler = new FirebaseLocationHandler();
        handler.getLocations( this );
    }

    public void populateLocationList(ArrayList<Location> locs){
        locations = locs;

        //TODO add junk here to take the location array an turn it into displayed list (probably with Recycle view and an adapter)

    }
}
