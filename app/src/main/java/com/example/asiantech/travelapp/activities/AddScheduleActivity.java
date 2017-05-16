package com.example.asiantech.travelapp.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.fragments.HomeBlankFragment;
import com.example.asiantech.travelapp.activities.utils.Common;
import com.firebase.client.Firebase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by phuong on 16/05/2017.
 */

public class AddScheduleActivity extends AppCompatActivity {
    private EditText mEdtDestination;
    private EditText mEdtTime;
    private EditText mEdtNote;
    private Toolbar mToolbar;

    private ImageView mBtnBack;
    private ImageView mBtnSave;

    private String mIdTour;

    private Calendar mCalendar;
    private int mMinute;
    private int mHour;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        mEdtDestination = (EditText) findViewById(R.id.edtDestination);
        mEdtNote = (EditText) findViewById(R.id.edtNote);
        mEdtTime = (EditText) findViewById(R.id.edtTime);
        mToolbar = (Toolbar) findViewById(R.id.toolBar);

        mBtnBack = (ImageView) mToolbar.findViewById(R.id.imgBack);
        mBtnSave = (ImageView) mToolbar.findViewById(R.id.imgSave);

        mIdTour = getIntent().getStringExtra(HomeBlankFragment.ID_TOUR);

        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);

        mEdtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddScheduleActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                mEdtTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
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
                if (isEmpty(mEdtDestination.getText().toString())) {
                    Common.createDialog(AddScheduleActivity.this, "Vui lòng nhập địa điểm");
                    mEdtDestination.requestFocus();
                    return;
                }
                if (isEmpty(mEdtNote.getText().toString())) {
                    Common.createDialog(AddScheduleActivity.this, "Vui lòng nhập ghi chú");
                    mEdtNote.requestFocus();
                    return;
                }
                if (isEmpty(mEdtTime.getText().toString())) {
                    Common.createDialog(AddScheduleActivity.this, "Vui lòng nhập thời gian");
                    mEdtTime.requestFocus();
                    return;
                }

                Firebase firebaseSchedule = new Firebase("https://travelapp-4961a.firebaseio.com/schedule/"+mIdTour);
                Map map = new HashMap();
                map.put("idSchedule", UUID.randomUUID().toString());
                map.put("location", mEdtDestination.getText().toString());
                map.put("time", mEdtTime.getText().toString());
                map.put("content", mEdtNote.getText().toString());
                firebaseSchedule.push().setValue(map);

                Toast.makeText(AddScheduleActivity.this, "Tạo kế hoạch thành công", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }

    public boolean isEmpty(String input) {
        if (input.length() == 0) {
            return true;
        }
        return false;
    }
}

