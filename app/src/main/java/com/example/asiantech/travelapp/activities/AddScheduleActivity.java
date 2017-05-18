package com.example.asiantech.travelapp.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.fragments.HomeBlankFragment;
import com.example.asiantech.travelapp.activities.response.ScheduleResponse;
import com.example.asiantech.travelapp.activities.utils.Common;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * Created by phuong on 16/05/2017.
 */

public class AddScheduleActivity extends AppCompatActivity {
    private EditText mEdtDate;
    private EditText mEdtDescription;
    private EditText mEdtTitle;
    private Toolbar mToolbar;

    private ImageView mBtnBack;
    private ImageView mBtnSave;

    private String mIdTour;

    private ScheduleResponse mScheduleResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        mIdTour = getIntent().getStringExtra(HomeBlankFragment.ID_TOUR);

        mEdtDescription = (EditText) findViewById(R.id.edtDescription);
        mEdtDate = (EditText) findViewById(R.id.edtDate);
        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        mEdtTitle = (EditText) findViewById(R.id.edtTitle);
        getSchedule();

        mBtnBack = (ImageView) mToolbar.findViewById(R.id.imgBack);
        mBtnSave = (ImageView) mToolbar.findViewById(R.id.imgSave);


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        mEdtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mEdtDate.setText(String.format(Locale.getDefault(), "%d/%d/%d", dayOfMonth, month, year));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEdtDescription.getText().toString())) {
                    Common.createDialog(AddScheduleActivity.this, "Vui lòng nhập ghi chú");
                    mEdtDescription.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(mEdtDate.getText().toString())) {
                    Common.createDialog(AddScheduleActivity.this, "Vui lòng nhập thời gian");
                    mEdtDate.requestFocus();
                    return;
                }

                Firebase firebaseSchedule = new Firebase("https://travelapp-4961a.firebaseio.com/TourSchedules/" + mIdTour);
                Map<String, Object> map = new HashMap<>();

                if (mScheduleResponse == null) {
                    Map<String, Object> mapTitle = new HashMap<>();
                    mapTitle.put("title", mEdtTitle.getText().toString());
                    firebaseSchedule.updateChildren(mapTitle);
                }
                map.put("idTourSchedule", UUID.randomUUID().toString());
                map.put("date", mEdtDate.getText().toString());
                map.put("description", mEdtDescription.getText().toString());
                int position = 0;
                if (mScheduleResponse != null) {
                    position = mScheduleResponse.getSchedules().size();
                }
                firebaseSchedule.child("schedules").child(String.valueOf(position)).setValue(map);
                Toast.makeText(AddScheduleActivity.this, "Tạo kế hoạch thành công", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void getSchedule() {
        Firebase firebaseSchedule = new Firebase(String.format("%sTourSchedules/%s", getString(R.string.URL_BASE), mIdTour));
        firebaseSchedule.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    mScheduleResponse = dataSnapshot.getValue(ScheduleResponse.class);
                    if (mScheduleResponse != null) {
                        mEdtTitle.setText(mScheduleResponse.getTitle());
                        mEdtTitle.setEnabled(false);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // No-op
            }
        });
    }
}