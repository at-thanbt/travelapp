package com.example.asiantech.travelapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.utils.Constant;

/**
 * Created by asiantech on 11/03/2017.
 */
public class RuleActivity extends AppCompatActivity {
    private RadioGroup mRdgRole;
    private RadioButton mRdbTourGuide;
    private RadioButton mRdbTourRist;
    private Button mBtnChoose;
    private SharedPreferences mSharedPreferencesLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);
        mSharedPreferencesLogin = getSharedPreferences(Constant.DATA_USER_LOGIN, MODE_PRIVATE);
        mRdgRole = (RadioGroup) findViewById(R.id.radioGroup);
        mRdbTourGuide = (RadioButton) findViewById(R.id.radioButton_tourgide);
        mRdbTourRist = (RadioButton) findViewById(R.id.radioButton_tourist);
        mBtnChoose = (Button) findViewById(R.id.btnchoose);
        mBtnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                int role = 0;
                if (mRdbTourGuide.isChecked()) {
                    String isLogin = mSharedPreferencesLogin.getString(Constant.IS_USER_LOGIN, "false");
                    if ("true".equals(isLogin)) {
                        intent = new Intent(RuleActivity.this, MainTourGuideActivity.class);
                    } else {

                        intent = new Intent(RuleActivity.this, LoginTourGuideActivity.class);
                        intent.putExtra("role", role);
                    }
                } else {
                    role = 1;
                    intent = new Intent(RuleActivity.this, LoginTourRistActivity.class);
                    intent.putExtra("role", role);
                }

                startActivity(intent);

            }
        });
    }
}
