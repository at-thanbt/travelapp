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
import android.view.View;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.fragments.NotifyTourFragment;
import com.example.asiantech.travelapp.activities.fragments.ScheduleTourFragment;
import com.example.asiantech.travelapp.activities.objects.Conversation;
import com.example.asiantech.travelapp.activities.objects.Tour;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by asiantech on 11/03/2017.
 */
public class LoginTourRistActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_tourrist);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        enableChat();
    }

    private void enableChat() {
        findViewById(R.id.chat_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase.setAndroidContext(LoginTourRistActivity.this);
                new Firebase("https://travelapp-4961a.firebaseio.com/tours").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            String tourId = App.getInstance().getIdTour();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Tour tour = snapshot.getValue(Tour.class);
                                if (tourId.equals(tour.getIdTour())) {
                                    String tourguideId = tour.getIdTourGuide();
                                    Firebase conversationRef = new Firebase("https://travelapp-4961a.firebaseio.com/conversations");
                                    String conversationId = UUID.randomUUID().toString();

                                    Conversation tourguideConversation = new Conversation();
                                    tourguideConversation.setId(conversationId);
                                    tourguideConversation.setAnotherGuyName(App.getInstance().getNameTourist());
                                    Map<String, Object> tourguideMap = new HashMap<>();
                                    tourguideMap.put(conversationId, tourguideConversation);
                                    conversationRef.child(tourguideId).setValue(tourguideMap);

                                    final Conversation touristConversation = new Conversation();
                                    touristConversation.setId(conversationId);
                                    touristConversation.setAnotherGuyName(App.getInstance().getNameTourguide());
                                    Map<String, Object> touristMap = new HashMap<>();
                                    touristMap.put(conversationId, touristConversation);
                                    conversationRef.child(App.getInstance().getIdTourist()).setValue(touristMap);

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(LoginTourRistActivity.this, SingleChatActivity.class);
                                            intent.putExtra(SingleChatActivity.CONVERSATION, touristConversation);
                                            intent.putExtra(SingleChatActivity.USER_ID, App.getInstance().getIdTourist());
                                            intent.putExtra(SingleChatActivity.USER_NAME, App.getInstance().getNameTourist());
                                            startActivity(intent);
                                        }
                                    });
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ScheduleTourFragment(), "Kế Hoạch");
        adapter.addFragment(new NotifyTourFragment(), "Thông báo");
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
}
