package com.track.brachio.donationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.track.brachio.donationtracker.controller.PersistanceManager;
import com.track.brachio.donationtracker.model.User;
import com.track.brachio.donationtracker.model.database.FirebaseUserHandler;

/**
 * Activity for Login
 */
public class LoginActivity extends AppCompatActivity {
    private EditText usernameField;
    private EditText passwordField;
    private ImageButton optionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.loginButton);
        Button guestButton = findViewById(R.id.guestButton);
        usernameField = findViewById(R.id.emailBox);
        passwordField = findViewById(R.id.passwordBox);
        optionButton = findViewById(R.id.login_option_register);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        FirebaseUserHandler handler = new FirebaseUserHandler();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredPassword;
                String enteredEmail;

                enteredPassword = passwordField.getText().toString();
                enteredEmail = usernameField.getText().toString();

                if (!enteredPassword.isEmpty() && !enteredEmail.isEmpty()){
                    findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                    handler.signInUser( enteredEmail, enteredPassword,
                            LoginActivity.this, LoginActivity.this);
                }
            }
        });

        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                PersistanceManager manager = new PersistanceManager(LoginActivity.this);
                manager.loadAppOnStart( LoginActivity.this );
            }
        });

        optionButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu( LoginActivity.this, optionButton );
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate( R.menu.login_options_menu, popup.getMenu() );
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener( new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                        startActivity(intent);
                        return true;
                    }

                } );

                popup.show();
            }
        } );
    }



    /**
     * goes to the correct location for user type
     */
    public void goToCorrectActivity(){
        PersistanceManager manager = new PersistanceManager(this);
        manager.loadAppOnStart( this );

    }




}
