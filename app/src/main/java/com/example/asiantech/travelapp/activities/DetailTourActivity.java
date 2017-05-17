package com.example.asiantech.travelapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.fragments.HomeBlankFragment;
import com.example.asiantech.travelapp.activities.fragments.NotifyFragment;
import com.example.asiantech.travelapp.activities.fragments.ScheduleFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phuong on 16/05/2017.
 */

public class DetailTourActivity extends AppCompatActivity {
    private String mIdTour;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private ImageView mImgAddPerson;
    private ImageView mImgBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tour);
        mIdTour = getIntent().getStringExtra(HomeBlankFragment.ID_TOUR);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        mImgAddPerson = (ImageView) toolbar.findViewById(R.id.imgAdd);
        mImgBack = (ImageView) toolbar.findViewById(R.id.imgBack);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        mImgAddPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailTourActivity.this,AddTourristActivity.class);
                intent.putExtra(HomeBlankFragment.ID_TOUR,mIdTour);
                startActivity(intent);
            }
        });

        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public String getIdTour(){
        return mIdTour;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ScheduleFragment(), "Kế Hoạch");
        adapter.addFragment(new NotifyFragment(), "Thông báo");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
