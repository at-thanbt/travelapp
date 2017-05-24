package com.example.asiantech.travelapp.activities;

import android.support.v7.app.AppCompatActivity;

import com.example.asiantech.travelapp.activities.dialog.CustomMessageDialog;

/**
 * Created by phuong on 17/05/2017.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private CustomMessageDialog mMessageDialog = new CustomMessageDialog();

    public void showMessageDialog(String message) {
        mMessageDialog.setMessage(message);
        mMessageDialog.show(getFragmentManager(), CustomMessageDialog.class.getSimpleName());
    }
}
