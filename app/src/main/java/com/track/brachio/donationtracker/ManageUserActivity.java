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
import com.track.brachio.donationtracker.model.singleton.CurrentItem;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);

        lastNameFirstName = findViewById(R.id.displayNameManageUserID);
        emailField = findViewById(R.id.displayEmailManageUserID);
        userTypeSpinner = findViewById(R.id.UserTypeSpinnerManageUserID);
        activitySpinner = findViewById(R.id.activitySpinnerID);

    }
}
