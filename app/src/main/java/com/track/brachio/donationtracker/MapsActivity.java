package com.track.brachio.donationtracker;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.view.View;
import android.widget.Spinner;

import com.google.android.gms.maps.model.MarkerOptions;
import com.track.brachio.donationtracker.controller.UIPopulator;
import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.singleton.UserLocations;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import android.widget.AdapterView;
import android.util.Log;

/**
 * Activity for Google Maps
 */
@SuppressWarnings("FeatureEnvy")
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Location> locations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Spinner locationDisplayed = findViewById(R.id.locationsOnMaps);
        UIPopulator ui = new UIPopulator();
        List<String> names = new ArrayList<>();
        locations = UserLocations.getInstance().getLocations();

        for (Location location : locations){
            names.add(location.getName());
        }

        ui.populateSpinner(locationDisplayed, names, this);
        
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);


        locationDisplayed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View v, int position, long id)
            {
                float zoomFactor = 12.0f;
                LatLng loc = new LatLng(
                        locations.get(position).getLongitude(), locations.get(position).getLatitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, zoomFactor));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {
                //Log.v("routes", "nothing selected");
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addMarkers();
    }

    private void addMarkers(){

        for(int i = 0; i < locations.size(); i++){
            LatLng loc = new LatLng(
                    locations.get(i).getLongitude(), locations.get(i).getLatitude());
            mMap.addMarker(
                    new MarkerOptions().position(loc).title(locations.get(i).getName())
                            .snippet(locations.get(i).getPhone()));
        }

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
        // Add a marker in Sydney and move the camera
        LatLng AFDStation4 = new LatLng(33.75416, -84.37742);
        mMap.addMarker(new MarkerOptions().position(AFDStation4).title("ID: 1 Name: "
                + "AFD Station 4"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(AFDStation4, 12.0f));
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
=======
>>>>>>> 9b9841f1d9e2474400d8bd6b425c599fe811b495
=======
>>>>>>> 9b9841f1d9e2474400d8bd6b425c599fe811b495
=======
>>>>>>> 9b9841f1d9e2474400d8bd6b425c599fe811b495
    }
}
