package com.track.brachio.donationtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.track.brachio.donationtracker.model.User;
import com.track.brachio.donationtracker.model.UserType;
import com.track.brachio.donationtracker.model.database.FirebaseUserHandler;

@SuppressWarnings("SpellCheckingInspection")
public class RegistrationActivity extends AppCompatActivity {
    private Button registerButton;
    private Button cancelButton;
    private EditText firstNameField;
    private EditText lastNameField;
    private EditText emailField;
    private EditText passwordField;
    private Spinner userTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_registration);

        registerButton = (Button) findViewById(R.id.registerButton);
        cancelButton = (Button) findViewById(R.id.cancelButtonR);
        firstNameField = (EditText) findViewById(R.id.first_nameR);
        lastNameField = (EditText) findViewById(R.id.last_nameR);
        emailField = (EditText) findViewById(R.id.emailBoxR);
        passwordField = (EditText) findViewById(R.id.passwordBoxR);
        userTypeSpinner = (Spinner) findViewById(R.id.userType);

        FirebaseUserHandler handler = new FirebaseUserHandler();

        ArrayAdapter<String> adapter = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item, User.getLegalUserTypes());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(adapter);

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String firstNameEntered;
                String lastNameEntered;
                String emailEntered;
                String passwordEntered;
                UserType userTypeSelected;
                Boolean registrationSuccessful;

                firstNameEntered = firstNameField.getText().toString();
                lastNameEntered = lastNameField.getText().toString();
                emailEntered = emailField.getText().toString();
                passwordEntered = passwordField.getText().toString();
                userTypeSelected = (UserType) userTypeSpinner.getSelectedItem();
                //add a default Spinner value
                if (!firstNameEntered.isEmpty() &&
                        !lastNameEntered.isEmpty() && !emailEntered.isEmpty()
                        && !passwordEntered.isEmpty()) {
                    User newUser = new User(firstNameEntered, lastNameEntered,
                            emailEntered, userTypeSelected);
                    handler.createNewUser(newUser, passwordEntered,
                            RegistrationActivity.this, RegistrationActivity.this);
                } else {
                    Toast.makeText( RegistrationActivity.this,
                            "Must fill in all info boxes.", Toast.LENGTH_SHORT ).show();
                }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Registration", "Registration Canceled");
                Intent intent = new Intent(RegistrationActivity.this, StartUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
