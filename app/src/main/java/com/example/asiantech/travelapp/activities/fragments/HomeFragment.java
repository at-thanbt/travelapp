package com.example.asiantech.travelapp.activities.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.AddTourristActivity;
import com.example.asiantech.travelapp.activities.adapters.ViewPagerHomeAdapter;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phuong on 19/03/2017.
 */

public class HomeFragment extends Fragment implements View.OnClickListener{
    private static float mDensity;
    private ViewPager mViewPagerHome;
    private CirclePageIndicator mIndicator;
    private ViewPagerHomeAdapter mAdapter;
    private List<Fragment> mFragments;
    private TextView mTvListTourist;

    private int[] mImages = {
            R.drawable.ic_home, R.drawable.ic_home, R.drawable.ic_settings
    };
    private String[] mContent = {"ASIAN PARK",
            "BANA HILL",
            "PHAM VAN DONG BEACH"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_fragment, container,false);
        Log.d("tag123","main");

        mViewPagerHome = (ViewPager) view.findViewById(R.id.viewpagerHome);
        mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        mTvListTourist = (TextView) view.findViewById(R.id.tvListTourist);
        mTvListTourist.setOnClickListener(this);

        String text = mTvListTourist.getText().toString();
        SpannableString spannable = new SpannableString(text);
        spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.hyper_link)), 1, mTvListTourist.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new UnderlineSpan(), 1, mTvListTourist.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvListTourist.setText(text);

        mDensity = getResources().getDisplayMetrics().density;
        mIndicator.setBackgroundColor(Color.TRANSPARENT);
        mIndicator.setRadius(5 * mDensity);
        mIndicator.setFillColor(getResources().getColor(R.color.colorAccent));
        mIndicator.setStrokeColor(getResources().getColor(R.color.colorAccent));
        mIndicator.setStrokeWidth(mDensity);

        mFragments = new ArrayList<>();
        mFragments.add(new ViewPagerHomeFragment(mImages[0],mContent[0]));
        mFragments.add(new ViewPagerHomeFragment(mImages[1],mContent[1]));
        mFragments.add(new ViewPagerHomeFragment(mImages[2],mContent[2]));

        mAdapter = new ViewPagerHomeAdapter(getActivity().getSupportFragmentManager(),mFragments);
        mViewPagerHome.setAdapter(mAdapter);
        mIndicator.setViewPager(mViewPagerHome);

        return view;
    }

    @Override
    public void onClick(View view) {
        Intent mIntent=new Intent(getActivity(), AddTourristActivity.class);
        startActivity(mIntent);

    }

}
