package com.track.brachio.donationtracker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.text.Editable;

import com.track.brachio.donationtracker.controller.PersistanceManager;
import com.track.brachio.donationtracker.model.Item;
import com.track.brachio.donationtracker.model.ItemType;
import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.singleton.AllLocations;
import com.track.brachio.donationtracker.model.singleton.SelectedItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Activity for Adding Item Activity
 */
public class ItemAddActivity extends AppCompatActivity {
    private EditText itemName;
    //private EditText location;
    private EditText shortDescription;
    private EditText longDescription;
    private EditText dollarValue;
    private Spinner itemCategory;
    private EditText comment;
    private ImageButton newImage;
    private final static int THUMBNAIL_SIZE = 200;
    private Uri file;
    private Bitmap bitmap;
    private List<String> locNames = new ArrayList<>( );
    private Spinner locations;

    private final Activity currentActivity = this;
    //Item currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemName = findViewById(R.id.addItemNameID);
        locations = findViewById(R.id.addItemLocSpinner);
        shortDescription = findViewById(R.id.addItemShortDescID);
        longDescription = findViewById(R.id.addItemLongDescID);
        dollarValue = findViewById(R.id.addItemDollarValue);
        itemCategory = findViewById(R.id.addItemCategoryID);
        comment = findViewById(R.id.addItemCommentID);
        newImage = findViewById( R.id.addItemImage );
        Button addButton = findViewById(R.id.addItemAddID);
        Button cancelButton = findViewById(R.id.addItemCancelButton);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        PersistanceManager manager = new PersistanceManager( this );

        ArrayAdapter adapter1 =
                new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                        Item.getLegalItemTypes());
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemCategory.setAdapter(adapter1);

        List<Location> locs = AllLocations.getInstance().getLocationArray();
        for(Location loc : locs){
            locNames.add(loc.getName());
        }

        ArrayAdapter adapter2 =
                new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                       locNames);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locations.setAdapter(adapter2);
        locations.setSelection(0);


        addButton.setOnClickListener(view -> {
            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            Log.d("Add Item", "Item added");

            Editable nameTemp = itemName.getText();
            String nameEntered = nameTemp.toString();

            int id = locations.getSelectedItemPosition();
            String locationEntered = locs.get(id).getId();

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
                if (bitmap != null){
                    newItem.bitmap = bitmap;
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


    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){

        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                newImage.setImageURI(file);
                ExifInterface exif = null;
                try {
                    exif = new ExifInterface(file.getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);
               bitmap = null;
                try {
                    bitmap = getThumbnail( file );
                    bitmap = rotateBitmap(bitmap, orientation);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                newImage.setImageBitmap(bitmap);
            }
        }

    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * acts as onClick REQUIRED
     * @param view view being passed in
     */
    public void takePicture(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 100);
        }
    }

    /**
     * returns outPutMediaFile
     * @return the OutputMediaFile
     */
    private static File getOutputMediaFile () {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + ".jpg");
    }
    public Bitmap getThumbnail(Uri uri) throws FileNotFoundException, IOException {
        InputStream input = this.getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();

        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1)) {
            return null;
        }

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > THUMBNAIL_SIZE) ? (originalSize / THUMBNAIL_SIZE) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//
        input = this.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }


}
