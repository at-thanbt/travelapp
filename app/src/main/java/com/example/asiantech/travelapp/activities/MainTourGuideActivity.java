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

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.adapters.SettingMenuAdapter;
import com.example.asiantech.travelapp.activities.fragments.MainFragment;
import com.example.asiantech.travelapp.activities.objects.MenuItem;
import com.example.asiantech.travelapp.activities.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

public class MainTourGuideActivity extends AppCompatActivity {
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
        getSupportFragmentManager().beginTransaction().replace(R.id.frMainContainer, new MainFragment()).commit();

    }

    private void initActionbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_menu);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Da Nang");


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

}

