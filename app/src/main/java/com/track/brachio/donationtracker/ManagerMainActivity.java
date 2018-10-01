package com.track.brachio.donationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.track.brachio.donationtracker.model.database.FirebaseUserHandler;

public class ManagerMainActivity extends Activity {

    private Button manMainLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_manager_main );
        manMainLogout = (Button) findViewById(R.id.logoutButton1);
        manMainLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FirebaseUserHandler handler = new FirebaseUserHandler();
                handler.signOutUser();
                Intent intent = new Intent(VolunteerMainActivity.this, StartUpActivity.class);
                startActivity(intent);
            }
        });
    }

}
