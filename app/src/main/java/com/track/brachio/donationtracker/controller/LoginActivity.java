package com.track.brachio.donationtracker;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;


import com.track.brachio.donationtracker.User;
import com.track.brachio.donationtracker.UserType;
import com.track.brachio.donationtracker.Model;



public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText usernameField;
    private EditText passwordField;

    //user logging in if successful
    private User _currentUser;


    /**
     * Setup for login page
     * Not much in here yet, just assigning vars to view items
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.loginButton);
        usernameField = (EditText) findViewById(R.id.usernameBox);
        passwordField = (EditText) findViewById(R.id.passwordBox);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Log.d stuff? to console??
                String enteredPassword;
                String enteredUsername;
                Model model = Model.getInstance();

                enteredPassword = passwordField.getText().toString();
                enteredUsername = usernameField.getText().toString();

                Boolean signInWorked = model.signInCheck(enteredUsername, enteredPassword);
                if (signInWorked) {
                    Log.d("Login", "Login Successful");
                } else {
                    Log.d("Login", "Login Unsuccessful");
                }

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
