package com.track.brachio.donationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.track.brachio.donationtracker.controller.UIPopulator;
import com.track.brachio.donationtracker.model.Item;
import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.database.FirebaseItemHandler;
import com.track.brachio.donationtracker.model.singleton.CurrentItem;
import com.track.brachio.donationtracker.model.singleton.UserLocations;

import java.util.ArrayList;
import java.util.HashMap;

public class EditableItemListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ArrayList<Item> items = new ArrayList<>();
    private static HashMap<String, Item> itemMap;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button backButton;
    private FloatingActionButton editButton;
    private Spinner locSpinner;
    private int locIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_item_list );

        recyclerView = findViewById(R.id.itemList);;
        editButton= findViewById(R.id.editbutton);
        locSpinner= findViewById(R.id.locSpinner);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (items.size() == 0 || items == null) {
            Bundle extra = getIntent().getExtras();
            if (extra == null) {
                FirebaseItemHandler handler = new FirebaseItemHandler();
                handler.getAllItems( this );

            }
        }

        editButton.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ItemList", "Edit");
                //putting it to donator main activity for now
                Intent intent = new Intent(EditableItemListActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });

        UIPopulator ui = new UIPopulator();
        ArrayList<String> names = new ArrayList<>();
        for(Location loc :  UserLocations.getInstance().getLocations()){
            names.add(loc.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, names);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        locSpinner.setOnItemSelectedListener(this);
        locSpinner.setAdapter(adapter);
        //ui.populateSpinner(locSpinner, names, this);

    }

    @Override
    protected void onResume(){
        super.onResume();
        Bundle extra = getIntent().getExtras();
        if (extra != null){
            if (extra.getBoolean( "edited" ) == true) {
                Log.d("Edit Item", "Item edited");
                Item editedItem = CurrentItem.getInstance().getItem();
                itemMap.put(editedItem.getKey(), editedItem);
                populateRecycleView(itemMap, false);
            } else if(extra.getBoolean( "remove" ) == true){
                Log.d("Delete Item", "Item deleted");
                Item current = CurrentItem.getInstance().getItem();
                String key = current.getKey();
                itemMap.remove(key);
                populateRecycleView( itemMap, false);
            }
        }
    }

    
    public void setItemArray(ArrayList<Item> array) {
        items = array;
    }

    private void setupRecyclerView(RecyclerView view) {

    }

    public void populateRecycleView(HashMap<String, Item> its, boolean firstLoad) {
        if(firstLoad && itemMap == null){
            itemMap = its;
        }

        if (its !=null){
            ArrayList<Item> temp = new ArrayList(its.values());
            items.clear();
            ArrayList<Location> userLoc = UserLocations.getInstance().getLocations();
            for (Item item : temp){
                String currentLoc = userLoc.get(locIndex).getId();
                String locID = item.getLocation();
                if(locID.equals( currentLoc)){
                    items.add(item);
                }
            }
        }

        if (items != null) {
            // populate view based on items and adapter specifications
            //TODO add on click to ItemEditActivity
            adapter = new EditableItemListActivity.ItemListAdapter(items, new ItemListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Item item) {
                    CurrentItem.getInstance().setItem(item);
                    Intent intent = new Intent(EditableItemListActivity.this, EditItemActivity.class);
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter( adapter );
        } else {
            Log.d("ItemEdit", "item is null");
        }


        //TODO add junk here to take the item array an turn it into displayed list (probably with Recycle view and an adapter
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        locIndex = position;

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
        populateRecycleView( itemMap , false);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private static class ItemListAdapter extends RecyclerView.Adapter<EditableItemListActivity.ItemListAdapter.ItemViewHolder>{
        private ArrayList<Item> items;
        private View view;
        private final OnItemClickListener theItemListener;
        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class ItemViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            private TextView nameText;
            private TextView dateText;
            private TextView valueText;
            private TextView categoryText;
            private View v;
            public ItemViewHolder(View v) {
                super(v);
                this.v = v;
                nameText = (TextView) v.findViewById(R.id.item_name_adapter);
                dateText = (TextView) v.findViewById(R.id.item_date_adapter);
                valueText = (TextView) v.findViewById(R.id.item_cost_adapter);
                categoryText = (TextView) v.findViewById(R.id.item_category_adapter);
            }

            public void bind(final Item theItem, final OnItemClickListener listener) {
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("EditItemListActivity", "To Item");
                        listener.onItemClick(theItem);
                    }
                });
            }
        }

        public interface OnItemClickListener {
            void onItemClick(Item item);
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public ItemListAdapter(ArrayList<Item> array, OnItemClickListener listener) {
            this.items = array;
            this.theItemListener = listener;
        }

        // Create new views (invoked by the layout manager)
        // Create new views (invoked by the layout manager)
        @Override
        public EditableItemListActivity.ItemListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                          int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_item_list, parent, false);

            view = v;
            EditableItemListActivity.ItemListAdapter.ItemViewHolder vh = new EditableItemListActivity.ItemListAdapter.ItemViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(EditableItemListActivity.ItemListAdapter.ItemViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.nameText.setText(items.get(position).getName());
            holder.dateText.setText(items.get(position).getDateCreated().toString());
            holder.valueText.setText(items.get(position).getDollarValue()+"");
            if (items.get(position).getCategory() != null) {
                holder.categoryText.setText(items.get(position).getCategory().toString());
            }
            holder.bind(items.get(position), theItemListener);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //TODO add intent to next page
//                }
//            });

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

    }
}
