package com.example.asiantech.travelapp.activities.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asiantech.travelapp.R;

/**
 * Created by phuong on 18/05/2017.
 */

public class NotifyTourFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notify_tour_fragment, container, false);

        return view;
    }
}
