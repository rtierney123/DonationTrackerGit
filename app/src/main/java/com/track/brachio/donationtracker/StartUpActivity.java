package com.track.brachio.donationtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class StartUpActivity extends AppCompatActivity {
    Button toLoginPageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_startup );

        toLoginPageButton = (Button) findViewById(R.id.loginButton);
        toLoginPageButton.setOnClickListener((view) -> {
            setContentView(R.layout.activity_login);
        });

        //Intent intent = new Intent(StartUpActivity.this, LoginActivity.class);
        //startActivity(intent);
    }
}
