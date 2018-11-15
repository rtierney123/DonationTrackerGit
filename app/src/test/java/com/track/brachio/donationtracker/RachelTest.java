package com.track.brachio.donationtracker;

import com.track.brachio.donationtracker.model.Address;
import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.singleton.AllLocations;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;


//Tests for getAllLocations method in FirebaseLocationHandler
public class RachelTest {

    //@RunWith(PowerMockRunner.class)

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test public void testGetAllLocationsWithValue() {
        //FirebaseLocationHandler t = new FirebaseLocationHandler(databaseMock);
        Address address = new Address("123 Fake Ln", "Atlanta", "GA", 30360);
        Location location = new Location("1", "Center", 50, 60, "Store",
                "12345678", "mywebsite.com", address);

       // t.addLocation(location);
        //t.getAllLocations();

        AllLocations allLocations = AllLocations.getInstance();
        Map<String, Location> mapLoc = allLocations.getLocationMap();
        List<String> keys = new ArrayList(mapLoc.keySet());

        assertEquals(keys.size(), 1);
    }

    @Test public void testGetAllLocationsWithEmpty() {
        //FirebaseLocationHandler t = new FirebaseLocationHandler(databaseMock);

        //t.getAllLocations();

        AllLocations allLocations = AllLocations.getInstance();
        Map<String, Location> mapLoc = allLocations.getLocationMap();
        List<String> keys = new ArrayList(mapLoc.keySet());

        assertEquals(keys.size(), 0);

    }
}
