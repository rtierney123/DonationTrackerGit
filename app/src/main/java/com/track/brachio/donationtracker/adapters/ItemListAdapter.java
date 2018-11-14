package com.track.brachio.donationtracker.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Date;
import android.widget.TextView;

import com.track.brachio.donationtracker.R;
import com.track.brachio.donationtracker.model.Item;
import com.track.brachio.donationtracker.model.ItemType;

import java.util.List;

/**
 * Adapter for ItemList
 */
public class ItemListAdapter extends
        RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>{
    private final List<Item> items;
    private final OnItemClickListener theItemListener;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    /**
     * Holder for Item View
     */
    public static final class ItemViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private final TextView nameText;
        private final TextView dateText;
        private final TextView valueText;
        private final TextView categoryText;
        private final View v;


        /**
         * Constructor for ItemViewHolder
         * @param v view being passed in
         */
        private ItemViewHolder(View v) {
            super(v);
            this.v = v;
            nameText = v.findViewById(R.id.item_name_adapter);
            dateText = v.findViewById(R.id.item_date_adapter);
            valueText = v.findViewById(R.id.item_cost_adapter);
            categoryText = v.findViewById(R.id.item_category_adapter);
        }

        /**
         * binds for the time view holder
         * @param theItem item being bound
         * @param listener listener on the list
         */
        private void bind(final Item theItem, final OnItemClickListener listener) {
            v.setOnClickListener(v -> {
                Log.d("EditItemListActivity", "To Item");
                listener.onItemClick(theItem);
            });
        }
    }

    /**
     * Listener for the recycler view
     */
    public interface OnItemClickListener {
        /**
         * handles what happens on click
         * @param item item being clicked
         */
        void onItemClick(Item item);
    }

    /**
     * constructor for ItemListAdapter
     * @param array array being assigned
     * @param listener current listener
     */
    // Provide a suitable constructor (depends on the kind of data set)
    public ItemListAdapter(List<Item> array, OnItemClickListener listener) {
        this.items = array;
        this.theItemListener = listener;
    }

    // Create new views (invoked by the layout manager)
    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ItemListAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                                  int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_item_list, parent, false);
        //Context context = v.getContext();

        return new ItemViewHolder(v);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(
            @NonNull ItemListAdapter.ItemViewHolder holder, int position) {
        // - get element from your data set at this position
        // - replace the contents of the view with that element
        Item theItem = items.get(position);
        String itemName = theItem.getName();

        holder.nameText.setText(itemName);

        Date theDate = theItem.getDateCreated();
        if (theDate != null){
            Date dateCreated = theItem.getDateCreated();
            String dateCreatedString = dateCreated.toString();
            holder.dateText.setText(dateCreatedString);
        }
        double dollarValue = theItem.getDollarValue();
        String dollarValueString = Double.toString(dollarValue);
        holder.valueText.setText(dollarValueString);
        ItemType itemsType = theItem.getCategory();
        if (itemsType != null) {
            ItemType theItemType = theItem.getCategory();
            String theItemTypeString = theItemType.toString();
            holder.categoryText.setText(theItemTypeString);
        }
        holder.bind(theItem, theItemListener);

    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return items.size();
    }



}