package com.example.asiantech.travelapp.activities;

import android.app.ProgressDialog;
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
import com.example.asiantech.travelapp.activities.objects.Code;
import com.example.asiantech.travelapp.activities.objects.Conversation;
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
 * Created by phuong on 16/05/2017.
 */

public class DetailTourActivity extends AppCompatActivity {
    private String mIdTour;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

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
        switch (item.getItemId()) {
            case R.id.itemAddPlan:
                // TODO: 17/05/2017 Handle click add plan
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
                Intent intent = new Intent(DetailTourActivity.this, AddTourristActivity.class);
                intent.putExtra(HomeBlankFragment.ID_TOUR, mIdTour);
                startActivity(intent);
                break;
            case R.id.itemSingleChat:
                Intent intent1 = new Intent(this, ConversationActivity.class);
                intent1.putExtra(ConversationActivity.USER_ID, App.getInstance().getIdTourguide());
                intent1.putExtra(ConversationActivity.USER_NAME, App.getInstance().getNameTourguide());
                startActivity(intent1);
                break;
            case R.id.itemGroupChat:
                startGroupChat();
                break;
        }
        return false;
    }

    private void startGroupChat() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Firebase.setAndroidContext(this);
        Firebase codeRef = new Firebase("https://travelapp-4961a.firebaseio.com/code");
        final Firebase conversationRef = new Firebase("https://travelapp-4961a.firebaseio.com/conversations");
        final String conversationId = UUID.randomUUID().toString();

        codeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final Conversation conversation = createConversation(conversationRef, conversationId, App.getInstance().getIdTourguide());
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Code code = snapshot.getValue(Code.class);
                                createConversation(conversationRef, conversationId, code.getIdUser());
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(DetailTourActivity.this, GroupChatActivity.class);
                                    intent.putExtra(GroupChatActivity.CONVERSATION, conversation);
                                    intent.putExtra(GroupChatActivity.USER_ID, App.getInstance().getIdTourguide());
                                    intent.putExtra(GroupChatActivity.USER_NAME, App.getInstance().getNameTourguide());
                                    startActivity(intent);

                                    progressDialog.dismiss();
                                }
                            });
                        }
                    }).start();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private Conversation createConversation(Firebase conversationRef, String conversationId, String idUser) {
        Conversation conversation = new Conversation();
        conversation.setId(conversationId);
        conversation.setAnotherGuyName(null);
        conversation.setGroup(true);

        Map<String, Object> map = new HashMap<>();
        map.put(conversationId, conversation);

        conversationRef.child(idUser).setValue(map);

        return conversation;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        ScheduleFragment scheduleFragment = new ScheduleFragment();
        scheduleFragment.setIdTour(mIdTour);
        adapter.addFragment(scheduleFragment, "Kế Hoạch");
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
}
