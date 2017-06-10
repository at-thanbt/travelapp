package com.example.asiantech.travelapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.fragments.HomeBlank;
import com.example.asiantech.travelapp.activities.fragments.HomeFragment;
import com.firebase.client.Firebase;

/**
 * Created by asiantech on 30/04/2017.
 */
public class AddTourActivity extends AppCompatActivity{
    Firebase myFirebaseRef;
    private Toolbar mToolbar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tour);
        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        inits();
    }

    void inits() {
        initActionbar();
    }
    private void initActionbar() {
        setSupportActionBar(mToolbar);


    }

}
