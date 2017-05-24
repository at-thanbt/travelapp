package com.example.asiantech.travelapp.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.objects.Tour;
import com.example.asiantech.travelapp.activities.utils.Constant;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

/**
 * Created by asiantech on 11/03/2017.
 */
public class RuleActivity extends AppCompatActivity {
    private RadioGroup mRdgRole;
    private RadioButton mRdbTourGuide;
    private RadioButton mRdbTourRist;
    private Button mBtnChoose;
    private SharedPreferences mSharedPreferencesLogin;

    private Firebase firebaseUser;
    private ProgressBar mProgressBarLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);
        Firebase.setAndroidContext(this);

        mSharedPreferencesLogin = getSharedPreferences(Constant.DATA_USER_LOGIN, MODE_PRIVATE);
        mProgressBarLoading = (ProgressBar) findViewById(R.id.progressBarLoading);
        mRdgRole = (RadioGroup) findViewById(R.id.radioGroup);
        mRdbTourGuide = (RadioButton) findViewById(R.id.radioButton_tourgide);
        mRdbTourRist = (RadioButton) findViewById(R.id.radioButton_tourist);
        mBtnChoose = (Button) findViewById(R.id.btnchoose);
        mBtnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (mRdbTourGuide.isChecked()) {
//                    String isLogin = mSharedPreferencesLogin.getString(Constant.IS_USER_LOGIN, "false");
//                    if ("true".equals(isLogin)) {
//                        intent = new Intent(RuleActivity.this, MainTourGuideActivity.class);
//                    } else {
//                        intent = new Intent(RuleActivity.this, LoginTourGuideActivity.class);
//                    }
                    intent = new Intent(RuleActivity.this, LoginTourGuideActivity.class);
                    startActivity(intent);
                } else {
                    //show dialog de nhap ma code
                    showInputCode();
                }

            }
        });
    }

    public void showInputCode() {
        final Dialog dialog = new Dialog(RuleActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_input_code);
        final EditText edtPhone = (EditText) dialog.findViewById(R.id.edtPhone);
        final EditText edtCode = (EditText) dialog.findViewById(R.id.edtCode);
        edtCode.requestFocus();
        edtPhone.setText("+841668583242");
        edtCode.setText("1340");

        Button btnOk = (Button) dialog.findViewById(R.id.tvBtnOk);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtCode.getText().toString().equals("")) {
                    Toast.makeText(RuleActivity.this, "Vui lòng nhập mã code", Toast.LENGTH_SHORT).show();
                } else {
                    mProgressBarLoading.setVisibility(View.VISIBLE);
                    checkCode(edtCode.getText().toString(), edtPhone.getText().toString());
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public void updateLogin() {
        firebaseUser.child("/joined").setValue("true");
    }

    public void checkCode(String code, final String phone) {
        firebaseUser = new Firebase(getString(R.string.URL_BASE) + "/code/" + code);
        firebaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    final Map map = dataSnapshot.getValue(Map.class);
                    if (!map.containsKey("phone"))
                        return;
                    if (map.get("phone").toString().equals(phone)) {
                        Log.d("tag123"," cb "+map.get("idTour").toString());
                        App.getInstance().setIdTour(map.get("idTour").toString());
                        App.getInstance().setNameTourist(map.get("name").toString());
                        App.getInstance().setIdTourist(map.get("idUser").toString());

                        new Firebase("https://travelapp-4961a.firebaseio.com/tours").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChildren()) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        Tour tour = snapshot.getValue(Tour.class);
                                        if (tour.getIdTour().equals(map.get("idTour"))) {
                                            String usernameTourGuide = tour.getUsernameTourGuide();
                                            App.getInstance().setNameTourguide(usernameTourGuide);
                                            break;
                                        }
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });

                        updateLogin();
                        mProgressBarLoading.setVisibility(View.GONE);
                        Intent intent = new Intent(RuleActivity.this, LoginTourRistActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(RuleActivity.this, "Thông tin không chính xác", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}
