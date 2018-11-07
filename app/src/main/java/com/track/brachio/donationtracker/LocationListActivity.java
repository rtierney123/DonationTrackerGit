
package com.track.brachio.donationtracker;

import android.support.annotation.NonNull;
import android.util.Log;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.singleton.AllLocations;
import com.track.brachio.donationtracker.model.singleton.CurrentLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for listing locations
 */
public class LocationListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button mapButton;
    private int thePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_location_list );

        recyclerView = findViewById(R.id.listOfLocations);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mapButton = findViewById( R.id.mapButtonID );
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocationListActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });


        populateRecycleView();



    }

    /**
     * sets up recycler view
     * @param view activity view
     */
    private void setupRecyclerView(RecyclerView view) {

    }

    /**
     * populates recycler view
     */
    public void populateRecycleView() {
        ArrayList<Location> locations = AllLocations.getInstance().getLocationArray();

        // populate view based on locations and adapter specifications
        adapter = new LocationListAdapter(locations, new LocationListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Location item) {
                CurrentLocation.getInstance().setLocation(item);
                Intent intent = new Intent(LocationListActivity.this, LocationActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

    }


    /**
     * Adapter for list of locations
     */
    public static class LocationListAdapter extends
            RecyclerView.Adapter<LocationListAdapter.LocationViewHolder>{
        private List<Location> locations;
        private View view;
        private final OnItemClickListener locationListener;
        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class LocationViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            private final TextView nameText;
            private TextView cityText;
            private View v;

            /**
             * constructor for LocationViewHolder
             * @param v current view
             */
            LocationViewHolder(View v) {
                super(v);
                this.v = v;
                nameText = v.findViewById(R.id.location_name_adapter);
                cityText = (TextView) v.findViewById(R.id.location_city_adapter);
            }

            /**
             * binds
             * @param item current item being binded
             * @param listener current listener
             */
            public void bind(final Location item, final OnItemClickListener listener) {
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("LocationListActivity", "To Location");
                        listener.onItemClick(item);
                    }
                });
            }
        }

        /**
         * listener for clicking of list item
         */
        public interface OnItemClickListener {
            void onItemClick(Location item);
        }


        /**
         * location list adapter
         * @param array array being displayed
         * @param listener listener for recycler view
         */
        public LocationListAdapter(List<Location> array, OnItemClickListener listener) {
            locations = array;
            locationListener = listener;
        }
        // Provide a suitable constructor (depends on the kind of data set)
//        public LocationListAdapter(ArrayList<Location> array) {
//            locations = array;
//        }

        // Create new views (invoked by the layout manager)
        // Create new views (invoked by the layout manager)
        @NonNull
        @Override
        public LocationListAdapter.LocationViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_location_list, parent, false);

            view = v;
            return new LocationViewHolder(v);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(
                @NonNull LocationListAdapter.LocationViewHolder holder, int position) {
            // - get element from your data set at this position
            // - replace the contents of the view with that element
            holder.nameText.setText(locations.get(position).getName());
            holder.cityText.setText(locations.get(position).getAddress().getCity());
            holder.bind(locations.get(position), locationListener);


        }

        // Return the size of your data set (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return locations.size();
        }



    }
}

