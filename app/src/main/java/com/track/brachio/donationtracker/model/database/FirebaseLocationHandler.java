package com.track.brachio.donationtracker.model.database;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.track.brachio.donationtracker.LocationListActivity;
import com.track.brachio.donationtracker.model.Address;
import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.singleton.AllLocations;
import com.track.brachio.donationtracker.controller.PersistanceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class FirebaseLocationHandler {
    private String TAG = "FirebaseLocationHandler";
    private Location locationCallback;
    private HashMap<String, Location> locationMap = new LinkedHashMap<>(  );
    private ArrayList<Location> locationArray = new ArrayList<>();

    public Location getLocation(String locationID){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection( "location" ).whereEqualTo( "locationID", locationID )
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
                        id = (String) doc.get("locationID");
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
                    locationCallback =
                            new Location(id, name, longitude, latitude,
                                    type, phone, website, address);
                }
            }
        } );
        return locationCallback;
    }

    public Task getAllLocations(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Task task = db.collection( "location" )
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

                    locationMap.clear();
                    for (DocumentSnapshot doc : retDocs) {
                        id = (String) doc.get("locationID");
                        name  = (String) doc.get( "name" );
                        longitude = (Double) doc.get( "longitude" );
                        latitude  = (Double) doc.get( "latitude" );
                        type = (String) doc.get( "type" );
                        phone = (String) doc.get("phone");
                        website = (String) doc.get("website");
                        streetAddress = (String) doc.get("address");
                        city = (String) doc.get("city");
                        state = (String) doc.get("state");
                        zip = (Long) doc.get("zip");
                        Address address = new Address(streetAddress, city, state, zip);
                        locationCallback =
                                new Location(id, name, longitude, latitude,
                                        type, phone, website, address);
                        locationMap.put(id, locationCallback);
                    }
                    AllLocations.getInstance().setLocationMap( locationMap );
                }
            }
        } );
        return task;
    }
    //TODO delete this once AllLocations is up and runnning
    //make sure to replace in LocationListActivity
    public void getLocations(LocationListActivity locListAct){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection( "location" )
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

                    locationMap.clear();
                    for (DocumentSnapshot doc : retDocs) {
                        id = (String) doc.get("locationID");
                        name  = (String) doc.get( "name" );
                        longitude = (Double) doc.get( "longitude" );
                        latitude  = (Double) doc.get( "latitude" );
                        type = (String) doc.get( "type" );
                        phone = (String) doc.get("phone");
                        website = (String) doc.get("website");
                        streetAddress = (String) doc.get("address");
                        city = (String) doc.get("city");
                        state = (String) doc.get("state");
                        zip = (Long) doc.get("zip");
                        Address address = new Address(streetAddress, city, state, zip);
                        locationCallback =
                                new Location(id, name, longitude, latitude,
                                        type, phone, website, address);
                        locationMap.put(id, locationCallback);
                    }
                    AllLocations.getInstance().setLocationMap( locationMap );
                }
            }
        } );
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
