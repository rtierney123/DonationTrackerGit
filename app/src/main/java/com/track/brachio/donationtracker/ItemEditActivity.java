package com.track.brachio.donationtracker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.Manifest;
import java.io.File;
import android.text.Editable;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.google.protobuf.compiler.PluginProtos;
import com.track.brachio.donationtracker.controller.PersistanceManager;
import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.singleton.AllLocations;
import com.track.brachio.donationtracker.model.singleton.SelectedItem;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import com.track.brachio.donationtracker.model.Item;
import com.track.brachio.donationtracker.model.ItemType;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity for Editing Items
 */
public class ItemEditActivity extends AppCompatActivity {
    //private EditText newLocation;
    private EditText newShortDescription;
    private EditText newLongDescription;
    private EditText newDollarValue;
    private Spinner newItemCategory;
    private EditText newComments;
    private ImageButton newImage;
    private ArrayList<String> comments;
    private PersistanceManager manager;
    private final Activity currentActivity = this;
    private Item currentItem;
    private final static int THUMBNAIL_SIZE = 200;
    private Uri file;
    private List<String> locNames = new ArrayList<>( );
    private Spinner newLocation;
    //private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_edit);

        TextView itemName = findViewById(R.id.editItemNameID);
        TextView dateCreated = findViewById(R.id.editItemDateCreatedID);
        newLocation = findViewById(R.id.editItemLocation);
        newShortDescription = findViewById(R.id.editItemShortDescription);
        newLongDescription = findViewById(R.id.editItemLongDescriptionID);
        newDollarValue = findViewById(R.id.editItemValueID);
        newItemCategory = findViewById(R.id.editItemCategoryID);
        newComments = findViewById(R.id.editItemAddCommentID);
        RecyclerView newCommentsRecyclerView = findViewById(R.id.editItemCommentsID);
        newImage = findViewById(R.id.editItemImage);
        Button addButton = findViewById(R.id.editItemMakeChangesID);
        ImageButton optionButton = findViewById(R.id.item_edit_options);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        //DO WE NEED??
        newCommentsRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        newCommentsRecyclerView.setLayoutManager(layoutManager);

        manager = new PersistanceManager( this );

        ArrayAdapter adapter1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,
                Item.getLegalItemTypes());
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newItemCategory.setAdapter(adapter1);

        List<Location> locs = AllLocations.getInstance().getLocationArray();
        for(Location loc : locs){
            locNames.add(loc.getName());
        }

        ArrayAdapter adapter2 =
                new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                        locNames);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newLocation.setAdapter(adapter2);
        newLocation.setSelection(0);

        //set default value
        SelectedItem selectedItemInstance = SelectedItem.getInstance();
        currentItem = selectedItemInstance.getItem();
        if (currentItem != null) {
            itemName.setText(currentItem.getName());
            if (currentItem.getDateCreated() != null) {
                Date theDateCreated = currentItem.getDateCreated();
                String dateString = theDateCreated.toString();
                dateCreated.setText(dateString);
            }
            newShortDescription.setText(currentItem.getShortDescription());
            newLongDescription.setText(currentItem.getLongDescription());
            double dollarValue = currentItem.getDollarValue();
            String dollarValueString = Double.toString(dollarValue);
            newDollarValue.setText(dollarValueString);
            if (currentItem.getCategory() != null) {
                newItemCategory.setSelection(Item.findItemTypePosition(currentItem.getCategory()));
            }
            //set default for comments
            //newComments.setText(currentItem.getComments());
            //set default for image
        }
        addButton.setOnClickListener(view -> {
            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            Log.d("Edit Item", "Change Made");

            Location currentLoc = locs.get( newLocation.getSelectedItemPosition() );
            String locationEntered = currentLoc.getId();

            Editable shortDescriptionTemp = newShortDescription.getText();
            String shortDescriptionEntered = shortDescriptionTemp.toString();

            Editable longDescriptionTemp = newLongDescription.getText();
            String longDescriptionEntered = longDescriptionTemp.toString();

            Editable dollarValueTemp = newDollarValue.getText();
            String dollarValueEntered = dollarValueTemp.toString();

            Editable commentsTemp = newComments.getText();
            String commentEntered = commentsTemp.toString();

            ItemType itemTypeSelected = (ItemType) newItemCategory.getSelectedItem();

            //image
            if (!locationEntered.isEmpty() &&
                    !locationEntered.equals(currentItem.getLocation())) {
                currentItem.setLocation(locationEntered);
            }


            if (!shortDescriptionEntered.isEmpty() &&
                    !shortDescriptionEntered.equals(currentItem.getShortDescription())) {
                currentItem.setShortDescription(shortDescriptionEntered);
            }

            if (!longDescriptionEntered.isEmpty() &&
                    !longDescriptionEntered.equals(currentItem.getLongDescription())) {
                currentItem.setLongDescription(longDescriptionEntered);
            }

            if (!dollarValueEntered.isEmpty() &&
                    !(Double.parseDouble(dollarValueEntered) == currentItem.getDollarValue())) {
                currentItem.setDollarValue(Double.parseDouble(dollarValueEntered));
            }

            if (!commentEntered.isEmpty()) {
                currentItem.addComment(commentEntered);
            }

            if (!itemTypeSelected.equals(currentItem.getCategory())) {
                currentItem.setCategory(itemTypeSelected);
            }

            Toast.makeText( getApplicationContext(), "Item Edited", Toast.LENGTH_SHORT ).show();
            manager.editItem( currentItem, currentActivity);

        });

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            newImage.setEnabled(false);
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
        newImage.setOnClickListener(this::takePicture);


        Bitmap pic = currentItem.bitmap;

        if (pic != null){
            newImage.setImageBitmap( pic );
        }


        optionButton.setOnClickListener(v -> {
            //Creating the instance of PopupMenu
            PopupMenu popup = new PopupMenu( ItemEditActivity.this, optionButton );
            //Inflating the Popup using xml file
            popup.getMenuInflater()
                    .inflate( R.menu.activity_edit_item_draw, popup.getMenu() );
            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_delete_item:
                        Log.d("Edit Item", "Item deleted");
                        Toast.makeText(getApplicationContext(),
                                "Item Deleted",
                                Toast.LENGTH_SHORT).show();
                        manager.deleteItem(currentItem, currentActivity);
                        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                        return true;
                    case R.id.nav_cancel_item:
                        Log.d("Edit Item", "Edit Item Canceled");
                        Intent intent = new Intent(ItemEditActivity.this,
                                ItemDetailActivity.class);
                        startActivity(intent);
                        return true;
                    default:
                        return false;
                }
            });
            popup.show();
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    && (grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                newImage.setEnabled(true);
            }
        }
    }


    /**
     * picture being taken
     * @param view current view
     */
    private void takePicture(View view){
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
                Bitmap bitmap = null;
                try {
                    bitmap = getThumbnail( file );
                    bitmap = rotateBitmap(bitmap, orientation);
                    //bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SelectedItem selectedItemInstance = SelectedItem.getInstance();
                Item item = selectedItemInstance.getItem();
                File auxFile = new File(file.toString());
                item.setPicture(auxFile);
                item.bitmap = bitmap;
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
    public Bitmap getThumbnail(Uri uri) throws FileNotFoundException, IOException{
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

//    /**
//     * sets comments for array
//     * @param array array being set
//     */
//    public void setCommentsArray(ArrayList<String> array) {
//        comments = array;
//    }


//    /**
//     * @param comm what is populating the recycler view
//     */
//    public void populateRecycleView(ArrayList<String> comm) {
//        comments = comm;
//
//        if (comments != null) {
//            RecyclerView.Adapter adapter = new ItemEditActivity.CommentListAdapter(comments);
//            newCommentsRecyclerView.setAdapter(adapter);
//        } else {
//            Log.d("Comments", "Comments are null");
//        }
//    }

    /**
     * Adapter for Comment List
     */
    private static final class CommentListAdapter extends
            RecyclerView.Adapter<ItemEditActivity
                    .CommentListAdapter.CommentViewHolder> {
        private final List<String> theComments;

        /**
         * Holder for Comment View
         */
        public static final class CommentViewHolder extends RecyclerView.ViewHolder {
            private final TextView commentText;
            private final View v;
            private CommentViewHolder(View v) {
                super(v);
                this.v = v;
                commentText = v.findViewById(R.id.textView14);
            }

            //do I need Bind?

        }

        /**
         * Adapter for comment list
         * @param array array being displayed
         */
        private CommentListAdapter(List<String> array) {
            this.theComments = array;
        }

        @Override
        public ItemEditActivity.CommentListAdapter.CommentViewHolder
        onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_comments_list,
                            parent, false);

            return new
                    CommentViewHolder(v);
        }

        //idk if this is right
        @Override
        public void onBindViewHolder(
                ItemEditActivity.CommentListAdapter.CommentViewHolder holder,
                int position) {
            holder.commentText.setText(theComments.get(position));
        }

        @Override
        public int getItemCount() {
            return theComments.size();
        }



    }
}
