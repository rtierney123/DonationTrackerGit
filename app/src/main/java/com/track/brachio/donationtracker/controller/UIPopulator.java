package com.track.brachio.donationtracker.controller;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.track.brachio.donationtracker.R;

import java.util.ArrayList;
import java.util.List;

public class UIPopulator {
    public int selected;
    public void populateSpinner(Spinner spinner, List<String> array, Activity activity) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                activity, android.R.layout.simple_spinner_item, array);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


}
