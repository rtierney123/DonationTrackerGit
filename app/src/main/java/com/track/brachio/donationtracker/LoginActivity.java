package com.track.brachio.donationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.track.brachio.donationtracker.model.User;
import com.track.brachio.donationtracker.model.database.FirebaseUserHandler;


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
        usernameField = (EditText) findViewById(R.id.emailBox );
        passwordField = (EditText) findViewById(R.id.passwordBox);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Log.d stuff? to console??
                String enteredPassword;
                String enteredEmail;

                FirebaseUserHandler handler = new FirebaseUserHandler();
                enteredPassword = passwordField.getText().toString();
                enteredEmail = usernameField.getText().toString();

                User user = new User("Test", "User", "fake@email.com", "Admin");
                handler.createNewUser(user, "password", LoginActivity.this);

                handler.signInUser( enteredEmail, enteredPassword, LoginActivity.this);
                //Boolean signInWorked = model.signInCheck(enteredUsername, enteredPassword);
                if (handler.isUserSignedIn()) {
                    Log.d("Login", "Login Successful");
                    Intent intent = new Intent(LoginActivity.this, VolunteerMainActivity.class);
                    startActivity(intent);
                    setContentView(R.layout.activity_volunteer_main);
                } else {
                    Log.d("Login", "Login Unsuccessful");
                }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_startup);
            }
        });


    }

    //    public void onLoginPressed(View view) {
//        //Log.d stuff? to console??
//        String enteredPassword;
//        String enteredUsername;
//        Model model = Model.getInstance();
//
//        enteredPassword = passwordField.getText().toString();
//        enteredUsername = usernameField.getText().toString();
//
//        Boolean signInWorked = model.signInCheck(enteredUsername, enteredPassword);
//        if (signInWorked) {
//            //go to main page
//        } else {
//            //prompt user it did not work
//        }
//
//    }

    public void onCanceledPressed(View view) {
        finish();
    }
}
