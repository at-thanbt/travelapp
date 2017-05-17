package com.example.asiantech.travelapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.utils.Common;
import com.example.asiantech.travelapp.activities.utils.Constant;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

/**
 * Created by asiantech on 11/03/2017.
 */
public class LoginTourRistActivity extends AppCompatActivity {
    String  pass;
    int role;
    private EditText mEdtPass;
    private Button mBtnLogin;
    private Firebase mFirebase;
    private boolean check = true;
    private SharedPreferences mSharedPreferencesLogin;
    Intent data;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_tourrist);
        mFirebase.setAndroidContext(this);
        mFirebase = new Firebase("https://travelapp-4961a.firebaseio.com/user");

        mEdtPass = (EditText) findViewById(R.id.edtPass);
        mBtnLogin = (Button) findViewById(R.id.btnLogin);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEdtPass.getText().toString().equals("")) {
                    mEdtPass.setError("Can't be blank");
                } else {
                    final ProgressDialog pd = new ProgressDialog(LoginTourRistActivity.this);
                    pd.setMessage("Loading...");
                    pd.show();
                    check = true;
                    mFirebase.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Map map = dataSnapshot.getValue(Map.class);
                            pass = map.get("pass").toString();
                            // lấy giá trị kết quả
                            Intent intent;
                            role = getIntent().getExtras().getInt("role");
                            Log.d("tag11", String.valueOf(role));
                            if ( pass.equals(mEdtPass.getText().toString())  ) {
                                mSharedPreferencesLogin = getSharedPreferences(Constant.DATA_USER_LOGIN,MODE_PRIVATE);
                                SharedPreferences.Editor mEditor = mSharedPreferencesLogin.edit();
                                mEditor.putString(Constant.IS_USER_LOGIN,"true");
                                mEditor.apply();
                                intent = new Intent(LoginTourRistActivity.this, MainTourGuideActivity.class);
                                startActivity(intent);
                            } else {
                                Log.d("tag11", "faile");
                            }
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });

                }

            }
        });
    }
}
