package com.track.brachio.donationtracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import com.track.brachio.donationtracker.controller.PersistanceManager;
import com.track.brachio.donationtracker.model.Item;
import com.track.brachio.donationtracker.model.ItemType;

import java.util.Date;

public class AddItemActivity  extends AppCompatActivity {
    private EditText itemName;
    private EditText location;
    private EditText shortDescription;
    private EditText longDescription;
    private EditText dollarValue;
    private Spinner itemCategory;
    private EditText comment;
    private Button addButton;
    private Button cancelButton;
    private Activity currentActivity = this;
    Item currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemName = (EditText) findViewById(R.id.addItemNameID);
        location = (EditText) findViewById(R.id.addItemLocationID);
        shortDescription = (EditText) findViewById(R.id.addItemShortDescID);
        longDescription = (EditText) findViewById(R.id.addItemLongDescID);
        dollarValue = (EditText) findViewById(R.id.addItemDollarValue);
        itemCategory = (Spinner) findViewById(R.id.addItemCategoryID);
        comment = (EditText) findViewById(R.id.addItemCommentID);
        addButton = (Button) findViewById(R.id.addItemAddID);
        cancelButton = (Button) findViewById(R.id.addItemCancelButton);

        PersistanceManager manager = new PersistanceManager( this );

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Item.getLegalItemTypes());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemCategory.setAdapter(adapter);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Add Item", "Item added");
                String nameEntered = itemName.getText().toString();
                String locationEntered = location.getText().toString();
                String sDescriptionEntered = shortDescription.getText().toString();
                String lDescriptionEntered = longDescription.getText().toString();
                String dollarEntered = dollarValue.getText().toString();
                ItemType itemTypeSelected = (ItemType) itemCategory.getSelectedItem();
                String commentEntered = comment.getText().toString();
                String key = "key";
                Date theDate = new Date();
                //WHAT IS THE KEY

                if (!nameEntered.isEmpty() && !locationEntered.isEmpty() && !dollarEntered.isEmpty()) {
                    Item newItem = new Item(key, nameEntered, theDate, locationEntered, Double.parseDouble(dollarEntered),
                            itemTypeSelected.toString());
                    if (!sDescriptionEntered.isEmpty()) {
                        newItem.setShortDescript(sDescriptionEntered);
                    }
                    if (!lDescriptionEntered.isEmpty()) {
                        newItem.setLongDescript(lDescriptionEntered);
                    }
                    if (!commentEntered.isEmpty()) {
                        newItem.addComment(commentEntered);
                    }
                    manager.addItem(newItem, currentActivity);
                    Toast.makeText( AddItemActivity.this, "Item Added", Toast.LENGTH_SHORT ).show();
                } else {
                    Toast.makeText( AddItemActivity.this, "Must fill in name, location, and amount", Toast.LENGTH_SHORT ).show();
                }
            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Add Item", "Add Item Canceled");
                Intent intent = new Intent(AddItemActivity.this, ItemListActivity.class);
                startActivity(intent);
            }
        });


    }

}