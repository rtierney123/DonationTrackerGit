package com.track.brachio.donationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.google.type.Date;
import com.track.brachio.donationtracker.controller.MainActivity;
import com.track.brachio.donationtracker.model.Item;
import com.track.brachio.donationtracker.model.database.FirebaseItemHandler;
import com.track.brachio.donationtracker.model.database.FirebaseUserHandler;

import java.util.Calendar;

public class DonatorMainActivity extends MainActivity {

    private Button donGoToLocationsButton;
    private Button donMainLogout;
    //TODO delete when EditItemListActivity
    private Button testEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_donator_main );

        //Item a = new Item("baseball", today, "1", 5, "sport");
        //Item b= new Item("couch", today, "2", 5, "furniture");

        //FirebaseItemHandler handler = new FirebaseItemHandler();

        //handler.addItem(a);
        //handler.addItem(b);

        donGoToLocationsButton = (Button) findViewById(R.id.donLocationButton);
        donGoToLocationsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(DonatorMainActivity.this, LocationListActivity.class);
                startActivity(intent);
            }
        });

        donMainLogout = (Button) findViewById(R.id.donMainLogout);
        setLogoutButton( donMainLogout );

        testEdit = (Button) findViewById(R.id.testEditableItem);
        testEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(DonatorMainActivity.this, EditableItemListActivity.class);
                startActivity(intent);
            }
        });
    }

}
