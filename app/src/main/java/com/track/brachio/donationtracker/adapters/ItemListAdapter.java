package com.track.brachio.donationtracker.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.track.brachio.donationtracker.R;
import com.track.brachio.donationtracker.model.Item;

import java.util.List;

/**
 * Adapter for ItemList
 */
@SuppressWarnings("FeatureEnvy")
public class ItemListAdapter extends
        RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>{
    private final List<Item> items;
    private View view;
    private final OnItemClickListener theItemListener;
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
        private final View v;

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
            categoryText = v.findViewById(R.id.item_category_adapter);
        }

        /**
         * binds for the itme view holder
         * @param theItem item being binded
         * @param listener listeneer on the list
         */
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
    // Provide a suitable constructor (depends on the kind of dataset)
    public ItemListAdapter(List<Item> array, OnItemClickListener listener) {
        this.items = array;
        this.theItemListener = listener;
    }

    // Create new views (invoked by the layout manager)
    // Create new views (invoked by the layout manager)
    @Override
    public ItemListAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                                  int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_item_list, parent, false);

        view = v;
        return new ItemViewHolder(v);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(
            @NonNull ItemListAdapter.ItemViewHolder holder, int position) {
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