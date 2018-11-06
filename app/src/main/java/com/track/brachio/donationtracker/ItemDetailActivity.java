package com.track.brachio.donationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.track.brachio.donationtracker.model.Item;
import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.singleton.AllLocations;
import com.track.brachio.donationtracker.model.singleton.CurrentItem;

import java.util.Map;
import java.util.Objects;

/**
 * Activity for the Details of the Item activity
 */
@SuppressWarnings("FeatureEnvy")
public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_item_detail );
        Item currentItem = CurrentItem.getInstance().getItem();

        TextView nameText = findViewById(R.id.detail_name);
        TextView dateText = findViewById( R.id.detail_date );
        TextView locationText = findViewById( R.id.detail_location);
        TextView shortDescriptText = findViewById(R.id.short_description);
        TextView longDescriptText = findViewById(R.id.long_description);
        TextView dollarText = findViewById(R.id.dollar_value);
        TextView categoryText = findViewById(R.id.item_category);
        ImageView itemPic = findViewById(R.id.detail_image);


        nameText.setText(currentItem.getName());
        dateText.setText(currentItem.getDateCreated().toString());
        Map<String, Location> allLoc = AllLocations.getInstance().getLocationMap();
        Location loc = allLoc.get(currentItem.getLocation());
        locationText.setText(Objects.requireNonNull(loc).getName());
        shortDescriptText.setText(currentItem.getShortDescription());
        longDescriptText.setText(currentItem.getLongDescription());
        dollarText.setText(currentItem.getDollarValue() + "");
        categoryText.setText(currentItem.getCategory().toString());

        ImageButton editButton = findViewById( R.id.edti_item_button);
        editButton.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //putting it to donator main activity for now
                Intent intent = new Intent(ItemDetailActivity.this, ItemEditActivity.class);
                startActivity(intent);
            }
        });

        if (currentItem.getPicture() != null){
            itemPic.setImageBitmap( currentItem.getPicture() );
        }

    }
}
