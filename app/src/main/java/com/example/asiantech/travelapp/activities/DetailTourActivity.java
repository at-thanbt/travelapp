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
import android.view.Menu;
import android.view.MenuItem;
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
    public static final int ADD_PLAN_REQUEST_CODE = 123;

    private String mIdTour;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ScheduleFragment mScheduleFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tour);
        mIdTour = getIntent().getStringExtra(HomeBlankFragment.ID_TOUR);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_tour_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.itemAddPlan:
                intent = new Intent(this, AddScheduleActivity.class);
                intent.putExtra(HomeBlankFragment.ID_TOUR, mIdTour);
                startActivityForResult(intent, ADD_PLAN_REQUEST_CODE);
                break;
            case R.id.itemDeleteTrip:
                // TODO: 17/05/2017 Handle click delete trip
                break;
            case R.id.itemEditTrip:
                // TODO: 17/05/2017 Handle click edit trip
                break;
            case R.id.itemMergeTrip:
                // TODO: 17/05/2017 Handle click merge trip
                break;
            case R.id.itemPeople:
                intent = new Intent(DetailTourActivity.this, AddTourristActivity.class);
                intent.putExtra(HomeBlankFragment.ID_TOUR, mIdTour);
                startActivity(intent);
                break;
        }
        return false;
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mScheduleFragment = new ScheduleFragment();
        mScheduleFragment.setIdTour(mIdTour);
        adapter.addFragment(mScheduleFragment, "Kế Hoạch");
        adapter.addFragment(new NotifyFragment(), "Thông báo");
        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
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

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_PLAN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                mScheduleFragment.getDataSchedule();
            }
        }
    }
}
