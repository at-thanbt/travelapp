package com.example.asiantech.travelapp.activities.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asiantech.travelapp.R;

/**
 * Created by phuong on 06/04/2017.
 */

public class ViewPagerHomeFragment extends Fragment {
    private String text;
    private int page;

    public  static ViewPagerHomeFragment newInstant(int page, String text)
    {

        ViewPagerHomeFragment frm_ =new ViewPagerHomeFragment();
        Bundle argBundle =new Bundle();
        argBundle.putInt("page",page);
        argBundle.putString("content",text);
        frm_.setArguments(argBundle);
        return frm_;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("page");
        text = getArguments().getString("content");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.viewpager_home_fragment,container,false);

        TextView view1 = (TextView) view.findViewById(R.id.tvViewPgHome);
        view1.setText(page +", "+text);
        return view;
    }

}
