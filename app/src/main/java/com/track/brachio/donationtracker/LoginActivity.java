package com.track.brachio.donationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.text.Editable;
import android.widget.Spinner;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.track.brachio.donationtracker.controller.PersistanceManager;
import com.track.brachio.donationtracker.controller.UIPopulator;
import com.track.brachio.donationtracker.model.User;
import com.track.brachio.donationtracker.model.database.FirebaseUserHandler;
import com.track.brachio.donationtracker.model.singleton.CurrentUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Activity for Login
 */
public class LoginActivity extends AppCompatActivity {
    private EditText usernameField;
    private EditText passwordField;
    private ImageButton optionButton;
    private static final int RC_SIGN_IN = 1;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.loginButton);
        Button guestButton = findViewById(R.id.guestButton);
        Button googleButton = findViewById(R.id.googleButton);
        usernameField = findViewById(R.id.emailBox);
        passwordField = findViewById(R.id.passwordBox);
        optionButton = findViewById(R.id.login_option_register);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        FirebaseUserHandler handler = new FirebaseUserHandler();

        loginButton.setOnClickListener(view -> {
            String enteredPassword;
            String enteredEmail;

            Editable passTemp = passwordField.getText();
            enteredPassword = passTemp.toString();

            Editable emailTemp = usernameField.getText();
            enteredEmail = emailTemp.toString();

            if (!enteredPassword.isEmpty() && !enteredEmail.isEmpty()){
                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                handler.signInUser( enteredEmail, enteredPassword,
                        LoginActivity.this, LoginActivity.this);
            }
        });

        guestButton.setOnClickListener(v -> {
            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            PersistanceManager manager = new PersistanceManager(LoginActivity.this);
            manager.loadAppOnStart( );
        });

        optionButton.setOnClickListener(v -> {
            //Creating the instance of PopupMenu
            PopupMenu popup = new PopupMenu( LoginActivity.this, optionButton );
            //Inflating the Popup using xml file
            popup.getMenuInflater()
                    .inflate( R.menu.login_options_menu, popup.getMenu() );
            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(item -> {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                return true;
            });

            popup.show();
        });


        //set up google login
        handler.configureGoogleSignIn( this );
        googleButton.setOnClickListener(v -> {
            handler.googleSignIn( this, RC_SIGN_IN );
        });


        //DELETE THIS STUFF
        Spinner temp = findViewById( R.id.tempLoginSpinner );
        List<String> userTypes = new ArrayList(Arrays.asList("Donator", "Manager", "Volunteer", "Admin"));
        UIPopulator ui = new UIPopulator();
        ui.populateSpinner( temp, userTypes, this );
        temp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (id == 0){
                    usernameField.setText("donator@email.com");
                } else if (id == 1){
                    usernameField.setText("manager@email.com");
                } else if (id == 2){
                    usernameField.setText("volunteer@email.com");
                } else if (id == 3){
                    usernameField.setText("admin@email.com");
                }
                passwordField.setText("password");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }



    /**
     * goes to the correct location for user type
     */
    public void goToCorrectActivity() {
        PersistanceManager manager = new PersistanceManager( this );
        manager.loadAppOnStart();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            CurrentUser.getInstance().setUser( new User() );
            goToCorrectActivity();
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }



}
