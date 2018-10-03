package com.track.brachio.donationtracker;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.track.brachio.donationtracker.model.User;
import com.track.brachio.donationtracker.model.UserType;
import com.track.brachio.donationtracker.model.database.FirebaseUserHandler;
import com.track.brachio.donationtracker.model.singleton.CurrentUser;


public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private Button cancelButton;
    private EditText usernameField;
    private EditText passwordField;

    //user logging in if successful
    private User _currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.loginButton);
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
        User currentUser = CurrentUser.getInstance().getUser();
        Intent intent = new Intent(LoginActivity.this, DonatorMainActivity.class);;
        if (currentUser.getUserType() == UserType.Donator) {
            intent = new Intent(LoginActivity.this, DonatorMainActivity.class);
        } else if (currentUser.getUserType() == UserType.Admin){
            intent = new Intent(LoginActivity.this, AdminMainActivity.class);
        } else if (currentUser.getUserType() == UserType.Volunteer) {
            intent = new Intent(LoginActivity.this, VolunteerMainActivity.class);
        } else if (currentUser.getUserType() == UserType.Manager) {
            intent = new Intent(LoginActivity.this, ManagerMainActivity.class);
        }
        startActivity(intent);
    }


}
