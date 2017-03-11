package com.example.asiantech.travelapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;
import android.view.Menu;

import com.example.asiantech.travelapp.R;

/**
 * Created by asiantech on 11/03/2017.
 */
public class SplashActivity extends AppCompatActivity{
    private  final int SPLASH_DISPLAY_LENGHT = 2000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, RuleActivity.class);
                startActivity(mainIntent);
               // SplashActivity.this.finish();

            }
        },SPLASH_DISPLAY_LENGHT);
    }
}
