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

import com.track.brachio.donationtracker.model.User;
import com.track.brachio.donationtracker.model.UserType;
import com.track.brachio.donationtracker.model.database.FirebaseLocationHandler;
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
    private ImageView newimage;
    private Button cancelButton;
    private Button addButton;
    private Button deleteButton;
    private ArrayList<String> comments;

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
        newimage = (ImageView) findViewById(R.id.editItemImageID);
        cancelButton = (Button) findViewById(R.id.editItemCancelButton);
        addButton = (Button) findViewById(R.id.editItemMakeChangesID);
        deleteButton = (Button) findViewById(R.id.editItemDeleteButton);

        //DO WE NEED??
        newCommentsRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        newCommentsRecyclerView.setLayoutManager(layoutManager);


        FirebaseLocationHandler handler = new FirebaseLocationHandler();

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
