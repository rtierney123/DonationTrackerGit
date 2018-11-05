package com.track.brachio.donationtracker;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.view.View;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import com.google.android.gms.maps.model.MarkerOptions;
import com.track.brachio.donationtracker.controller.UIPopulator;
import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.singleton.UserLocations;
import java.util.ArrayList;
import android.widget.AdapterView;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Spinner locationDisplayed;
    private UIPopulator ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        locationDisplayed = (Spinner) findViewById(R.id.locationsOnMaps);
        ui = new UIPopulator();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Location> array = UserLocations.getInstance().getLocations();
        for (Location loc : array) {
            names.add(loc.getName());
        }

        ui.populateSpinner(locationDisplayed, names, this);
        //locationDisplayed.setSelection()
        
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        locationDisplayed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
//        {
//            public void onItemSelected(AdapterView<?> arg0, View v, int position, long id)
//            {
//                if (locationDisplayed.getSelectedItem() != null) {
//                    Location selLoc = (Location) locationDisplayed.getSelectedItem();
//                    LatLng loc = new LatLng(selLoc.getLatitude(), selLoc.getLongitude());
//                    mMap.addMarker(new MarkerOptions().position(loc).title(selLoc.getName()));
//                    mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
//                }
//            }
//
//            public void onNothingSelected(AdapterView<?> arg0)
//            {
//                //Log.v("routes", "nothing selected");
//            }
//        });
//        locationDisplayed.setOnItemSelectedListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Location locationSelected = null;
//                for (Location loc: array) {
//                    if (loc.equals((Location) locationDisplayed.getSelectedItem())) {
//                        locationSelected = loc;
//                    }
//                }
//                if (locationSelected != null) {
//                    LatLng loc = new LatLng(locationSelected.getLatitude(), locationSelected.getLongitude());
//                    mMap.addMarker(new MarkerOptions().position(loc).title(locationSelected.getName()));
//                    mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
//                }
//            }
//        });
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

        // Add a marker in Sydney and move the camera
        LatLng AFDStation4 = new LatLng(33.75416, -84.37742);
        mMap.addMarker(new MarkerOptions().position(AFDStation4).title("AFD Station 4"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(AFDStation4));
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
