package com.track.brachio.donationtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.track.brachio.donationtracker.model.Item;
import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.singleton.AllLocations;
import com.track.brachio.donationtracker.model.singleton.CurrentItem;

import org.w3c.dom.Text;

import java.util.HashMap;

public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_item_detail );
        Item currentItem = CurrentItem.getInstance().getItem();

        TextView nameText = findViewById(R.id.detail_name);
        TextView dateText = findViewById( R.id.detail_date );
        TextView locationText = findViewById( R.id.location_title );
        TextView shortDescriptText = findViewById(R.id.short_description);
        TextView longDescriptText = findViewById(R.id.long_description);
        TextView dollarText = findViewById(R.id.dollar_value);

        nameText.setText(currentItem.getName());
        dateText.setText(currentItem.getDateCreated().toString());
        HashMap<String, Location> allLoc = AllLocations.getInstance().getLocationMap();
        Location loc = allLoc.get(currentItem.getLocation());
        locationText.setText(loc.getName());
        shortDescriptText.setText(currentItem.getShortDescript());
        longDescriptText.setText(currentItem.getLongDescript());
        dollarText.setText(currentItem.getDollarValue() + "");

    }
}
