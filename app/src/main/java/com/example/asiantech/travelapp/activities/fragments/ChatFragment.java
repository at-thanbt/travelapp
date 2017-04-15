package com.example.asiantech.travelapp.activities.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.ChatActivity;
import com.example.asiantech.travelapp.activities.adapters.ListTouristAdapter;
import com.example.asiantech.travelapp.activities.adapters.ListUserChatAdapter;
import com.example.asiantech.travelapp.activities.objects.User;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by phuong on 08/04/2017.
 */

public class ChatFragment extends Fragment implements ListUserChatAdapter.itemClick {
    TextView noUsersText;
    private List<User> mTvUsers = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;
    private Firebase mFirebaseUser;
    private ListUserChatAdapter mAdapter;
    private RecyclerView mRecyclerViewListUserChat;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_users, container, false);

        noUsersText = (TextView) view.findViewById(R.id.noUsersText);
        mRecyclerViewListUserChat = (RecyclerView) view.findViewById(R.id.rcvListUserChat);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewListUserChat.setLayoutManager(layoutManager);
        mAdapter = new ListUserChatAdapter(getContext(), mTvUsers, this);
        mRecyclerViewListUserChat.setAdapter(mAdapter);

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.show();
        Firebase.setAndroidContext(getActivity());
        mFirebaseUser = new Firebase("https://travelapp-4961a.firebaseio.com/user");
        mFirebaseUser.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                User user = new User();
                user.setPhoneNumber(map.get("phonenumber").toString());
                mTvUsers.add(user);
                mAdapter.notifyDataSetChanged();
                pd.dismiss();
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
        });
                return view;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("numberphone",mTvUsers.get(position).getPhoneNumber());
        startActivity(intent);
    }
}
