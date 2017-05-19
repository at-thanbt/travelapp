package com.example.asiantech.travelapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.adapters.SettingMenuAdapter;
import com.example.asiantech.travelapp.activities.fragments.NotifyFragment;
import com.example.asiantech.travelapp.activities.fragments.ChatFragment;
import com.example.asiantech.travelapp.activities.fragments.HomeBlankFragment;
import com.example.asiantech.travelapp.activities.fragments.MapFragment;
import com.example.asiantech.travelapp.activities.fragments.ScheduleFragment;
import com.example.asiantech.travelapp.activities.objects.MenuItem;
import com.example.asiantech.travelapp.activities.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

public class MainTourGuideActivity extends AppCompatActivity implements View.OnClickListener, SettingMenuAdapter.OnMenuItemClickListener {
    public ActionBarDrawerToggle mDrawerToggle;
    protected View mRlContainer;
    protected RecyclerView mRecyclerViewMenu;
    protected DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private List<Object> mItems;
    private SettingMenuAdapter mAdapter;
    private LinearLayout mBtnHome;
    private LinearLayout mBtnSchedule;
    private LinearLayout mBtnAlert;
    private LinearLayout mBtnMap;
    private LinearLayout mBtnChat;
    private TextView mTvTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tourguide);
        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mRecyclerViewMenu = (RecyclerView) findViewById(R.id.frLeftMenuContainer);
        mRlContainer = findViewById(R.id.frMainContainer);
        inits();
    }

    void inits() {
        initActionbar();
        initMenu();
        initMain();
    }

    private void initMain() {
        setTitleMenu("Trang chủ");

        getSupportFragmentManager().beginTransaction().replace(R.id.frMainContainer, new HomeBlankFragment()).commit();
        mBtnHome = (LinearLayout) findViewById(R.id.btnHome);
        mBtnSchedule = (LinearLayout) findViewById(R.id.btnSchedule);
        mBtnAlert = (LinearLayout) findViewById(R.id.btnAlert);
        mBtnMap = (LinearLayout) findViewById(R.id.btnMap);
        mBtnChat = (LinearLayout) findViewById(R.id.btnChat);

        mBtnHome.setOnClickListener(this);
        mBtnSchedule.setOnClickListener(this);
        mBtnAlert.setOnClickListener(this);
        mBtnMap.setOnClickListener(this);
        mBtnChat.setOnClickListener(this);

    }

    private void initActionbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.menu);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTvTitle = (TextView) mToolbar.findViewById(R.id.titleToolbar);
    }

    public void initMenu() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewMenu.setLayoutManager(layoutManager);
        initsData();
        mAdapter = new SettingMenuAdapter(mItems, this, this);
        mRecyclerViewMenu.setAdapter(mAdapter);

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
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.menu);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) mRecyclerViewMenu.getLayoutParams();
        params.width = ScreenUtil.getWidthScreen(this) - ScreenUtil.convertDPToPixels(this, 76);

    }

    public void setTitleMenu(String title) {
        mTvTitle.setText(title);
    }

    public void initsData() {
        mItems = new ArrayList<>();
        mItems.add("header");
        mItems.add(new MenuItem(R.drawable.ic_home, "Travel App", 0));
        mItems.add(new MenuItem(R.drawable.ic_user_infor, "Thông tin cá nhân", 1));
        mItems.add(new MenuItem(R.drawable.ic_create, "Chat", 2));
        mItems.add(new MenuItem(R.drawable.ic_settings, "Cài đặt", 3));
        mItems.add(new MenuItem(R.drawable.ic_logout, "Đăng xuất", 4));
        mItems.add(1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnHome:
                setTitleMenu("Trang chủ");
                getSupportFragmentManager().beginTransaction().replace(R.id.frMainContainer, new HomeBlankFragment()).commit();
                break;
            case R.id.btnSchedule:
                setTitleMenu("Kế hoạch");
                getSupportFragmentManager().beginTransaction().replace(R.id.frMainContainer, new ScheduleFragment()).commit();
                break;
            case R.id.btnAlert:
                setTitleMenu("Thông báo");
                getSupportFragmentManager().beginTransaction().replace(R.id.frMainContainer, new NotifyFragment()).commit();
                break;
            case R.id.btnMap:
                setTitleMenu("Bản đồ");
                getSupportFragmentManager().beginTransaction().replace(R.id.frMainContainer, new MapFragment()).commit();

                break;
            case R.id.btnChat:
                setTitleMenu("Nhắn tin");
                getSupportFragmentManager().beginTransaction().replace(R.id.frMainContainer, new ChatFragment()).commit();
                break;

        }
    }

    @Override
    public void onMenuItemClick(MenuItem item) {
        if (item.getId() == 4) {// logout
            App.getInstance().setIdTour(null);
            App.getInstance().setIdTourguide(null);
            App.getInstance().setIdTourist(null);
            App.getInstance().setNameTourguide(null);
            App.getInstance().setNameTourist(null);
            startActivity(new Intent(this, SplashActivity.class));
            finish();
        }
    }
}

