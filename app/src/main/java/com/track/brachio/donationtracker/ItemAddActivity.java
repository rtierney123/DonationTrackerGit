package com.track.brachio.donationtracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import com.track.brachio.donationtracker.controller.PersistanceManager;
import com.track.brachio.donationtracker.model.Item;
import com.track.brachio.donationtracker.model.ItemType;

import java.util.Date;

@SuppressWarnings("SpellCheckingInspection")
public class ItemAddActivity extends AppCompatActivity {
    private EditText itemName;
    private EditText location;
    private EditText shortDescription;
    private EditText longDescription;
    private EditText dollarValue;
    private Spinner itemCategory;
    private EditText comment;
    private Button cancelButton;
    private ImageButton newimage;

    private final Activity currentActivity = this;
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
        Button addButton = (Button) findViewById(R.id.addItemAddID);
        cancelButton = (Button) findViewById(R.id.addItemCancelButton);
        newimage = (ImageButton) findViewById(R.id.addItemImage);

        PersistanceManager manager = new PersistanceManager( this );

        ArrayAdapter<String> adapter =
                new ArrayAdapter(this,android.R.layout.simple_spinner_item,
                        Item.getLegalItemTypes());
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
            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Add Item", "Add Item Canceled");
                Intent intent = new Intent(ItemAddActivity.this, ItemListActivity.class);
                startActivity(intent);
            }
        });

//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            newImage.setEnabled(false);
//            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
//        }
//        newImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                takePicture(view);
//            }
//        });
//
//        newImage.setImageBitmap(currentItem.getPicture() );
    }
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (requestCode == 0) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
//                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                newImage.setEnabled(true);
//            }
//        }
//    }
//    Uri file;
//    static final int REQUEST_IMAGE_CAPTURE = 1;
//    public void takePicture(View view){
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        file = Uri.fromFile(getOutputMediaFile());
//        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, file);
//        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, 100);
//        }
//    }
//    @Override
//    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
//        if (requestCode == 100) {
//            if (resultCode == RESULT_OK) {
//                newImage.setImageURI(file);
//                Bitmap bitmap = null;
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), file);
//                    //bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Item item = CurrentItem.getInstance().getItem();
//                item.setPicture(bitmap);
//                newImage.setImageBitmap(bitmap);
//            }
//        }
//    }
//    private static File getOutputMediaFile () {
//        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES), "CameraDemo");
//
//        if (!mediaStorageDir.exists()) {
//            if (!mediaStorageDir.mkdirs()) {
//                return null;
//            }
//        }
//        return new File(mediaStorageDir.getPath() + File.separator +
//                "IMG_" + ".jpg");
//    }

}
