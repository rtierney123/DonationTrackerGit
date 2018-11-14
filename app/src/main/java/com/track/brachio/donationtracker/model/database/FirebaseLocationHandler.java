package com.track.brachio.donationtracker.model.database;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.track.brachio.donationtracker.model.Address;
import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.singleton.AllLocations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Handler for Locations in Database
 */
public class FirebaseLocationHandler {
    private final String TAG = "FirebaseLocationHandler";
    private Location locationCallback;
    private final Map<String, Location> locationMap = new LinkedHashMap<>(  );
    private final ArrayList<Location> locationArray = new ArrayList<>();
    private final FirebaseFirestore db;

    public FirebaseLocationHandler(FirebaseFirestore db){
        this.db = db;
    }
    /**
     * returns locations
     * @param locationID id for the specific location
     * @return the Location being searched for
     */
    public Location getLocation(String locationID){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection( "location" ).whereEqualTo( "locationID", locationID )
                .get().addOnSuccessListener(documentSnapshots -> {
                    if (documentSnapshots.isEmpty()) {
                        Log.d( TAG, "onSuccess: LIST EMPTY" );
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
                        Log.d(TAG, "onSuccess: Got Location");

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
                });
        return locationCallback;
    }

    /**
     * returns all locations
     * @return current Task
     */
    public Task getAllLocations(){
        return db.collection( "location" )
                .get().addOnSuccessListener(documentSnapshots -> {
                    if (documentSnapshots.isEmpty()) {
                        Log.d( TAG, "onSuccess: LIST EMPTY" );
                    } else {
                        List<DocumentSnapshot> retDocs = documentSnapshots.getDocuments();
                        String id;
                        String name;
                        double longitude;
                        double latitude;
                        String type;
                        String phone;
                        String website;
                        String streetAddress;
                        String city;
                        String state;
                        long zip;
                        Log.d(TAG, "onSuccess: Got Locations");

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
                            locationMap.put(Objects.requireNonNull(id), locationCallback);
                        }
                        AllLocations currentLocationInstance = AllLocations.getInstance();
                        currentLocationInstance.setLocationMap( locationMap );
                    }
                });
    }

    

    /**
     * add locations to database
     * @param location location being added
     */
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
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Added location."));
        } else {
            Log.e(TAG, "Entered null location");
        }
    }

}
