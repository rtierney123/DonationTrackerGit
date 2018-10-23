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
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.provider.MediaStore;
import android.graphics.Bitmap;

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
import com.track.brachio.donationtracker.model.ItemType;

public class EditItemActivity extends AppCompatActivity {
    private TextView itemName;
    private TextView dateCreated;
    private EditText newLocation;
    private EditText newShortDescription;
    private EditText newLongDescription;
    private EditText newDollarValue;
    private Spinner newItemCategory;
    private EditText newComments;
    private ImageView newimage;
    private Button cancelButton;
    private Button addButton;
    private Button deleteButton;

    Item currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_edit);

        itemName = (TextView) findViewById(R.id.editItemNameID);
        dateCreated = (TextView) findViewById(R.id.editItemDateCreatedID);
        newLocation = (EditText) findViewById(R.id.editItemLocationID);
        newShortDescription = (EditText) findViewById(R.id.editItemShortDescription);
        newLongDescription = (EditText) findViewById(R.id.editItemLongDescriptionID);
        newDollarValue = (EditText) findViewById(R.id.editItemValueID);
        newItemCategory = (Spinner) findViewById(R.id.editItemCategoryID);
        newComments = (EditText) findViewById(R.id.editItemCommentsID);
        newimage = (ImageView) findViewById(R.id.editItemImageID);
        cancelButton = (Button) findViewById(R.id.editItemCancelButton);
        addButton = (Button) findViewById(R.id.editItemMakeChangesID);
        deleteButton = (Button) findViewById(R.id.editItemDeleteButton);


        FirebaseLocationHandler handler = new FirebaseLocationHandler();

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Item.getLegalItemTypes());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newItemCategory.setAdapter(adapter);

        //set default value


        //should item category be spinner?

        currentItem = CurrentItem.getInstance().getItem();
        if (currentItem != null) {
            itemName.setText(currentItem.getName());
            if (currentItem.getDateCreated() != null) {
                dateCreated.setText(currentItem.getDateCreated().toString());
            }
            newLocation.setText(currentItem.getLocation());
            newShortDescription.setText(currentItem.getShortDescript());
            newLongDescription.setText(currentItem.getLongDescript());
            newDollarValue.setText(Double.toString(currentItem.getDollarValue()));
            //set default for item category
            //set default for comments
            //newComments.setText(currentItem.getComments());
            //set default for image
        }
//        newimage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int REQUEST_IMAGE_CAPTURE = 100;
//                //private void dispatchTakePictureIntent(View view) {
//                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//                    }
//                //}
//                protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//                    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//                        Bundle extras = data.getExtras();
//                        Bitmap imageBitmap = (Bitmap) extras.get("data");
//                        newimage.setImageBitmap(imageBitmap);
//                    }
//                }
//
//            }
//        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Edit Item", "Change Made");
                String locationEntered = newLocation.getText().toString();
                String shortDescriptionEntered = newShortDescription.getText().toString();
                String longDescriptionEntered = newLongDescription.getText().toString();
                String dollarValueEntered = newDollarValue.getText().toString();
                String commentEntered = newComments.getText().toString();
                ItemType itemTypeSelected = (ItemType) newItemCategory.getSelectedItem();


                //spinner
                //image
                if (!locationEntered.isEmpty() && !locationEntered.equals(currentItem.getLocation())) {
                    currentItem.setLocation(locationEntered);
                }

                if (!shortDescriptionEntered.isEmpty() && !shortDescriptionEntered.equals(currentItem.getShortDescript())) {
                    currentItem.setShortDescript(shortDescriptionEntered);
                }

                if (!longDescriptionEntered.isEmpty() && !longDescriptionEntered.equals(currentItem.getLongDescript())) {
                    currentItem.setLongDescript(longDescriptionEntered);
                }

                if (!dollarValueEntered.isEmpty() && !(Double.parseDouble(dollarValueEntered) == currentItem.getDollarValue())) {
                    currentItem.setDollarValue(Double.parseDouble(dollarValueEntered));
                }

                if (!commentEntered.isEmpty()) {
                    currentItem.addComment(commentEntered);
                }

                if (!itemTypeSelected.equals(currentItem.getCategory())) {
                    currentItem.setCategory(itemTypeSelected);
                }

                Toast.makeText( getApplicationContext(), "Item Edited", Toast.LENGTH_SHORT ).show();

                Log.d("Edit Item", "Item edited");
                Intent intent = new Intent(EditItemActivity.this, EditableItemListActivity.class);
                startActivity(intent);

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Edit Item", "Edit Item Canceled");

                Intent intent = new Intent(EditItemActivity.this, EditableItemListActivity.class);
                startActivity(intent);
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Log.d("Edit Item", "Item deleted");
                Intent intent = new Intent(EditItemActivity.this, EditableItemListActivity.class);
                startActivity(intent);
            }

         });

    }
}
