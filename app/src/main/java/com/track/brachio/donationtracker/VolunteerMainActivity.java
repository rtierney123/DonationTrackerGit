package com.track.brachio.donationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.track.brachio.donationtracker.controller.MainActivity;
import com.track.brachio.donationtracker.controller.UIPopulator;
import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.database.FirebaseUserHandler;
import com.track.brachio.donationtracker.model.singleton.AllLocations;
import com.track.brachio.donationtracker.model.singleton.UserLocations;

import java.util.ArrayList;

public class VolunteerMainActivity extends MainActivity {
    private Button volGoToLocationsButton;
    private Button logoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState );
        setContentView(R.layout.activity_volunteer_main);

        volGoToLocationsButton = (Button) findViewById(R.id.volLocationButton);
        volGoToLocationsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(VolunteerMainActivity.this, LocationListActivity.class);
                startActivity(intent);
            }
        });

        logoutButton = (Button) findViewById(R.id.logoutButton1);
        setLogoutButton( logoutButton );


        ArrayList<Location> locations = AllLocations.getInstance().getLocationArray();
        ArrayList<String> spinnerArray = new ArrayList<>();
        for(Location loc : locations) {
            spinnerArray.add(loc.getName());
        }

        Spinner allLocSpinner = (Spinner) findViewById(R.id.add_location_spinner);

        UIPopulator populator = new UIPopulator();
        populator.populateSpinner( allLocSpinner, spinnerArray , this);

        locations = UserLocations.getInstance().getLocations();
        spinnerArray = new ArrayList<>();
        for(Location loc : locations) {
            if (loc != null) {
                spinnerArray.add(loc.getName());
            }
        }

        Spinner userSpinner = (Spinner) findViewById(R.id.view_location_spinner);
        populator.populateSpinner( userSpinner, spinnerArray , this);




    }
}
