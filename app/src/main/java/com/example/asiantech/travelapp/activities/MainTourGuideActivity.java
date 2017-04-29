package com.example.asiantech.travelapp.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.adapters.SettingMenuAdapter;
import com.example.asiantech.travelapp.activities.fragments.AlertFragment;
import com.example.asiantech.travelapp.activities.fragments.ChatFragment;
import com.example.asiantech.travelapp.activities.fragments.HomeFragment;
import com.example.asiantech.travelapp.activities.fragments.MapFragment;
import com.example.asiantech.travelapp.activities.fragments.ScheduleFragment;
import com.example.asiantech.travelapp.activities.objects.MenuItem;
import com.example.asiantech.travelapp.activities.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

public class MainTourGuideActivity extends AppCompatActivity implements View.OnClickListener{
    public ActionBarDrawerToggle mDrawerToggle;
    protected View mRlContainer;
    protected RecyclerView mRecyclerViewMenu;
    protected DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private List<Object> mItems;
    private SettingMenuAdapter mAdapter;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private float mLastTranslate = 0.0f;
    private Button mBtnHome;
    private Button mBtnSchedule;
    private Button mBtnAlert;
    private Button mBtnMap;
    private Button mBtnChat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tourguide);
        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mRecyclerViewMenu = (RecyclerView) findViewById(R.id.frLeftMenuContainer);
        mRlContainer = (View) findViewById(R.id.frMainContainer);
        inits();
    }

    void inits() {
        initActionbar();
        initMenu();
        initMain();
    }

    private void initMain() {

        getSupportFragmentManager().beginTransaction().replace(R.id.frMainContainer, new HomeFragment()).commit();
        mBtnHome = (Button) findViewById(R.id.btnHome);
        mBtnSchedule = (Button) findViewById(R.id.btnSchedule);
        mBtnAlert = (Button) findViewById(R.id.btnAlert);
        mBtnMap = (Button) findViewById(R.id.btnMap);
        mBtnChat = (Button) findViewById(R.id.btnChat);

        mBtnHome.setOnClickListener(this);
        mBtnSchedule.setOnClickListener(this);
        mBtnAlert.setOnClickListener(this);
        mBtnMap.setOnClickListener(this);
        mBtnChat.setOnClickListener(this);

    }

    private void initActionbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_menu);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView mTvTitle = (TextView) mToolbar.findViewById(R.id.titleToolbar);
        mTvTitle.setText("Da Nang");


    }

    public void initMenu() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewMenu.setLayoutManager(layoutManager);
        initsData();
        mAdapter = new SettingMenuAdapter(mItems, this);
        mRecyclerViewMenu.setAdapter(mAdapter);

        mTitle = mDrawerTitle = "Home";
        mDrawerLayout.setScrimColor(getResources().getColor(R.color.drawerlayout_scrim));
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mDrawerLayout.openDrawer(GravityCompat.START);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float moveFactor = (mRecyclerViewMenu.getWidth() * slideOffset);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    mRlContainer.setTranslationX(moveFactor);
                } else {
                    TranslateAnimation anim = new TranslateAnimation(mLastTranslate, moveFactor, 0.0f, 0.0f);
                    anim.setDuration(0);
                    anim.setFillAfter(true);
                    mRlContainer.startAnimation(anim);
                    mLastTranslate = moveFactor;
                }
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) mRecyclerViewMenu.getLayoutParams();
        params.width = ScreenUtil.getWidthScreen(this) - ScreenUtil.convertDPToPixels(this, 76);


    }

    public void initsData() {
        mItems = new ArrayList<>();
        mItems.add(new String("header"));
        mItems.add(new MenuItem(R.drawable.ic_home, "Travel App"));
        mItems.add(new MenuItem(R.drawable.ic_user_infor, "Thông tin cá nhân"));
        mItems.add(new MenuItem(R.drawable.ic_create, "Chat"));
        mItems.add(new MenuItem(R.drawable.ic_settings, "Cài đặt"));
        mItems.add(new MenuItem(R.drawable.ic_logout, "Đăng xuất"));
        mItems.add(new Integer(1));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnHome:
                getSupportFragmentManager().beginTransaction().replace(R.id.frMainContainer, new HomeFragment()).commit();
                break;
            case R.id.btnSchedule:
                getSupportFragmentManager().beginTransaction().replace(R.id.frMainContainer, new ScheduleFragment()).commit();

                break;
            case R.id.btnAlert:
                getSupportFragmentManager().beginTransaction().replace(R.id.frMainContainer, new AlertFragment()).commit();

                break;
            case R.id.btnMap:
                getSupportFragmentManager().beginTransaction().replace(R.id.frMainContainer, new MapFragment()).commit();

                break;
            case R.id.btnChat:
                getSupportFragmentManager().beginTransaction().replace(R.id.frMainContainer, new ChatFragment()).commit();

                break;

        }
    }
}

