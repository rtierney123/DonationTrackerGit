package com.track.brachio.donationtracker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.Manifest;
import java.io.File;

import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.track.brachio.donationtracker.controller.PersistanceManager;
import com.track.brachio.donationtracker.model.singleton.CurrentItem;

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
@SuppressWarnings({"SpellCheckingInspection", "FeatureEnvy"})
public class ItemEditActivity extends AppCompatActivity {
    private EditText newLocation;
    private EditText newShortDescription;
    private EditText newLongDescription;
    private EditText newDollarValue;
    private Spinner newItemCategory;
    private RecyclerView newCommentsRecyclerView;
    private RecyclerView.Adapter adapter;
    private EditText newComments;
    private ImageButton newImage;
    private Button cancelButton;
    private Button addButton;
    private Button deleteButton;
    private ArrayList<String> comments;
    private PersistanceManager manager;
    private final Activity currentActivity = this;
    private Item currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_edit);

        TextView itemName = (TextView) findViewById(R.id.editItemNameID);
        TextView dateCreated = findViewById(R.id.editItemDateCreatedID);
        newLocation = findViewById(R.id.editItemLocationID);
        newShortDescription = findViewById(R.id.editItemShortDescription);
        newLongDescription = (EditText) findViewById(R.id.editItemLongDescriptionID);
        newDollarValue = (EditText) findViewById(R.id.editItemValueID);
        newItemCategory = (Spinner) findViewById(R.id.editItemCategoryID);
        newComments = (EditText) findViewById(R.id.editItemAddCommentID);
        newCommentsRecyclerView = (RecyclerView) findViewById(R.id.editItemCommentsID);
        newImage = (ImageButton) findViewById(R.id.editItemImage);
        cancelButton = (Button) findViewById(R.id.editItemCancelButton);
        addButton = (Button) findViewById(R.id.editItemMakeChangesID);
        deleteButton = (Button) findViewById(R.id.editItemDeleteButton);

        //DO WE NEED??
        newCommentsRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        newCommentsRecyclerView.setLayoutManager(layoutManager);

        manager = new PersistanceManager( this );

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,
                Item.getLegalItemTypes());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newItemCategory.setAdapter(adapter);

        //set default value
        currentItem = CurrentItem.getInstance().getItem();
        if (currentItem != null) {
            itemName.setText(currentItem.getName());
            if (currentItem.getDateCreated() != null) {
                dateCreated.setText(currentItem.getDateCreated().toString());
            }
            newLocation.setText(currentItem.getLocation());
            newShortDescription.setText(currentItem.getShortDescription());
            newLongDescription.setText(currentItem.getLongDescription());
            newDollarValue.setText(Double.toString(currentItem.getDollarValue()));
            if (currentItem.getCategory() != null) {
                newItemCategory.setSelection(Item.findItemTypePosition(currentItem.getCategory()));
            }
            //set default for comments
            //newComments.setText(currentItem.getComments());
            //set default for image
        }
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

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Edit Item", "Edit Item Canceled");

                Intent intent = new Intent(ItemEditActivity.this, ItemListActivityTemp.class);
                startActivity(intent);
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Log.d("Edit Item", "Item deleted");
                Toast.makeText( getApplicationContext(),
                        "Item Deleted",
                        Toast.LENGTH_SHORT ).show();
                manager.deleteItem(currentItem, currentActivity);

            }

         });
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            newImage.setEnabled(false);
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
        newImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture(view);
            }
        });

        newImage.setImageBitmap( currentItem.getPicture() );

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
        Uri file;
        static final int REQUEST_IMAGE_CAPTURE = 1;

    /**
     * picture being taken
     * @param view current view
     */
    public void takePicture(View view){
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
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), file);
                        //bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Item item = CurrentItem.getInstance().getItem();
                    item.setPicture(bitmap);
                    newImage.setImageBitmap(bitmap);
                }
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

    /**
     * sets comments for array
     * @param array array being set
     */
    public void setCommentsArray(ArrayList<String> array) {
        comments = array;
    }

    /**
     * sets up Recycle view
     * @param view current view
     */
    public void setUpRecyclerView(RecyclerView view) {

    }

    /**
     * populates the recyclerview
     * @param comm what is populating the recycler view
     */
    public void populateRecycleView(ArrayList<String> comm) {
        comments = comm;

        if (comments != null) {
            adapter = new ItemEditActivity.CommentListAdapter(comments);
            newCommentsRecyclerView.setAdapter(adapter);
        } else {
            Log.d("Comments", "Comments are null");
        }
    }

    /**
     * Adapter for Comment List
     */
    private static class CommentListAdapter extends
            RecyclerView.Adapter<ItemEditActivity
                    .CommentListAdapter.CommentViewHolder> {
        private final List<String> theComments;
        private View view;

        /**
         * Holder for Comment View
         */
        public static class CommentViewHolder extends RecyclerView.ViewHolder {
            private TextView commentText;
            private View v;
            public CommentViewHolder(View v) {
                super(v);
                this.v = v;
                commentText = (TextView) v.findViewById(R.id.textView14);
            }

            //do I need Bind?

        }

        /**
         * Adapter for comment list
         * @param array array being displayed
         */
        public CommentListAdapter(List<String> array) {
            this.theComments = array;
        }

        @Override
        public ItemEditActivity.CommentListAdapter.CommentViewHolder
        onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_comments_list,
                            parent, false);
            view = v;
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

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }
    }
}
