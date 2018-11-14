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

/**
 * Activity for Google Maps
 */
@SuppressWarnings("FeatureEnvy")
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Location> locations;
    private static final float zoomFactor = 12.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Spinner locationDisplayed = findViewById(R.id.locationsOnMaps);
        UIPopulator ui = new UIPopulator();
        List<String> names = new ArrayList<>();
        UserLocations currentLocations = UserLocations.getInstance();
        locations = currentLocations.getLocations();

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
                Location locationOne = locations.get(position);
                LatLng loc = new LatLng(
                        locationOne.getLongitude(),
                        locationOne.getLatitude());
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
            Location location = locations.get(i);
            LatLng loc = new LatLng(
                    location.getLongitude(), location.getLatitude());
            mMap.addMarker(
                    new MarkerOptions().position(loc).title(location.getName())
                            .snippet(location.getPhone()));
        }


    }
}
