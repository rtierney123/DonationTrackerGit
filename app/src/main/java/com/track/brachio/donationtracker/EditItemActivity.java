package com.track.brachio.donationtracker;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ImageView;
import android.widget.TextView;

import com.track.brachio.donationtracker.model.User;
import com.track.brachio.donationtracker.model.UserType;
import com.track.brachio.donationtracker.model.database.FirebaseLocationHandler;
import com.track.brachio.donationtracker.model.singleton.CurrentLocation;
import com.track.brachio.donationtracker.model.database.FirebaseUserHandler;
import com.track.brachio.donationtracker.model.singleton.CurrentUser;
import com.track.brachio.donationtracker.model.singleton.CurrentItem;
import com.track.brachio.donationtracker.model.LocationType;
import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.Item;

public class EditItemActivity extends AppCompatActivity {
    private EditText newLocation;
    private EditText newShortDescription;
    private EditText newLongDescription;
    private EditText newDollarValue;
    private Spinner newItemCategory;
    private EditText newComments;
    private ImageView newimage;

    Item currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        setContentView(R.layout.activity_item_edit);

        newLocation = (EditText) findViewById(R.id.editItemLocationID);
        newShortDescription = (EditText) findViewById(R.id.editItemShortDescription);
        newLongDescription = (EditText) findViewById(R.id.editItemLongDescriptionID);
        newDollarValue = (EditText) findViewById(R.id.editItemValueID);
        newItemCategory = (Spinner) findViewById(R.id.editItemCategoryID);
        newComments = (EditText) findViewById(R.id.editItemCommentsID);
        newimage = (ImageView) findViewById(R.id.editItemImageID);
    }
}
