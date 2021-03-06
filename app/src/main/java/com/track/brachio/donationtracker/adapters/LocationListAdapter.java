package com.track.brachio.donationtracker.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.track.brachio.donationtracker.R;
import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.Address;

import java.util.List;

/**
 * Adapter for list of locations
 */
public class LocationListAdapter extends
        RecyclerView.Adapter<LocationListAdapter.LocationViewHolder>{
    private final List<Location> locations;

    private final OnItemClickListener locationListener;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class LocationViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private final TextView nameText;
        private final TextView cityText;
        private final View v;

        /**
         * constructor for LocationViewHolder
         * @param v current view
         */
        LocationViewHolder(View v) {
            super(v);
            this.v = v;
            nameText = v.findViewById(R.id.location_name_adapter);
            cityText = v.findViewById(R.id.location_city_adapter);
        }

        /**
         * binds
         * @param item current item being bound
         * @param listener current listener
         */
        private void bind(final Location item, final OnItemClickListener listener) {
            v.setOnClickListener(v -> {
                Log.d("LocationListActivity", "To Location");
                listener.onItemClick(item);
            });
        }
    }

    /**
     * listener for clicking of list item
     */
    public interface OnItemClickListener {
        /**
         * abstract listener for onClick
         * @param item item being clicked
         */
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
    public LocationListAdapter.LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_location_list, parent, false);

        return new LocationViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(
            @NonNull LocationListAdapter.LocationViewHolder holder, int position) {
        // - get element from your data set at this position
        // - replace the contents of the view with that element
        Location location = locations.get(position);
        Address add = location.getAddress();
        holder.nameText.setText(location.getName());
        holder.cityText.setText(add.getCity());
        holder.bind(location, locationListener);


    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return locations.size();
    }



}