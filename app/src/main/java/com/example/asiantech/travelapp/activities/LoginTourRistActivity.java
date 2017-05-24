package com.example.asiantech.travelapp.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.fragments.NotifyTourFragment;
import com.example.asiantech.travelapp.activities.fragments.ScheduleTourFragment;
import com.example.asiantech.travelapp.activities.objects.LocationNotification;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by asiantech on 11/03/2017.
 */
public class LoginTourRistActivity extends LoginActivity implements ChildEventListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Firebase tourNotificationRef;
    private SharedPreferences sharedPreferences;
    private static final String NOTIFICATIONS_SET = "notification-set";
    private Set<String> notificationSet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_tourrist);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        sharedPreferences = getSharedPreferences("read-notifications", MODE_PRIVATE);
        notificationSet = sharedPreferences.getStringSet(NOTIFICATIONS_SET, new HashSet<String>());

        startMessagesWatcher(App.getInstance().getIdTourist(), App.getInstance().getNameTourist());
        startNotificationsWatcher();
    }

    @Override
    protected void onDestroy() {
        if (tourNotificationRef != null)
            tourNotificationRef.removeEventListener(this);
        super.onDestroy();
    }

    private void startNotificationsWatcher() {
        String idTour = App.getInstance().getIdTour();
        tourNotificationRef = new Firebase("https://travelapp-4961a.firebaseio.com/notifications").child(idTour);
        tourNotificationRef.addChildEventListener(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ScheduleTourFragment(), "Kế Hoạch");
        adapter.addFragment(new NotifyTourFragment(), "Thông báo");
        viewPager.setAdapter(adapter);
    }

    private void showNotification(LocationNotification locationNotification) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle("Thông điệp từ " + locationNotification.getWho())
                .setContentText(locationNotification.getContent()) // message for notification
                .setAutoCancel(true); // clear notification after click

        Intent intent;
        intent = new Intent(this, LocationNotificationActivity.class);
        intent.putExtra(LocationNotificationActivity.LOCATION_NOTIFICATION, locationNotification);

        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        LocationNotification locationNotification = dataSnapshot.getValue(LocationNotification.class);
        for (String notificationId : notificationSet) {
            if (notificationId.equals(locationNotification.getId())) {
                return;
            }
        }
        showNotification(locationNotification);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

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
}
