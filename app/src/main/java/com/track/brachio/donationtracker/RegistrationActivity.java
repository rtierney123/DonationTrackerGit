package com.track.brachio.donationtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.text.Editable;

import com.track.brachio.donationtracker.model.User;
import com.track.brachio.donationtracker.model.UserType;
import com.track.brachio.donationtracker.model.database.FirebaseUserHandler;

import java.util.Calendar;
import java.util.Date;

/**
 * Activity for registration
 */
public class RegistrationActivity extends AppCompatActivity {
    private EditText firstNameField;
    private EditText lastNameField;
    private EditText emailField;
    private EditText passwordField;
    private Spinner userTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_registration);

        Button registerButton = findViewById(R.id.registerButton);
        Button cancelButton = findViewById(R.id.cancelButtonR);
        firstNameField = findViewById(R.id.first_nameR);
        lastNameField = findViewById(R.id.last_nameR);
        emailField = findViewById(R.id.emailBoxR);
        passwordField = findViewById(R.id.passwordBoxR);
        userTypeSpinner = findViewById(R.id.userType);

        FirebaseUserHandler handler = new FirebaseUserHandler();

        ArrayAdapter adapter = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item, User.getLegalUserTypes());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(adapter);

        registerButton.setOnClickListener(view -> {
            String firstNameEntered;
            String lastNameEntered;
            String emailEntered;
            String passwordEntered;
            UserType userTypeSelected;
            Editable tempFirstName;

            tempFirstName = firstNameField.getText();
            firstNameEntered = tempFirstName.toString();

            Editable tempLastName = lastNameField.getText();
            lastNameEntered = tempLastName.toString();

            Editable emailTemp = emailField.getText();
            emailEntered = emailTemp.toString();

            Editable passTemp = passwordField.getText();
            passwordEntered = passTemp.toString();

            userTypeSelected = (UserType) userTypeSpinner.getSelectedItem();
            //add a default Spinner value
            if (!firstNameEntered.isEmpty() &&
                    !lastNameEntered.isEmpty() && !emailEntered.isEmpty()
                    && !passwordEntered.isEmpty()) {
                User newUser = new User(firstNameEntered, lastNameEntered,
                        emailEntered, userTypeSelected);
                Calendar cal = Calendar.getInstance();
                Date today = cal.getTime();
                newUser.setTimestamp( today );
                handler.createNewUser(newUser, passwordEntered,
                        RegistrationActivity.this, RegistrationActivity.this);
            } else {
                Toast.makeText( RegistrationActivity.this,
                        "Must fill in all info boxes.", Toast.LENGTH_SHORT ).show();
            }

        });

        cancelButton.setOnClickListener(view -> {
            Log.d("Registration", "Registration Canceled");
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}
