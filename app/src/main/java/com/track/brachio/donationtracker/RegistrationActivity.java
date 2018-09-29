package com.track.brachio.donationtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import com.track.brachio.donationtracker.model.User;
import com.track.brachio.donationtracker.model.UserType;
import com.track.brachio.donationtracker.model.database.FirebaseUserHandler;
import com.track.brachio.donationtracker.model.singleton.CurrentUser;

public class RegistrationActivity extends AppCompatActivity {
    private Button loginButton;
    private Button cancelButton;
    private EditText firstNameField;
    private EditText lastNameField;
    private EditText emailField;
    private EditText passwordField;
    private Spinner userTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_registration );

        loginButton = (Button) findViewById(R.id.loginButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        firstNameField = (EditText) findViewById(R.id.first_name);
        lastNameField = (EditText) findViewById(R.id.last_name);
        emailField = (EditText) findViewById(R.id.emailBox);
        passwordField = (EditText) findViewById(R.id.passwordBox);
        userTypeSpinner = (Spinner) findViewById(R.id.userType);

        FirebaseUserHandler handler = new FirebaseUserHandler();

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, User.getLegalUserTypes());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(adapter);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("login", "clicked");
                String firstNameEntered;
                String lastNameEntered;
                String emailEntered;
                String passwordEntered;
                UserType userTypeSelected;

                firstNameEntered = firstNameField.getText().toString();
                lastNameEntered = lastNameField.getText().toString();
                emailEntered = emailField.getText().toString();
                passwordEntered = passwordField.getText().toString();
                userTypeSelected = (UserType) userTypeSpinner.getSelectedItem();
            }
        });
    }
}
