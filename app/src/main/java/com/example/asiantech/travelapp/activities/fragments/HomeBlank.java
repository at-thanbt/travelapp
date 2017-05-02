package com.example.asiantech.travelapp.activities.fragments;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.AddTourActivity;

/**
 * Created by asiantech on 30/04/2017.
 */
@TargetApi(Build.VERSION_CODES.M)
public class HomeBlank extends Fragment implements View.OnClickListener{
    Button mBtnAddTour;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_fragment_before_add_tour, container, false);
        mBtnAddTour = (Button) view.findViewById(R.id.btnAddTour);
        mBtnAddTour.setOnClickListener(this);
        return view ;
    }

    @Override
    public void onClick(View view) {
        Intent mIntent=new Intent(getActivity(), AddTourActivity.class);
        startActivity(mIntent);
    }
}
