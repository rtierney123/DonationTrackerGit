//package com.track.brachio.donationtracker;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.track.brachio.donationtracker.model.Item;
//import com.track.brachio.donationtracker.model.database.FirebaseItemHandler;
//
//import java.util.ArrayList;
//
//public class ItemListActivity extends AppCompatActivity {
//    private ArrayList<Item> items;
//
//    private RecyclerView recyclerView;
//    private RecyclerView.Adapter adapter;
//    private RecyclerView.LayoutManager layoutManager;
//    private Button backButton;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate( savedInstanceState );
//        setContentView( R.layout.activity_item_list );
//
//        FirebaseItemHandler handler = new FirebaseItemHandler();
//        handler.getItems( this );
//
//        recyclerView = findViewById(R.id.itemList);
//        backButton = findViewById(R.id.itemListBackButton);
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        recyclerView.setHasFixedSize(true);
//
//        // use a linear layout manager
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("ItemList", "Back");
//                //putting it to donator main activity for now
//                Intent intent = new Intent(ItemListActivity.this, DonatorMainActivity.class);
//                startActivity(intent);
//            }
//        });
//
//    }
//
//    private void setupRecyclerView(RecyclerView view) {
//
//    }
//
//    public void populateRecycleView(ArrayList<Item> its) {
//        items = its;
//
//        // populate view based on items and adapter specifications
//        adapter = new ItemListActivity.ItemListAdapter(items);
//        recyclerView.setAdapter(adapter);
//
//        //TODO add junk here to take the item array an turn it into displayed list (probably with Recycle view and an adapter
//    }
//
//
//    private static class ItemListAdapter extends RecyclerView.Adapter<ItemListActivity.ItemListAdapter.ItemViewHolder>{
//        private ArrayList<Item> items;
//        private View view;
//        // Provide a reference to the views for each data item
//        // Complex data items may need more than one view per item, and
//        // you provide access to all the views for a data item in a view holder
//        public static class ItemViewHolder extends RecyclerView.ViewHolder {
//            // each data item is just a string in this case
//            private TextView nameText;
//            public ItemViewHolder(View v) {
//                super(v);
//                nameText = (TextView) v.findViewById(R.id.item_name_adapter);
//            }
//        }
//
//        // Provide a suitable constructor (depends on the kind of dataset)
//        public ItemListAdapter(ArrayList<Item> array) {
//            items = array;
//        }
//
//        // Create new views (invoked by the layout manager)
//        // Create new views (invoked by the layout manager)
//        @Override
//        public ItemListActivity.ItemListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent,
//                                                                                              int viewType) {
//            // create a new view
//            View v = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.adapter_item_list, parent, false);
//
//            view = v;
//            ItemListActivity.ItemListAdapter.ItemViewHolder vh = new ItemListActivity.ItemListAdapter.ItemViewHolder(v);
//            return vh;
//        }
//
//        // Replace the contents of a view (invoked by the layout manager)
//        @Override
//        public void onBindViewHolder(ItemListActivity.ItemListAdapter.ItemViewHolder holder, int position) {
//            // - get element from your dataset at this position
//            // - replace the contents of the view with that element
//            holder.nameText.setText(items.get(position).getName());
//
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //TODO add intent to next page
//                }
//            });
//
//        }
//
//        // Return the size of your dataset (invoked by the layout manager)
//        @Override
//        public int getItemCount() {
//            return items.size();
//        }
//
//    }
//}
