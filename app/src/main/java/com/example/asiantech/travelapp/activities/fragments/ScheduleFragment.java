package com.example.asiantech.travelapp.activities.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.AddScheduleActivity;
import com.example.asiantech.travelapp.activities.DetailTourActivity;

/**
 * Created by phuong on 08/04/2017.
 */

public class ScheduleFragment extends Fragment {
    private FloatingActionButton mBtnAddSchedule;
    private RecyclerView mRecyclerViewSchedule;
    private TextView mTvNoSchedule;
    private DetailTourActivity mActivity;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_schedule_fragment, container,false);

        mBtnAddSchedule = (FloatingActionButton) view.findViewById(R.id.btnAddSchedule);
        mRecyclerViewSchedule = (RecyclerView) view.findViewById(R.id.recyclerViewSchedule);
        mTvNoSchedule = (TextView) view.findViewById(R.id.tvNoSchedule);

        mActivity = (DetailTourActivity) getActivity();

        mBtnAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddScheduleActivity.class);
                intent.putExtra(HomeBlankFragment.ID_TOUR,mActivity.getIdTour());
                startActivity(intent);
            }
        });

        return view;
    }

    public void getDataSchedule(){

    }

}
