package com.track.brachio.donationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.track.brachio.donationtracker.model.database.FirebaseLocationHandler;
import com.track.brachio.donationtracker.model.singleton.CurrentLocation;
import com.track.brachio.donationtracker.model.Location;



//Displays specific location information

/**
 * Activity for Locations
 */
@SuppressWarnings({"SpellCheckingInspection", "FeatureEnvy"})
public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        TextView locationName = findViewById(R.id.locationNameID);
        TextView longitude = findViewById(R.id.longitudeID);
        TextView latitude = findViewById(R.id.latitudeID);
        TextView address = findViewById(R.id.addressID);
        TextView phone = findViewById(R.id.phoneID);
        TextView website = findViewById(R.id.websiteID);
        TextView locationType = findViewById(R.id.locationTypeID);

        FirebaseLocationHandler handler = new FirebaseLocationHandler();
        Location currentLocation;
        currentLocation = CurrentLocation.getInstance().getLocation();
        if (currentLocation != null) {
            locationName.setText(currentLocation.getName());
            longitude.setText(Double.toString(currentLocation.getLongitude()));
            latitude.setText(Double.toString(currentLocation.getLatitude()));
            address.setText(currentLocation.getAddress().toString());
            phone.setText(currentLocation.getPhone());
            website.setText(currentLocation.getWebsite());
            locationType.setText(currentLocation.getStringLocationType());
        }



    }
}
