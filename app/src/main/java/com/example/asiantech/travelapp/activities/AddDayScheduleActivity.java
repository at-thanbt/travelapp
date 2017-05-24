package com.example.asiantech.travelapp.activities;

import android.app.TimePickerDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.dialog.ChooseLocationDialog;
import com.example.asiantech.travelapp.activities.objects.DaySchedule;
import com.example.asiantech.travelapp.activities.utils.Common;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * Created by phuong on 16/05/2017.
 */
public class AddDayScheduleActivity extends BaseActivity {
    public static final String ID_TOUR_SCHEDULE = "idTourSchedule";
    private EditText mEdtTime;
    private EditText mEdtNote;
    private EditText mEdtTitle;
    private EditText mEdtContent;
    private Toolbar mToolbar;
    private TextView mTvLocation;

    private ImageView mBtnBack;
    private ImageView mBtnSave;

    private String mIdTourSchedule;
    private LatLng mLatLng;

    private List<DaySchedule> mDaySchedules;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_day);

        mIdTourSchedule = getIntent().getStringExtra(ID_TOUR_SCHEDULE);

        mEdtTime = (EditText) findViewById(R.id.edtTime);
        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        mEdtTitle = (EditText) findViewById(R.id.edtTitle);
        mEdtNote = (EditText) findViewById(R.id.edtNote);
        mEdtContent = (EditText) findViewById(R.id.edtContent);
        mTvLocation = (TextView) findViewById(R.id.tvLocation);
        TextView btnChooseLocation = (TextView) findViewById(R.id.btnChooseLocation);
        btnChooseLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseLocationDialog dialog = new ChooseLocationDialog();
                dialog.setOnChooseLocationListener(new ChooseLocationDialog.OnChooseLocationListener() {
                    @Override
                    public void onChooseLocation(LatLng latLng) {
                        mLatLng = latLng;
                        Geocoder geocoder;
                        List<Address> addresses;
                        geocoder = new Geocoder(AddDayScheduleActivity.this, Locale.getDefault());
                        try {
                            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                            if (addresses != null && !addresses.isEmpty()) {
                                String address = "";
                                if (addresses.get(0).getMaxAddressLineIndex() > 0 && !TextUtils.isEmpty(addresses.get(0).getAddressLine(0))) {
                                    address = addresses.get(0).getAddressLine(0);
                                }
                                String city = addresses.get(0).getAdminArea();
                                String country = addresses.get(0).getCountryName();
                                mTvLocation.setText(String.format("%s\t%s\t%s", address, city, country));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                dialog.show(getFragmentManager(), ChooseLocationDialog.class.getSimpleName());
            }
        });

        getSchedule();

        mBtnBack = (ImageView) mToolbar.findViewById(R.id.imgBack);
        mBtnSave = (ImageView) mToolbar.findViewById(R.id.imgSave);


        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR);
        final int minute = calendar.get(Calendar.MINUTE);

        mEdtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog datePickerDialog = new TimePickerDialog(AddDayScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mEdtTime.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, true);
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
                if (TextUtils.isEmpty(mEdtContent.getText().toString())) {
                    Common.createDialog(AddDayScheduleActivity.this, "Vui lòng nhập nội dung");
                    mEdtContent.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(mEdtTime.getText().toString())) {
                    Common.createDialog(AddDayScheduleActivity.this, "Vui lòng nhập thời gian");
                    mEdtTime.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(mEdtNote.getText().toString())) {
                    Common.createDialog(AddDayScheduleActivity.this, "Vui lòng nhập ghi chú");
                    mEdtNote.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(mEdtTitle.getText().toString())) {
                    Common.createDialog(AddDayScheduleActivity.this, "Vui lòng nhập tiêu đề");
                    mEdtTitle.requestFocus();
                    return;
                }
                if (mLatLng == null) {
                    Common.createDialog(AddDayScheduleActivity.this, "Vui lòng chọn địa điểm");
                    return;
                }

                Firebase firebaseSchedule = new Firebase("https://travelapp-4961a.firebaseio.com/DaySchedules/" + mIdTourSchedule);
                Map<String, Object> map = new HashMap<>();

                map.put("idDaySchedule", UUID.randomUUID().toString());
                map.put("time", mEdtTime.getText().toString());
                map.put("content", mEdtContent.getText().toString());
                map.put("lat", mLatLng.latitude);
                map.put("lng", mLatLng.longitude);
                map.put("note", mEdtNote.getText().toString());
                map.put("title", mEdtTitle.getText().toString());
                int position = 0;
                if (mDaySchedules != null) {
                    position = mDaySchedules.size();
                }
                firebaseSchedule.child(String.valueOf(position)).setValue(map);
                Toast.makeText(AddDayScheduleActivity.this, "Tạo kế hoạch thành công", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void getSchedule() {
        Firebase firebaseSchedule = new Firebase(String.format("%sDaySchedules/%s", getString(R.string.URL_BASE), mIdTourSchedule));
        firebaseSchedule.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.exists()) {
                    mDaySchedules = new ArrayList<>();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        mDaySchedules.add(data.getValue(DaySchedule.class));
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