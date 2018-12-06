package com.track.brachio.donationtracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.protobuf.compiler.PluginProtos;
import com.track.brachio.donationtracker.model.Item;
import com.track.brachio.donationtracker.model.ItemType;
import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.User;
import com.track.brachio.donationtracker.model.UserType;
import com.track.brachio.donationtracker.model.singleton.AllLocations;
import com.track.brachio.donationtracker.model.singleton.CurrentUser;
import com.track.brachio.donationtracker.model.singleton.SelectedItem;


import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * Activity for the Details of the Item activity
 */
public class ItemDetailActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_item_detail );
        SelectedItem currentInstance = SelectedItem.getInstance();
        Item currentItem = currentInstance.getItem();

        TextView nameText = findViewById(R.id.detail_name);
        TextView dateText = findViewById( R.id.detail_date );
        TextView locationText = findViewById( R.id.detail_location);
        TextView shortDescriptionText = findViewById(R.id.short_description);
        TextView longDescriptionText = findViewById(R.id.long_description);
        TextView dollarText = findViewById(R.id.dollar_value);
        TextView categoryText = findViewById(R.id.item_category);
        ImageView itemPic = findViewById(R.id.detail_image);


        nameText.setText(currentItem.getName());
        Date dateCreated = currentItem.getDateCreated();
        if (dateCreated != null){
            String dateCreatedString = dateCreated.toString();
            dateText.setText(dateCreatedString);
        }

        AllLocations currentLocationsInstance = AllLocations.getInstance();
        Map<String, Location> allLoc = currentLocationsInstance.getLocationMap();
        Location loc = allLoc.get(currentItem.getLocation());
        Location obj = Objects.requireNonNull(loc);
        String theName = obj.getName();
        locationText.setText(theName);
        shortDescriptionText.setText(currentItem.getShortDescription());
        longDescriptionText.setText(currentItem.getLongDescription());
        dollarText.setText(currentItem.getDollarValue() + "");

        ItemType type = currentItem.getCategory();
        String typeString = type.toString();
        categoryText.setText(typeString);

        ImageButton editButton = findViewById( R.id.edit_item_button);
        editButton.setOnClickListener (view -> {
            Intent intent = new Intent(ItemDetailActivity.this, ItemEditActivity.class);
            startActivity(intent);
        });

        Bitmap pic = currentItem.bitmap;
        if (pic != null){
            itemPic.setImageBitmap(  pic );
        }

        User currentUser = CurrentUser.getInstance().getUser();
        if (currentUser.getUserType() == UserType.Guest
                || currentUser.getUserType() == UserType.Donator){
            findViewById( R.id.edit_item_button).setVisibility( View.GONE );
        }
    }
}
