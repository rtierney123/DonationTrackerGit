package com.track.brachio.donationtracker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.Manifest;
import java.io.File;
import android.text.Editable;
import java.util.Date;

import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.track.brachio.donationtracker.controller.PersistanceManager;
import com.track.brachio.donationtracker.model.singleton.CurrentUser;
import com.track.brachio.donationtracker.model.User;
import com.track.brachio.donationtracker.model.UserType;
import com.track.brachio.donationtracker.model.database.FirebaseUserHandler;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import com.track.brachio.donationtracker.model.Item;
import com.track.brachio.donationtracker.model.ItemType;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManageUserActivity extends AppCompatActivity {
    private TextView lastNameFirstName;
    private TextView emailField;
    private Spinner userTypeSpinner;
    private Spinner activitySpinner;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);

        lastNameFirstName = findViewById(R.id.displayNameManageUserID);
        emailField = findViewById(R.id.displayEmailManageUserID);
        userTypeSpinner = findViewById(R.id.UserTypeSpinnerManageUserID);
        activitySpinner = findViewById(R.id.activitySpinnerID);
        saveButton = findViewById(R.id.saveUserChangesID);

        FirebaseUserHandler handler = new FirebaseUserHandler();

        CurrentUser currentUser = CurrentUser.getInstance();

        User user = currentUser.getUser();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        UserType userUserType = user.getUserType();

        lastNameFirstName.setText(lastName + ", " + firstName);
        emailField.setText(email);

        ArrayAdapter adapterUserType = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                User.getLegalUserTypes());
        adapterUserType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(adapterUserType);

        List<String> legalActivityTypes = new ArrayList<>();
        legalActivityTypes.add("Active");
        legalActivityTypes.add("Inactive");
        ArrayAdapter adapterActivity = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                legalActivityTypes);
        adapterActivity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activitySpinner.setAdapter(adapterActivity);

        saveButton.setOnClickListener(view -> {
            UserType userTypeSelected;
            String activityStatusSelected;

            userTypeSelected = (UserType) userTypeSpinner.getSelectedItem();

            activityStatusSelected = activitySpinner.getSelectedItem().toString();

            /*
            * Change the User when firebase items are created
             */

        });


    }
}
