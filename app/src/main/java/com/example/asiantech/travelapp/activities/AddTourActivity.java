package com.example.asiantech.travelapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.asiantech.travelapp.R;
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
        initData();
    }

    void inits() {
        initActionbar();
        initMain();
    }

    private void initActionbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_menu);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView mTvTitle = (TextView) mToolbar.findViewById(R.id.titleToolbar);
        mTvTitle.setText("ADD A TOUR");
    }

    private void initMain() {

        getSupportFragmentManager().beginTransaction().replace(R.id.frMainContainer, new HomeFragment()).commit();

    }
}
