package com.example.asiantech.travelapp.activities.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.AddScheduleActivity;
import com.example.asiantech.travelapp.activities.App;
import com.example.asiantech.travelapp.activities.DetailTourActivity;
import com.example.asiantech.travelapp.activities.RuleActivity;
import com.example.asiantech.travelapp.activities.ScheduleDetailActivity;
import com.example.asiantech.travelapp.activities.adapters.ScheduleAdapter;
import com.example.asiantech.travelapp.activities.objects.TourSchedule;
import com.example.asiantech.travelapp.activities.response.ScheduleResponse;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by phuong on 18/05/2017.
 */

public class ScheduleTourFragment extends Fragment implements ScheduleAdapter.OnTourScheduleListener  {
    private RecyclerView mRecyclerViewSchedule;
    private TextView mTvNoSchedule;
    private ProgressBar mProgressBarLoading;
    private TextView mTvTitle;

    private List<TourSchedule> mTourSchedules = new ArrayList<>();

    private String mIdTour;
    private FloatingActionButton mBtnAdd;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_schedule_fragment, container, false);
        mBtnAdd = (FloatingActionButton) view.findViewById(R.id.btnAddSchedule);
        mIdTour = App.getInstance().getIdTour();

        mRecyclerViewSchedule = (RecyclerView) view.findViewById(R.id.recyclerViewSchedule);
        mTvNoSchedule = (TextView) view.findViewById(R.id.tvNoSchedule);
        mProgressBarLoading = (ProgressBar) view.findViewById(R.id.progressBarLoading);
        mTvTitle = (TextView) view.findViewById(R.id.tvTitle);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewSchedule.setLayoutManager(layoutManager);
        ScheduleAdapter adapter = new ScheduleAdapter(mTourSchedules, this);
        mRecyclerViewSchedule.setAdapter(adapter);
        getDataSchedule();

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        return view;
    }

    public void showDialog(){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_menu_message);
        final Button btnHistory = (Button) dialog.findViewById(R.id.btnHistory);
        final Button btnMessage = (Button) dialog.findViewById(R.id.btnMessage);

        dialog.show();
    }

    public void getDataSchedule() {
        mTourSchedules.clear();
        Firebase firebaseSchedule = new Firebase(String.format("%sTourSchedules/%s", getString(R.string.URL_BASE), mIdTour));
        firebaseSchedule.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mProgressBarLoading.setVisibility(View.GONE);
                ScheduleResponse response = dataSnapshot.getValue(ScheduleResponse.class);
                Log.d("tag123",response.toString());
                if (response != null) {
                    mTourSchedules.addAll(response.getSchedules());
                    mTvTitle.setText(response.getTitle());
                    if (mTourSchedules.size() == 0) {
                        mTvNoSchedule.setVisibility(View.VISIBLE);
                    } else {
                        mRecyclerViewSchedule.getAdapter().notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    @Override
    public void onTourScheduleClick(int position) {
        TourSchedule tourSchedule = mTourSchedules.get(position);
        Intent intent = new Intent(getContext(), ScheduleDetailActivity.class);
        intent.putExtra(ScheduleDetailActivity.TITLE_KEY, mTvTitle.getText().toString());
        intent.putExtra(ScheduleDetailActivity.TOUR_SCHEDULE_DATE_ID, tourSchedule.getIdTourSchedule());
        intent.putExtra(ScheduleDetailActivity.DATE_TIME_KEY, tourSchedule.getDate());
        startActivity(intent);
    }
}
