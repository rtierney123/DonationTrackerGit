package com.track.brachio.donationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.track.brachio.donationtracker.controller.PersistanceManager;
import com.track.brachio.donationtracker.model.User;
import com.track.brachio.donationtracker.model.database.FirebaseUserHandler;


public class LoginActivity extends AppCompatActivity {
    private Button cancelButton;
    private EditText usernameField;
    private EditText passwordField;

    //user logging in if successful
    private User _currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_login);

        Button loginButton = (Button) findViewById(R.id.loginButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        usernameField = (EditText) findViewById(R.id.emailBox);
        passwordField = (EditText) findViewById(R.id.passwordBox);

        FirebaseUserHandler handler = new FirebaseUserHandler();

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("login", "clicked");
                //Log.d stuff? to console??
                String enteredPassword;
                String enteredEmail;

                enteredPassword = passwordField.getText().toString();
                enteredEmail = usernameField.getText().toString();

                if (!enteredPassword.isEmpty() && !enteredEmail.isEmpty()){
                    handler.signInUser( enteredEmail, enteredPassword, LoginActivity.this, LoginActivity.this);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("login", "canceled");
                Intent intent = new Intent(LoginActivity.this, StartUpActivity.class);
                startActivity(intent);
            }
        });


    }

    public void goToCorrectActivity(){
        PersistanceManager manager = new PersistanceManager(this);
        manager.loadAppOnStart( this );

    }




}
