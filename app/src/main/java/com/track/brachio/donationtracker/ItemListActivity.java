package com.track.brachio.donationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SearchView;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Activity for Item List
 */
public class ItemListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Collection<Item> items = new ArrayList<>();
    private static HashMap<String, HashMap<String, Item>> storeItems;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button backButton;
    private FloatingActionButton editButton;
    private Spinner locSpinner;
    private Spinner categorySpinner;
    private SearchView searchView;
    private static int locIndex;
    private static int catIndex;
    private UIPopulator ui;
    private String searchString = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_item_list );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );

        recyclerView = findViewById(R.id.itemList);
        editButton= findViewById(R.id.editbutton);
        locSpinner= findViewById(R.id.locSpinner);
        categorySpinner= findViewById( R.id.categorySpinner );
        searchView = findViewById(R.id.nameSearchView);
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
                Intent intent = new Intent(ItemListActivity.this, ItemAddActivity.class);
                startActivity(intent);
            }
        });

        ui = new UIPopulator();
        List<String> names = new ArrayList<>();
        names.add("All");
        List<Location> array = UserLocations.getInstance().getLocations();
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
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        List<String> categories = ItemType.getArrayList();
        categories.add(0,"All");
        ui.populateSpinner( categorySpinner, categories, this );
        categorySpinner.setSelection(catIndex);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                catIndex = position;
                populateRecycleView();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // Do something when user his enter on keyboard
                searchString = query;
                populateRecycleView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Do something while user is entering text
                searchString = newText;
                populateRecycleView();
                return false;
            }
        });
    }

    /**
     * returns current location Id
     * @return id
     */
    private String getCurrentLocationID(){
        UserLocations locs = UserLocations.getInstance();
        List<Location> array = locs.getLocations();
        if (locIndex != 0) {
            Location loc = array.get(locIndex-1);
            return loc.getId();
        } else {
            return null;
        }

    }

    /**
     * returns all of the locaitons id
     * @return list of ids
     */
    public List<String> getAllLocationIds(){
        UserLocations locs = UserLocations.getInstance();
        List<Location> array = locs.getLocations();
        List<String> ids = new ArrayList<>();
        for(Location loc : array){
            String id = loc.getId();
            ids.add(id);
        }
        return ids;
    }

    /**
     * populates the recycler view
     */
    public void populateRecycleView() {
        String locID = getCurrentLocationID();

        SearchedItems searched = SearchedItems.getInstance();
        Map<String, Map<String, Item>> storeItems = searched.getSearchedMap();

        Map<String, Item> itemMap;
        if(locIndex == 0){
            items.clear();
            List<String> locIds = getAllLocationIds();
            for(String location : locIds){
                itemMap = storeItems.get(location);
                if (itemMap != null){
                    items.addAll(new ArrayList<>(itemMap.values()));
                }
            }

        } else {
            itemMap = storeItems.get(locID);

            if(!(itemMap ==null)) {
                items = new ArrayList<>(itemMap.values());
            }
        }


        ArrayList<Item> displayItems = new ArrayList();
        for(Item item : items){
            int itemCat = item.getCategory().ordinal();
            String itemName = item.getName();
            if((catIndex == 0) || (itemCat == (catIndex - 1))){
                if (searchString.isEmpty()){
                    displayItems.add(item);
                } else {
                    Pattern p = Pattern.compile(searchString);
                    Matcher m = p.matcher(itemName);
                    if (m.find()){
                        displayItems.add(item);
                    }
                }
            }
        }


        // populate view based on items and adapter specifications
        adapter = new ItemListActivityTemp.ItemListAdapter(displayItems,
                new ItemListActivityTemp.ItemListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Item item) {
                        CurrentItem.getInstance().setItem(item);
                        Intent intent = new Intent(ItemListActivity.this, ItemDetailActivity.class);
                        startActivity(intent);
                    }
                });
        recyclerView.setAdapter( adapter );


    }


    /**
     * Adapter for ItemList
     */
    @SuppressWarnings("FeatureEnvy")
    private static class ItemListAdapter extends
            RecyclerView.Adapter<ItemListActivityTemp.ItemListAdapter.ItemViewHolder>{
        private List<Item> items;
        private View view;
        private final ItemListActivityTemp.ItemListAdapter.OnItemClickListener theItemListener;
        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder

        /**
         * Holder for Item View
         */
        public static class ItemViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public final TextView nameText;
            public TextView dateText;
            public TextView valueText;
            public TextView categoryText;
            public View v;

            /**
             * Constructor for ItemViewHolder
             * @param v view being passed in
             */
            public ItemViewHolder(View v) {
                super(v);
                this.v = v;
                nameText = v.findViewById(R.id.item_name_adapter);
                dateText = v.findViewById(R.id.item_date_adapter);
                valueText = (TextView) v.findViewById(R.id.item_cost_adapter);
                categoryText = (TextView) v.findViewById(R.id.item_category_adapter);
            }

            /**
             * binds for the itme view holder
             * @param theItem item being binded
             * @param listener listeneer on the list
             */
            public void bind(final Item theItem, final ItemListActivityTemp.ItemListAdapter.OnItemClickListener listener) {
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("EditItemListActivity", "To Item");
                        listener.onItemClick(theItem);
                    }
                });
            }
        }

        /**
         * Listener for the recycler view
         */
        public interface OnItemClickListener {
            void onItemClick(Item item);
        }

        /**
         * constructor for ItemListAdapter
         * @param array array being assigned
         * @param listener current listener
         */
        // Provide a suitable constructor (depends on the kind of dataset)
        public ItemListAdapter(List<Item> array, ItemListActivityTemp.ItemListAdapter.OnItemClickListener listener) {
            this.items = array;
            this.theItemListener = listener;
        }

        // Create new views (invoked by the layout manager)
        // Create new views (invoked by the layout manager)
        @NonNull
        @Override
        public ItemListActivityTemp.ItemListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                      int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_item_list, parent, false);

            view = v;
            return new ItemListActivityTemp.ItemListAdapter.ItemViewHolder(v);

        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(
                @NonNull ItemListActivityTemp.ItemListAdapter.ItemViewHolder holder, int position) {
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

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.item_list_activity2, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }
}
