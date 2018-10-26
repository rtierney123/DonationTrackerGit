package com.track.brachio.donationtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.Manifest;
import java.io.File;
import android.hardware.Camera;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageButton;

import com.track.brachio.donationtracker.controller.PersistanceManager;
import com.track.brachio.donationtracker.model.User;
import com.track.brachio.donationtracker.model.UserType;
import com.track.brachio.donationtracker.model.database.FirebaseItemHandler;
import com.track.brachio.donationtracker.model.singleton.CurrentLocation;
import com.track.brachio.donationtracker.model.database.FirebaseUserHandler;
import com.track.brachio.donationtracker.model.singleton.CurrentUser;
import com.track.brachio.donationtracker.model.singleton.CurrentItem;
import com.track.brachio.donationtracker.model.LocationType;
import com.track.brachio.donationtracker.model.Location;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import com.track.brachio.donationtracker.model.Item;
import com.track.brachio.donationtracker.model.ItemType;
import android.view.ViewGroup;
import java.util.ArrayList;

public class EditItemActivity extends AppCompatActivity {
    private TextView itemName;
    private TextView dateCreated;
    private EditText newLocation;
    private EditText newShortDescription;
    private EditText newLongDescription;
    private EditText newDollarValue;
    private Spinner newItemCategory;
    private RecyclerView newCommentsRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private EditText newComments;
    private ImageButton newimage;
    private Button cancelButton;
    private Button addButton;
    private Button deleteButton;
    private ArrayList<String> comments;
    private PersistanceManager manager;
    private Activity currentActivity = this;
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
        newComments = (EditText) findViewById(R.id.editItemAddCommentID);
        newCommentsRecyclerView = (RecyclerView) findViewById(R.id.editItemCommentsID);
        newimage = (ImageButton) findViewById(R.id.editItemImage);
        cancelButton = (Button) findViewById(R.id.editItemCancelButton);
        addButton = (Button) findViewById(R.id.editItemMakeChangesID);
        deleteButton = (Button) findViewById(R.id.editItemDeleteButton);

        //DO WE NEED??
        newCommentsRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        newCommentsRecyclerView.setLayoutManager(layoutManager);

        manager = new PersistanceManager( this );

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Item.getLegalItemTypes());
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
            newShortDescription.setText(currentItem.getShortDescript());
            newLongDescription.setText(currentItem.getLongDescript());
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
                manager.editItem( currentItem, currentActivity);
                //Intent intent = new Intent(EditItemActivity.this, EditableItemListActivity.class);
                //intent.putExtra( "edited", true );
                //startActivity(intent);

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
                Toast.makeText( getApplicationContext(), "Item Deleted", Toast.LENGTH_SHORT ).show();
                manager.deleteItem(currentItem, currentActivity);
                //Intent intent = new Intent(EditItemActivity.this, EditableItemListActivity.class);
                //intent.putExtra( "remove", true );
                //startActivity(intent);
            }

         });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            newimage.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
        newimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture(view);
            }
        });

    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                newimage.setEnabled(true);
            }
        }
    }
        Uri file;
        public void takePicture(View view){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            file = Uri.fromFile(getOutputMediaFile());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

            startActivityForResult(intent, 100);
        }
        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            if (requestCode == 100) {
                if (resultCode == RESULT_OK) {
                    newimage.setImageURI(file);
                }
            }
        }
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
    public void setCommentsARray(ArrayList<String> array) {
        comments = array;
    }

    public void setUpRecyclerView(RecyclerView view) {

    }

    public void populateRecycleView(ArrayList<String> comm) {
        comments = comm;

        if (comments != null) {
            adapter = new EditItemActivity.CommentListAdapter(comments);
            newCommentsRecyclerView.setAdapter(adapter);
        } else {
            Log.d("Comments", "Comments are null");
        }
    }

    private static class CommentListAdapter extends RecyclerView.Adapter<EditItemActivity.CommentListAdapter.CommentViewHolder> {
        private ArrayList<String> theComments;
        private View view;

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

        public CommentListAdapter(ArrayList<String> array) {
            this.theComments = array;
        }

        @Override
        public EditItemActivity.CommentListAdapter.CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_comments_list, parent, false);
            view = v;
            EditItemActivity.CommentListAdapter.CommentViewHolder vh = new EditItemActivity.CommentListAdapter.CommentViewHolder(v);
            return vh;
        }

        //idk if this is right
        @Override
        public void onBindViewHolder(EditItemActivity.CommentListAdapter.CommentViewHolder holder, int position) {
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
