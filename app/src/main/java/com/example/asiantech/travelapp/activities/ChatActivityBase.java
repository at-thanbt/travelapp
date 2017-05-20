package com.example.asiantech.travelapp.activities;

import android.support.v7.app.AppCompatActivity;

abstract class ChatActivityBase extends AppCompatActivity {
    public static boolean isShowing = false;

    @Override
    protected void onStart() {
        super.onStart();
        isShowing = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isShowing = false;
    }
}
