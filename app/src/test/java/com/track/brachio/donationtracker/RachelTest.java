package com.track.brachio.donationtracker;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.track.brachio.donationtracker.model.Address;
import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.database.FirebaseLocationHandler;
import com.track.brachio.donationtracker.model.singleton.AllLocations;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.when;


//Tests for getAllLocations method in FirebaseLocationHandler
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseFirestore.class, CollectionReference.class})
public class RachelTest {

    @Mock
    private Task<Void> mockVoidTask;

    @Mock
    private DocumentReference documentReference;

    @Mock
    private DocumentReference emptyDocumentReference;

    @Mock
    private CollectionReference collectionReference;

    @Mock
    private CollectionReference emptyCollectionReference;

    @Mock
    private Query queryReference;

    @Mock
    private Query emptyQueryReference;

    @Mock
    private DocumentSnapshot documentSnapshot;

    @Mock
    private DocumentSnapshot emptyDocumentSnapshot;

    @Mock
    private QuerySnapshot querySnapshot;
    @Mock
    private QuerySnapshot emptyQuerySnapshot;

    @Mock
    private Task<DocumentSnapshot> emptyDocumentSnapshotTask;

    @Mock
    private Task<DocumentSnapshot> documentSnapshotTask;

    @Mock
    private Task<DocumentReference> documentRefTask;

    @Mock
    private Task<QuerySnapshot> queryResultTask;

    @Mock
    private Task<QuerySnapshot> emptyQueryResultTask;

    @Mock
    private ListenerRegistration registration;
    @Mock
    private FirebaseFirestore firestoreMock;

    private HashMap<String, Object> updateMap = new HashMap<>();
    private String locStr = "location";

    public void initializeDatabase(){
        MockitoAnnotations.initMocks(this);

        when(documentReference.get()).thenReturn(documentSnapshotTask);
        when(emptyDocumentReference.get()).thenReturn(emptyDocumentSnapshotTask);
        when(collectionReference.get()).thenReturn(queryResultTask);
        when(emptyCollectionReference.get()).thenReturn(emptyQueryResultTask);
        when(queryReference.get()).thenReturn(queryResultTask);
        when(emptyQueryReference.get()).thenReturn(emptyQueryResultTask);
        when(documentReference.delete()).thenReturn(mockVoidTask);
        when(documentReference.update(updateMap)).thenReturn(mockVoidTask);
        when(documentSnapshot.exists()).thenReturn(true); //This snapshots exist
        when(documentSnapshot.exists()).thenReturn(true); //This snapshots exist
        when(emptyDocumentSnapshot.exists()).thenReturn(false); //This snapshots exist
        when(querySnapshot.isEmpty()).thenReturn(false);
        when(querySnapshot.iterator()).thenAnswer(new Answer<Iterator<DocumentSnapshot>>() {
            @Override
            public Iterator<DocumentSnapshot> answer(InvocationOnMock invocation) throws Throwable {
                return Collections.singletonList(documentSnapshot).iterator();
            }
        });
        when(emptyQuerySnapshot.isEmpty()).thenReturn(true);

        when(firestoreMock.collection("location")).thenReturn(collectionReference);
        when(firestoreMock.getInstance()).thenReturn(firestoreMock);


        /*
        CollectionReference mockedDatabaseReference = Mockito.mock(CollectionReference.class);

        firestoreMock = Mockito.mock(FirebaseFirestore.class);
        when(firestoreMock.collection( "location/" )).thenReturn(mockedDatabaseReference);

        PowerMockito.mockStatic(FirebaseFirestore.class);
        when(FirebaseFirestore.getInstance()).thenReturn(firestoreMock);
        */


    }

    @Test
    public void testGetAllLocationsWithValue() {
        FirebaseLocationHandler t = new FirebaseLocationHandler(firestoreMock);
        Address address = new Address("123 Fake Ln", "Atlanta", "GA", 30360);
        Location location = new Location("1", "Center", 50, 60, "Store",
                "12345678", "mywebsite.com", address);

        t.addLocation(location);
        t.getAllLocations();

        AllLocations allLocations = AllLocations.getInstance();
        Map<String, Location> mapLoc = allLocations.getLocationMap();
        List<String> keys = new ArrayList(mapLoc.keySet());

        assertEquals(keys.size(), 1);
    }

    @Test
    public void testGetAllLocationsWithEmpty() {
        FirebaseLocationHandler t = new FirebaseLocationHandler(firestoreMock);

        t.getAllLocations();

        AllLocations allLocations = AllLocations.getInstance();
        Map<String, Location> mapLoc = allLocations.getLocationMap();
        List<String> keys = new ArrayList(mapLoc.keySet());

        assertEquals(keys.size(), 0);

    }

}
