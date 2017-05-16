package com.example.asiantech.travelapp.activities.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.AddScheduleActivity;
import com.example.asiantech.travelapp.activities.DetailTourActivity;
import com.example.asiantech.travelapp.activities.adapters.ScheduleAdapter;
import com.example.asiantech.travelapp.activities.objects.Schedule;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by phuong on 08/04/2017.
 */

public class ScheduleFragment extends Fragment {
    private FloatingActionButton mBtnAddSchedule;
    private RecyclerView mRecyclerViewSchedule;
    private TextView mTvNoSchedule;
    private DetailTourActivity mActivity;
    private ProgressBar mProgressBarLoading;

    private List<Schedule> mSchedules;
    private ScheduleAdapter mAdapter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_schedule_fragment, container, false);

        mBtnAddSchedule = (FloatingActionButton) view.findViewById(R.id.btnAddSchedule);
        mRecyclerViewSchedule = (RecyclerView) view.findViewById(R.id.recyclerViewSchedule);
        mTvNoSchedule = (TextView) view.findViewById(R.id.tvNoSchedule);
        mProgressBarLoading = (ProgressBar) view.findViewById(R.id.progressBarLoading);

        mActivity = (DetailTourActivity) getActivity();

        mBtnAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddScheduleActivity.class);
                intent.putExtra(HomeBlankFragment.ID_TOUR, mActivity.getIdTour());
                startActivity(intent);
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgressBarLoading.setVisibility(View.GONE);
                if (mSchedules.size() == 0) {
                    mTvNoSchedule.setVisibility(View.VISIBLE);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
        }, 6000);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataSchedule();

    }

    public void getDataSchedule() {
        Firebase firebaseSchedule = new Firebase(getString(R.string.URL_BASE) + "/schedule/" + mActivity.getIdTour());
        mSchedules = new ArrayList<>();

        firebaseSchedule.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Map map = data.getValue(Map.class);
                    Schedule schedule = new Schedule();
                    schedule.setIdTour(mActivity.getIdTour());
                    schedule.setIdSchedule(map.get("idSchedule").toString());
                    schedule.setLocation(map.get("location").toString());
                    schedule.setNote(map.get("content").toString());
                    schedule.setTime(map.get("time").toString());
                    mSchedules.add(schedule);
                }
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                mRecyclerViewSchedule.setLayoutManager(layoutManager);
                mAdapter = new ScheduleAdapter(mSchedules, getContext());
                mRecyclerViewSchedule.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}
