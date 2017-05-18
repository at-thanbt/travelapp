package com.example.asiantech.travelapp.activities.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.DetailTourActivity;
import com.example.asiantech.travelapp.activities.adapters.MessageAdapter;
import com.example.asiantech.travelapp.activities.objects.Message;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.List;

/**
 * Created by phuong on 18/05/2017.
 */

public class SendMessageFragment extends Fragment{

    private Firebase mFirebaseMessage = new Firebase(getString(R.string.URL_BASE) + "/messages");
    private RecyclerView mRecyclerViewMessage;
    private TextView mTvNoMessage;
    private FloatingActionButton mBtnAdd;
    private ProgressBar mProgressBarLoading;

    private String mIdPost;
    private DetailTourActivity mActivity;
    private MessageAdapter mAdapter;
    private List<Message> mMessages;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_message, container, false);
        mRecyclerViewMessage = (RecyclerView) view.findViewById(R.id.recyclerViewChat);
        mTvNoMessage = (TextView) view.findViewById(R.id.tvNoChat);
        mBtnAdd = (FloatingActionButton) view.findViewById(R.id.fab);
        mProgressBarLoading = (ProgressBar) view.findViewById(R.id.progressBarLoading);

        mActivity = (DetailTourActivity) getActivity();
        mIdPost = mActivity.getIdTour();

        getData();

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;

    }

    public void getData() {
        mFirebaseMessage.child(mIdPost).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //xu ly du lieu

                //view
                viewData(mMessages);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void viewData(List<Message> mMessages) {
        if (mMessages.size() > 0) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            mRecyclerViewMessage.setLayoutManager(layoutManager);
            mAdapter = new MessageAdapter(mMessages, getContext());
            mRecyclerViewMessage.setAdapter(mAdapter);
        } else {
            mTvNoMessage.setVisibility(View.VISIBLE);
        }
        mProgressBarLoading.setVisibility(View.GONE);
    }
}
