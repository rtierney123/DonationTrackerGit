package com.track.brachio.donationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.track.brachio.donationtracker.controller.MainActivity;
import com.track.brachio.donationtracker.controller.UIPopulator;
import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.singleton.AllLocations;
import com.track.brachio.donationtracker.model.singleton.UserLocations;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for Volunteer Main
 */
public class VolunteerMainActivity extends MainActivity {
    private Button logoutButton;
    private Button volManagerItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState );
        setContentView(R.layout.activity_volunteer_main);

        Button volGoToLocationsButton = (Button) findViewById(R.id.volLocationButton);
        volGoToLocationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VolunteerMainActivity.this, LocationListActivity.class);
                startActivity(intent);
            }
        });

        logoutButton = findViewById(R.id.logoutButton1);
        setLogoutButton( logoutButton );


        List<Location> locations = AllLocations.getInstance().getLocationArray();
        List<String> spinnerArray = new ArrayList<>();
        for(Location loc : locations) {
            spinnerArray.add(loc.getName());
        }

        Spinner allLocSpinner = findViewById(R.id.add_location_spinner);

        UIPopulator populator = new UIPopulator();
        populator.populateSpinner( allLocSpinner, spinnerArray , this);

        locations = UserLocations.getInstance().getLocations();
        spinnerArray = new ArrayList<>();
        for(Location loc : locations) {
            if (loc != null) {
                spinnerArray.add(loc.getName());
            }
        }

        Spinner userSpinner = findViewById(R.id.view_location_spinner);
        populator.populateSpinner( userSpinner, spinnerArray , this);

        volManagerItems = (Button) findViewById(R.id.volManagerItemsButton);
        volManagerItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VolunteerMainActivity.this, ItemListActivityTemp.class);
                startActivity(intent);
            }
        });
    }
}
