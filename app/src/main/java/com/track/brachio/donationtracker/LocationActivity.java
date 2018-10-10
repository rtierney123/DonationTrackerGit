package com.track.brachio.donationtracker;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.track.brachio.donationtracker.model.User;
import com.track.brachio.donationtracker.model.UserType;
import com.track.brachio.donationtracker.model.database.FirebaseLocationHandler;
import com.track.brachio.donationtracker.model.singleton.CurrentLocation;
import com.track.brachio.donationtracker.model.database.FirebaseUserHandler;
import com.track.brachio.donationtracker.model.singleton.CurrentUser;
import com.track.brachio.donationtracker.model.LocationType;
import com.track.brachio.donationtracker.model.Location;



//Displays specific location information


public class LocationActivity extends AppCompatActivity {
    private TextView locationName;
    private TextView longitude;
    private TextView latitude;
    private TextView address;
    private TextView phone;
    private TextView website;
    private TextView locationType;
    private Button backButton;
    //How do we incorporate the singleton?
    Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locationName = (TextView) findViewById(R.id.locationNameID);
        longitude = (TextView) findViewById(R.id.longitudeID);
        latitude = (TextView) findViewById(R.id.latitudeID);
        address = (TextView) findViewById(R.id.addressID);
        phone = (TextView) findViewById(R.id.phoneID);
        website = (TextView) findViewById(R.id.websiteID);
        locationType = (TextView) findViewById(R.id.locationTypeID);
        backButton = (Button) findViewById(R.id.backButtonID);

        FirebaseLocationHandler handler = new FirebaseLocationHandler();

        currentLocation = CurrentLocation.getInstance().getLocation();

        locationName.setText(currentLocation.getName());
        longitude.setText(Double.toString(currentLocation.getLongitude()));
        latitude.setText(Double.toString(currentLocation.getLatitude()));
        address.setText(currentLocation.getAddress().toString());
        phone.setText(currentLocation.getPhone());
        website.setText(currentLocation.getWebsite());
        //locationType.setText(currentLocation.getTypebyString());




        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Location", "Location page canceled");
                //IDK where this should go, put it to donator for now
                Intent intent = new Intent(LocationActivity.this, DonatorMainActivity.class);
                startActivity(intent);
            }
        });


    }
}
