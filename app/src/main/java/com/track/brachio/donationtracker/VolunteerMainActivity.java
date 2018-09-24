package com.track.brachio.donationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.track.brachio.donationtracker.model.database.FirebaseUserHandler;

public class VolunteerMainActivity extends AppCompatActivity {
    private Button logoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState );
        setContentView(R.layout.activity_volunteer_main);

        logoutButton = (Button) findViewById(R.id.logoutButton1);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FirebaseUserHandler handler = new FirebaseUserHandler();
                handler.signOutUser();
                Intent intent = new Intent(VolunteerMainActivity.this, StartUpActivity.class);
                startActivity(intent);
            }
        });

    }
}
