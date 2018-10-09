package com.track.brachio.donationtracker.model.database;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.track.brachio.donationtracker.LoginActivity;
import com.track.brachio.donationtracker.model.Address;
import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.User;
import com.track.brachio.donationtracker.model.singleton.CurrentUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseLocationHandler {
    private String TAG = "FirebaseLocationHandler";
    private Location locationCallback;
    private ArrayList<Location> locationArray;

    public Location getLocation(String name){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection( "location" ).whereEqualTo( "name", name )
                .get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    Log.d( TAG, "onSuccess: LIST EMPTY" );
                    return;
                } else {
                    List<DocumentSnapshot> retDocs = documentSnapshots.getDocuments();
                    String id = "";
                    String name = "";
                    double longitude = 0;
                    double latitude = 0;
                    String type = "";
                    String phone = "";
                    String website = "";
                    String streetAddress = "";
                    String city = "";
                    String state = "";
                    long zip = 0;
                    Log.d(TAG, "onSucccess: Got Location");

                    for (DocumentSnapshot doc : retDocs) {
                        id = (String) doc.getId();
                        name  = (String) doc.get( "name" );
                        longitude = (Double) doc.get( "longitude" );
                        latitude  = (Double) doc.get( "latitude" );
                        type = (String) doc.get( "type" );
                        phone = (String) doc.get("phone");
                        website = (String) doc.get("website");
                        streetAddress = (String) doc.get("address");
                        city = (String) doc.get("city");
                        state = (String) doc.get("city");
                        zip = (Long) doc.get("zip");
                    }
                    Address address = new Address(streetAddress, city, state, zip);
                    locationCallback = new Location(id, name, longitude, latitude, type, phone, website, address);
                }
            }
        } );
        return locationCallback;
    }

    public ArrayList<Location> getLocations(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection( "location" ).whereEqualTo( "name", true )
                .get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    Log.d( TAG, "onSuccess: LIST EMPTY" );
                    return;
                } else {
                    List<DocumentSnapshot> retDocs = documentSnapshots.getDocuments();
                    String id = "";
                    String name = "";
                    double longitude = 0;
                    double latitude = 0;
                    String type = "";
                    String phone = "";
                    String website = "";
                    String streetAddress = "";
                    String city = "";
                    String state = "";
                    long zip = 0;
                    Log.d(TAG, "onSucccess: Got Locations");

                    locationArray.clear();
                    for (DocumentSnapshot doc : retDocs) {
                        id = (String) doc.getId();
                        name  = (String) doc.get( "name" );
                        longitude = (Double) doc.get( "longitude" );
                        latitude  = (Double) doc.get( "latitude" );
                        type = (String) doc.get( "type" );
                        phone = (String) doc.get("phone");
                        website = (String) doc.get("website");
                        streetAddress = (String) doc.get("address");
                        city = (String) doc.get("city");
                        state = (String) doc.get("city");
                        zip = (Long) doc.get("zip");
                        Address address = new Address(streetAddress, city, state, zip);
                        locationCallback = new Location(id, name, longitude, latitude, type, phone, website, address);
                        locationArray.add(locationCallback);
                    }

                }
            }
        } );
        return locationArray;
    }

    public void addLocation(Location location)  {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> locMap = new HashMap<>();
        if(location != null) {
            locMap.put("name", location.getName());
            locMap.put("latitude", location.getLatitude());
            locMap.put("longitude", location.getLongitude());
            locMap.put("type", location.getType());
            locMap.put("phone", location.getPhone());
            locMap.put("website", location.getWebsite());
            Address address = location.getAddress();
            locMap.put("address", address.getStreetAddress());
            locMap.put("city", address.getCity());
            locMap.put("state", address.getState());
            locMap.put("zip", address.getZip());
            db.collection("location").document("location").set(locMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Added location.");
                        }
                    });
        } else {
            Log.e(TAG, "Entered null location");
        }
    }

}
