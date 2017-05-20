package com.example.asiantech.travelapp.activities.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.asiantech.travelapp.activities.objects.Conversation;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MessagingService extends Service implements ChildEventListener {
    private Firebase firebase;
    private String userId;

    @Subscribe
    public void onStartFirebaseEvent(StartFirebaseEvent startFirebaseEvent) {
        if (firebase == null)
            firebase = new Firebase("https://travelapp-4961a.firebaseio.com/conversations");
        else
            firebase.removeEventListener(this);
        this.userId = startFirebaseEvent.getUserId();
        firebase.addChildEventListener(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Conversation conversation = dataSnapshot.getValue(Conversation.class);

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
}
