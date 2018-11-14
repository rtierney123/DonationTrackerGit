package com.track.brachio.donationtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.track.brachio.donationtracker.model.database.FirebaseLocationHandler;
import com.track.brachio.donationtracker.model.singleton.CurrentItem;
import com.track.brachio.donationtracker.model.singleton.CurrentLocation;
import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.Address;


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

        //FirebaseLocationHandler handler = new FirebaseLocationHandler();
        Location currentLocation;
        CurrentLocation locationInstance = CurrentLocation.getInstance();
        currentLocation = locationInstance.getLocation();
        if (currentLocation != null) {
            double theLong = currentLocation.getLongitude();
            String theLongString = Double.toString(theLong);
            double lat = currentLocation.getLatitude();
            String theLatString = Double.toString(lat);
            Address add = currentLocation.getAddress();
            String addString = add.toString();
            String name = currentLocation.getName();
            String thePhone = currentLocation.getPhone();
            String theWebsite = currentLocation.getWebsite();
            String locType = currentLocation.getStringLocationType();

            locationName.setText(name);
            longitude.setText(theLongString);
            latitude.setText(theLatString);
            address.setText(addString);
            phone.setText(thePhone);
            website.setText(theWebsite);
            locationType.setText(locType);
        }



    }
}
