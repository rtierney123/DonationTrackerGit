package com.track.brachio.donationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.track.brachio.donationtracker.controller.UIPopulator;
import com.track.brachio.donationtracker.model.Item;
import com.track.brachio.donationtracker.model.ItemType;
import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.singleton.CurrentItem;
import com.track.brachio.donationtracker.model.singleton.SearchedItems;
import com.track.brachio.donationtracker.model.singleton.UserLocations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Stream;

public class ItemListActivity extends AppCompatActivity{
    private ArrayList<Item> items = new ArrayList<>();
    private static HashMap<String, Item> itemMap = new HashMap<String, Item>();
    private static HashMap<String, HashMap<String, Item>> storeItems;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button backButton;
    private FloatingActionButton editButton;
    private Spinner locSpinner;
    private Spinner categorySpinner;
    private static int locIndex;
    private static int catIndex;
    private UIPopulator ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_item_list );

        recyclerView = findViewById(R.id.itemList);;
        editButton= findViewById(R.id.editbutton);
        locSpinner= findViewById(R.id.locSpinner);
        categorySpinner= findViewById( R.id.categorySpinner );
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        editButton.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ItemList", "Edit");
                //putting it to donator main activity for now
                Intent intent = new Intent(ItemListActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });

        ui = new UIPopulator();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Location> array = UserLocations.getInstance().getLocations();
        for(Location loc : array){
            names.add(loc.getName());
        }

        ui.populateSpinner( locSpinner, names, this );
        locSpinner.setSelection(locIndex);
        locSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                locIndex = position;
                populateRecycleView();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayList<String> categories = ItemType.getArrayList();
        categories.add(0,"All");
        ui.populateSpinner( categorySpinner, categories, this );
        categorySpinner.setSelection(catIndex);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                catIndex = position;
                populateRecycleView();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        /*
        Bundle extra = getIntent().getExtras();
        if (extra != null){
            if (extra.getBoolean( "edited" ) == true) {
                Log.d("Edit Item", "Item edited");
                Item editedItem = CurrentItem.getInstance().getItem();
                if (itemMap == null){
                    HashMap<String, Item> newMap = new LinkedHashMap<>(  );
                    newMap.put(editedItem.getKey(), editedItem);
                    storeItems.put(editedItem.getLocation(), newMap);
                }
                itemMap.put(editedItem.getKey(), editedItem);
                populateRecycleView();
            } else if(extra.getBoolean( "remove" ) == true){
                Log.d("Delete Item", "Item deleted");
                Item current = CurrentItem.getInstance().getItem();
                String key = current.getKey();
                itemMap.remove(key);
                populateRecycleView();
            } else if(extra.getBoolean( "add" ) == true){
                //Log.d("Add Item", "Item added");
                //Item addedItem = CurrentItem.getInstance().getItem();
                //itemMap.put(addedItem.getKey(), addedItem);
                //populateRecycleView();
            }
        }
        */
    }

    
    public void setItemArray(ArrayList<Item> array) {
        items = array;
    }

    private void setupRecyclerView(RecyclerView view) {

    }

    public String getCurrentLocationID(){
        UserLocations locs = UserLocations.getInstance();
        ArrayList<Location> array = locs.getLocations();
        Location loc = array.get(locIndex);
        return loc.getId();
    }


    public void populateRecycleView() {
        String locID = getCurrentLocationID();

        SearchedItems searched = SearchedItems.getInstance();
        HashMap<String, HashMap<String, Item>> storeItems = searched.getSearchedMap();

        if (!(storeItems==null)) {
            itemMap = storeItems.get(locID);
        }

        if(!(itemMap==null)) {
            items = new ArrayList<>(itemMap.values());
        } else {
            items.clear();
        }

        ArrayList<Item> displayItems = new ArrayList();
        for(Item item : items){
            int itemCat = item.getCategory().ordinal();
            if(catIndex == 0 || itemCat == catIndex-1){
                displayItems.add(item);
            }
        }


        if (displayItems != null) {
            // populate view based on items and adapter specifications
            //TODO add on click to ItemEditActivity
            adapter = new ItemListActivity.ItemListAdapter(displayItems, new ItemListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Item item) {
                    CurrentItem.getInstance().setItem(item);
                    Intent intent = new Intent(ItemListActivity.this, EditItemActivity.class);
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter( adapter );
        } else {
            Log.d("ItemEdit", "item is null");
        }


        //TODO add junk here to take the item array an turn it into displayed list (probably with Recycle view and an adapter
    }

    /*
    private ArrayList<String> getArrayfromEnum(Class<? extends Enum<?>> e){
        Stream s = Arrays.stream(e.getEnumConstants()).map(Enum::name);
        String[] array = (String[])s.toArray(String[]::new);
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(array));
        return arrayList;
    }
    */

    private static class ItemListAdapter extends RecyclerView.Adapter<ItemListActivity.ItemListAdapter.ItemViewHolder>{
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
        public ItemListActivity.ItemListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                  int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_item_list, parent, false);

            view = v;
            ItemListActivity.ItemListAdapter.ItemViewHolder vh = new ItemListActivity.ItemListAdapter.ItemViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ItemListActivity.ItemListAdapter.ItemViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.nameText.setText(items.get(position).getName());
            if (items.get(position).getDateCreated() != null){
                holder.dateText.setText(items.get(position).getDateCreated().toString());
            }
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
