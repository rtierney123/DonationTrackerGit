package com.track.brachio.donationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class StartUpActivity extends AppCompatActivity {
    Button toLoginPageButton;
    Button toRegisterPageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_startup );

        toLoginPageButton = (Button) findViewById(R.id.loginButton);
        toLoginPageButton.setOnClickListener((view) -> {
            Intent intent = new Intent(StartUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        toRegisterPageButton = (Button) findViewById(R.id.registerButton);
        toRegisterPageButton.setOnClickListener((view) -> {
            Intent intent = new Intent(StartUpActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });

        //Intent intent = new Intent(StartUpActivity.this, LoginActivity.class);
        //startActivity(intent);
    }
}
