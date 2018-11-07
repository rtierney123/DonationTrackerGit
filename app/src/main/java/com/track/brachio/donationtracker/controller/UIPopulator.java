package com.track.brachio.donationtracker.controller;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

/**
 * Populates the Spinner
 */
public class UIPopulator {
    /**
     * populates the spinner
     * @param spinner spinner being populated
     * @param array array populating the spinner
     * @param activity current activity
     */
    public void populateSpinner(Spinner spinner, List<String> array, Activity activity) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                activity, android.R.layout.simple_spinner_item, array);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


}
