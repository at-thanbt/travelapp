package com.example.asiantech.travelapp.activities.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by phuong on 06/04/2017.
 */

public class ViewPagerHomeAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;

    public ViewPagerHomeAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return this.mFragments.get(position);
    }
}
