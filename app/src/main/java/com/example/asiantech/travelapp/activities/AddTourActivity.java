package com.example.asiantech.travelapp.activities;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.objects.Tour;
import com.example.asiantech.travelapp.activities.utils.Common;
import com.example.asiantech.travelapp.activities.utils.Constant;
import com.firebase.client.Firebase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by asiantech on 30/04/2017.
 */
public class AddTourActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferencesUserLogin;
    private Toolbar mToolbar;

    private EditText mEdtDestination;
    private EditText mEdtStartDate;
    private EditText mEdtEndDate;
    private EditText mEdtTourName;
    private EditText mEdtDescription;
    private EditText mEdtMaximumPerson;

    private ImageView mImgBack;
    private ImageView mImgSave;

    private Calendar mCalendar;
    private int mYear;
    private int mMonth;
    private int mDay;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tour);
        Firebase.setAndroidContext(this);
        mToolbar = (Toolbar) findViewById(R.id.toolBar);

        mCalendar = Calendar.getInstance();
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

        mSharedPreferencesUserLogin = getSharedPreferences(Constant.DATA_USER_LOGIN, 0);
        inits();
    }

    void inits() {
        initActionbar();

        mEdtDestination = (EditText) findViewById(R.id.edtDestination);
        mEdtStartDate = (EditText) findViewById(R.id.edtStartDate);
        mEdtEndDate = (EditText) findViewById(R.id.edtEndDate);
        mEdtTourName = (EditText) findViewById(R.id.edtTourName);
        mEdtDescription = (EditText) findViewById(R.id.edtDescription);
        mEdtMaximumPerson = (EditText) findViewById(R.id.edtMaximumPerson);

        mEdtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTourActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        mEdtStartDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        mEdtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTourActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        mEdtEndDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    private void initActionbar() {
        setSupportActionBar(mToolbar);
        mImgBack = (ImageView) mToolbar.findViewById(R.id.imgBack);
        mImgSave = (ImageView) mToolbar.findViewById(R.id.imgSave);

        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mImgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(mEdtDestination.getText().toString())) {
                    Common.createDialog(AddTourActivity.this, "Vui lòng nhập điểm đến");
                    mEdtDestination.requestFocus();
                    return;
                }
                if (isEmpty(mEdtTourName.getText().toString())) {
                    Common.createDialog(AddTourActivity.this, "Vui lòng nhập tên tour");
                    mEdtTourName.requestFocus();
                    return;
                }
                if (isEmpty(mEdtMaximumPerson.getText().toString())) {
                    Common.createDialog(AddTourActivity.this, "Vui lòng nhập số người");
                    mEdtMaximumPerson.requestFocus();
                    return;
                }
                if (isEmpty(mEdtDescription.getText().toString())) {
                    Common.createDialog(AddTourActivity.this, "Vui lòng nhập mô tả");
                    mEdtDescription.requestFocus();
                    return;
                }
                if (isEmpty(mEdtStartDate.getText().toString())) {
                    Common.createDialog(AddTourActivity.this, "Vui lòng nhập ngày bắt đầu");
                    mEdtStartDate.requestFocus();
                    return;
                }

                if (isEmpty(mEdtEndDate.getText().toString())) {
                    Common.createDialog(AddTourActivity.this, "Vui lòng nhập ngày kết thúc");
                    mEdtEndDate.requestFocus();
                    return;
                }

                try {
                    if (sdf.parse(mEdtEndDate.getText().toString()).before(sdf.parse(mEdtStartDate.getText().toString()))) {
                        Common.createDialog(AddTourActivity.this, "Thời gian tour không chính xác");
                        mEdtEndDate.requestFocus();
                        return;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Firebase mFirebaseTour = new Firebase("https://travelapp-4961a.firebaseio.com/tours");
                Tour tour = new Tour();
                tour.setDescriptionn(mEdtDescription.getText().toString());
                tour.setDestination(mEdtDestination.getText().toString());
                tour.setEndDate(mEdtEndDate.getText().toString());
                tour.setStartDate(mEdtStartDate.getText().toString());
                tour.setIdTour(UUID.randomUUID().toString());
                tour.setMaximumPerson(Integer.parseInt(mEdtMaximumPerson.getText().toString()));
                tour.setTourName(mEdtTourName.getText().toString());
                tour.setUsernameTourGuide(App.getInstance().getNameTourguide());
                tour.setIdTourGuide(App.getInstance().getIdTourguide());
                mFirebaseTour.push().setValue(tour);

                Toast.makeText(AddTourActivity.this, "Tạo tour thành công", Toast.LENGTH_SHORT).show();
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
