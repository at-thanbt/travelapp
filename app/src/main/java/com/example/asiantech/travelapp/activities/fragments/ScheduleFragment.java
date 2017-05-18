package com.example.asiantech.travelapp.activities.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.AddScheduleActivity;
import com.example.asiantech.travelapp.activities.DetailTourActivity;
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
 * Created by phuong on 08/04/2017.
 */
public class ScheduleFragment extends Fragment implements ScheduleAdapter.OnTourScheduleListener {
    private FloatingActionButton mBtnAddSchedule;
    private RecyclerView mRecyclerViewSchedule;
    private TextView mTvNoSchedule;
    private ProgressBar mProgressBarLoading;
    private TextView mTvTitle;

    private List<TourSchedule> mTourSchedules = new ArrayList<>();

    @Setter
    @Accessors(prefix = "m")
    private String mIdTour;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_schedule_fragment, container, false);

        mBtnAddSchedule = (FloatingActionButton) view.findViewById(R.id.btnAddSchedule);
        mRecyclerViewSchedule = (RecyclerView) view.findViewById(R.id.recyclerViewSchedule);
        mTvNoSchedule = (TextView) view.findViewById(R.id.tvNoSchedule);
        mProgressBarLoading = (ProgressBar) view.findViewById(R.id.progressBarLoading);
        mTvTitle = (TextView) view.findViewById(R.id.tvTitle);

        mBtnAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddScheduleActivity.class);
                if (getActivity() instanceof DetailTourActivity) {
                    intent.putExtra(HomeBlankFragment.ID_TOUR, mIdTour);
                }
                startActivity(intent);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewSchedule.setLayoutManager(layoutManager);
        ScheduleAdapter adapter = new ScheduleAdapter(mTourSchedules, this);
        mRecyclerViewSchedule.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataSchedule();
    }

    public void getDataSchedule() {
        Firebase firebaseSchedule = new Firebase(String.format("%sTourSchedules/%s", getString(R.string.URL_BASE), mIdTour));
        firebaseSchedule.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mProgressBarLoading.setVisibility(View.GONE);
                ScheduleResponse response = dataSnapshot.getValue(ScheduleResponse.class);
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
