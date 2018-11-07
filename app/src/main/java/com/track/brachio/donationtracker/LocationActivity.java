package com.track.brachio.donationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private TextView longitude;
    private TextView phone;
    private TextView website;
    private TextView locationType;
    private Button backButton;
    //How do we incorporate the singleton?
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        TextView locationName = findViewById(R.id.locationNameID);
        longitude = findViewById(R.id.longitudeID);
        TextView latitude = findViewById(R.id.latitudeID);
        TextView address = findViewById(R.id.addressID);
        phone = findViewById(R.id.phoneID);
        website = findViewById(R.id.websiteID);
        locationType = findViewById(R.id.locationTypeID);
        backButton = findViewById(R.id.backButtonID);

        FirebaseLocationHandler handler = new FirebaseLocationHandler();

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





        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Location", "Location page canceled");
                //IDK where this should go, put it to LocationListActivity for now
                Intent intent = new Intent(LocationActivity.this, LocationListActivity.class);
                startActivity(intent);
            }
        });


    }
}
