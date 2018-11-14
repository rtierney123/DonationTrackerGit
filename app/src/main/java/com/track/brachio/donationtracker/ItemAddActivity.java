package com.track.brachio.donationtracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.text.Editable;

import com.track.brachio.donationtracker.controller.PersistanceManager;
import com.track.brachio.donationtracker.model.Item;
import com.track.brachio.donationtracker.model.ItemType;

import java.util.Date;

/**
 * Activity for Adding Item Activity
 */
@SuppressWarnings({"SpellCheckingInspection", "FeatureEnvy"})
public class ItemAddActivity extends AppCompatActivity {
    private EditText itemName;
    private EditText location;
    private EditText shortDescription;
    private EditText longDescription;
    private EditText dollarValue;
    private Spinner itemCategory;
    private EditText comment;

    private final Activity currentActivity = this;
    //Item currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemName = findViewById(R.id.addItemNameID);
        location = findViewById(R.id.addItemLocationID);
        shortDescription = findViewById(R.id.addItemShortDescID);
        longDescription = findViewById(R.id.addItemLongDescID);
        dollarValue = findViewById(R.id.addItemDollarValue);
        itemCategory = findViewById(R.id.addItemCategoryID);
        comment = findViewById(R.id.addItemCommentID);
        Button addButton = findViewById(R.id.addItemAddID);
        Button cancelButton = findViewById(R.id.addItemCancelButton);
        //ImageButton newimage = findViewById(R.id.addItemImage);

        PersistanceManager manager = new PersistanceManager( this );

        @SuppressWarnings("unchecked")
        ArrayAdapter adapter =
                new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                        Item.getLegalItemTypes());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemCategory.setAdapter(adapter);


        addButton.setOnClickListener(view -> {
            Log.d("Add Item", "Item added");

            Editable nameTemp = itemName.getText();
            String nameEntered = nameTemp.toString();

            Editable locationTemp = location.getText();
            String locationEntered = locationTemp.toString();

            Editable shortTemp = shortDescription.getText();
            String sDescriptionEntered = shortTemp.toString();

            Editable longTemp = longDescription.getText();
            String lDescriptionEntered = longTemp.toString();

            Editable dollarTemp = dollarValue.getText();
            String dollarEntered = dollarTemp.toString();

            ItemType itemTypeSelected = (ItemType) itemCategory.getSelectedItem();

            Editable commentTemp = comment.getText();
            String commentEntered = commentTemp.toString();

            String key = "key";
            Date theDate = new Date();
            //WHAT IS THE KEY

            if (!nameEntered.isEmpty() &&
                    !locationEntered.isEmpty() &&
                    !dollarEntered.isEmpty()) {
                Item newItem = new Item(key, nameEntered, theDate,
                        locationEntered,
                        Double.parseDouble(dollarEntered),
                        itemTypeSelected.toString());
                if (!sDescriptionEntered.isEmpty()) {
                    newItem.setShortDescription(sDescriptionEntered);
                }
                if (!lDescriptionEntered.isEmpty()) {
                    newItem.setLongDescription(lDescriptionEntered);
                }
                if (!commentEntered.isEmpty()) {
                    newItem.addComment(commentEntered);
                }
                manager.addItem(newItem, currentActivity);
                Toast.makeText( ItemAddActivity.this, "Item Added", Toast.LENGTH_SHORT ).show();
            } else {
                Toast.makeText( ItemAddActivity.this,
                        "Must fill in name, location, and amount",
                        Toast.LENGTH_SHORT ).show();
            }
        });


        cancelButton.setOnClickListener(view -> {
            Log.d("Add Item", "Add Item Canceled");
            Intent intent = new Intent(ItemAddActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }


}
