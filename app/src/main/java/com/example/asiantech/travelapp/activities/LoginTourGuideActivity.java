package com.example.asiantech.travelapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.dialog.CustomMessageDialog;
import com.example.asiantech.travelapp.activities.utils.Constant;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

/**
 * Created by asiantech on 11/03/2017.
 */
public class LoginTourGuideActivity extends LoginActivity {

    String user, pass;
    private EditText mEdtUsername;
    private EditText mEdtPass;
    private Button mBtnLogin;
    private Firebase mFirebase;
    private boolean check = true;
    private SharedPreferences mSharedPreferencesLogin;
    private ProgressBar mProgressBarLoading;
    private App mApp;
    private Firebase conversationRef;

    private CustomMessageDialog mMessageDialog = new CustomMessageDialog();

    public void showMessageDialog(String message) {
        mMessageDialog.setMessage(message);
        mMessageDialog.show(getFragmentManager(), CustomMessageDialog.class.getSimpleName());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_tourguide);
        mProgressBarLoading = (ProgressBar) findViewById(R.id.progressBarLoading);
        mApp = (App) getApplication();

        Firebase.setAndroidContext(this);
        mFirebase = new Firebase("https://travelapp-4961a.firebaseio.com/user");

        mEdtUsername = (EditText) findViewById(R.id.edtUsername);
        mEdtPass = (EditText) findViewById(R.id.edtPass);
        mBtnLogin = (Button) findViewById(R.id.btnLogin);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEdtUsername.getText().toString().equals("")) {
                    mEdtUsername.setError("Vui lòng nhập tên");
                } else if (mEdtPass.getText().toString().equals("")) {
                    mEdtPass.setError("Vui lòng nhập mật khẩu");
                } else {
                    check = true;
                    mFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.hasChildren()) {
                                showMessageDialog(getString(R.string.username_or_password_invalid));
                                return;
                            }
                            boolean ok = false;
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Map map = snapshot.getValue(Map.class);
                                if (!(map.containsKey("name") && map.containsKey("pass")))
                                    continue;
                                user = map.get("name").toString();
                                pass = map.get("pass").toString();
                                Intent intent;
                                if (user.equals(mEdtUsername.getText().toString()) && pass.equals(mEdtPass.getText().toString())) {
                                    mSharedPreferencesLogin = getSharedPreferences(Constant.DATA_USER_LOGIN, MODE_PRIVATE);
                                    SharedPreferences.Editor mEditor = mSharedPreferencesLogin.edit();
                                    mEditor.putString(Constant.IS_USER_LOGIN, "true");
                                    mEditor.putString(Constant.NAME_USER_LOGIN, map.get("id").toString());
                                    mEditor.apply();

                                    mApp.setNameTourguide(user);
                                    mApp.setIdTourguide(map.get("id").toString());

                                    intent = new Intent(LoginTourGuideActivity.this, MainTourGuideActivity.class);
                                    startActivity(intent);
                                    startMessagesWatcher(map.get("id").toString(), user);
                                    ok = true;
                                    finish();
                                    break;
                                }
                            }

                            if (!ok)
                                showMessageDialog(getString(R.string.username_or_password_invalid));
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                    mProgressBarLoading.setVisibility(View.GONE);
                }

            }
        });
    }
}
