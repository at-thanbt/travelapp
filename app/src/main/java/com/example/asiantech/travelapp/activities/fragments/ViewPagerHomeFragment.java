package com.example.asiantech.travelapp.activities.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asiantech.travelapp.R;


/**
 * Created by phuong on 06/04/2017.
 */

public class ViewPagerHomeFragment extends Fragment {
    public int mIdResource;
    private String mContentPicture;
    ImageView mIvViewPgHome;
    TextView mTvViewPgHome;

    public ViewPagerHomeFragment(int idPicture, String content) {
        mIdResource = idPicture;
        mContentPicture = content;
    }

    public ViewPagerHomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_home_fragment, container, false);
        mTvViewPgHome = (TextView) view.findViewById(R.id.tvViewPgHome);
        mIvViewPgHome = (ImageView) view.findViewById(R.id.ivViewPgHome);
        mIvViewPgHome.setImageResource(mIdResource);
        mTvViewPgHome.setText(mContentPicture);
        return view;
    }


}
