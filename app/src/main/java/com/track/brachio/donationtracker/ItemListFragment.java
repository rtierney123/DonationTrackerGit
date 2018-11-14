package com.track.brachio.donationtracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.track.brachio.donationtracker.adapters.ItemListAdapter;
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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ItemListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ItemListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressWarnings("ClassWithTooManyDependencies")
public class ItemListFragment extends Fragment {

    private Collection<Item> items = new ArrayList<>();
    //private static HashMap<String, HashMap<String, Item>> storeItems;
    private RecyclerView recyclerView;
    private static int locIndex;
    private static int catIndex;
    private Activity containerActivity;

    private String searchString = "";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    @Nullable
    private OnFragmentInteractionListener mListener;


    /**
     * This is a required and empty public constructor
     */
    public ItemListFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItemListFragment.
     */
//    public static ItemListFragment newInstance(String param1, String param2) {
//        ItemListFragment fragment = new ItemListFragment();
//        Bundle args = new Bundle();
//        args.putString( ARG_PARAM1, param1 );
//        args.putString( ARG_PARAM2, param2 );
//        fragment.setArguments( args );
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        containerActivity = this.getActivity();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_item_list, container, false );
        recyclerView = view.findViewById(R.id.itemList);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        FloatingActionButton editButton= view.findViewById(R.id.editButton);
        Spinner locSpinner= view.findViewById(R.id.locSpinner);
        Spinner categorySpinner= view.findViewById( R.id.categorySpinner );
        SearchView searchView = view.findViewById(R.id.nameSearchView);
        editButton.setOnClickListener (view1 -> {
            Log.d("ItemList", "Edit");
            //putting it to donator main activity for now
            Intent intent = new Intent(containerActivity, ItemAddActivity.class);
            startActivity(intent);
        });

        UIPopulator ui = new UIPopulator();
        List<String> names = new ArrayList<>();
        names.add("All");
        UserLocations currentUserLocations = UserLocations.getInstance();
        List<Location> array = currentUserLocations.getLocations();
        for(Location loc : array){
            names.add(loc.getName());
        }

        ui.populateSpinner( locSpinner, names, this.getActivity() );
        locSpinner.setSelection(locIndex);
        locSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
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
        ui.populateSpinner( categorySpinner, categories, containerActivity);
        categorySpinner.setSelection(catIndex);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                catIndex = position;
                populateRecycleView();
            }
            @Override
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
        return view;
    }


    /**
     * handler for button press
     * @param uri parameter
     */
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction( uri );
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach( context );
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException( context.toString()
                    + " must implement OnFragmentInteractionListener" );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        /**
         * abstract handler for fragment interaction
         * @param uri parameter
         */
        @SuppressWarnings("EmptyMethod")
        void onFragmentInteraction(Uri uri);
    }

    /**
     * returns current location Id
     * @return id
     */
    private String getCurrentLocationID(){
        UserLocations locations = UserLocations.getInstance();
        List<Location> array = locations.getLocations();
        if (locIndex != 0) {
            Location loc = array.get(locIndex-1);
            return loc.getId();
        } else {
            return null;
        }

    }

    /**
     * returns all of the locations id
     * @return list of ids
     */
    private List<String> getAllLocationIds(){
        UserLocations locations = UserLocations.getInstance();
        List<Location> array = locations.getLocations();
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
    private void populateRecycleView() {
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
            } else {
                //clear items for location without items
                items.clear();

            }
        }


        ArrayList<Item> displayItems = new ArrayList<>();
        for(Item item : items){
            ItemType theItemType = item.getCategory();
            int itemCat = theItemType.ordinal();
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

        //show that no items were found
        if (displayItems.isEmpty()){
            Toast.makeText(this.getActivity(), "No items found for this criteria.",
                    Toast.LENGTH_LONG).show();
        }


        // populate view based on items and adapter specifications
        RecyclerView.Adapter adapter =  new ItemListAdapter( displayItems,
                item -> {
                    CurrentItem currentItemInstance = CurrentItem.getInstance();
                    currentItemInstance.setItem(item);
                    Intent intent = new Intent(containerActivity, ItemDetailActivity.class);
                    startActivity(intent);
                });
        recyclerView.setAdapter( adapter );


    }

}
