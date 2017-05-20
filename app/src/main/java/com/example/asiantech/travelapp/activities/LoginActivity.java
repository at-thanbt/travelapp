package com.example.asiantech.travelapp.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.objects.Conversation;
import com.example.asiantech.travelapp.activities.objects.Message;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

abstract class LoginActivity extends AppCompatActivity {
    private Firebase messagesRef;
    private Firebase userConversationsRef;
    private String userId;
    private String userName;

    private ConversationHandler conversationHandler;
    private Map<String, MessageHandler> messageHandlerMap;

    void startWatcher(String userId, String userName) {
        if (userConversationsRef != null)
            userConversationsRef.removeEventListener(conversationHandler);

        if (messagesRef != null && messageHandlerMap != null) {
            for (String conversationId : messageHandlerMap.keySet()) {
                messagesRef.child(conversationId).removeEventListener(messageHandlerMap.get(conversationId));
            }
        }

        this.userId = userId;
        this.userName = userName;

        messagesRef = new Firebase("https://travelapp-4961a.firebaseio.com/messages");

        messageHandlerMap = new HashMap<>();
        userConversationsRef = new Firebase("https://travelapp-4961a.firebaseio.com/conversations").child(userId);
        userConversationsRef.addChildEventListener(conversationHandler = new ConversationHandler());
    }

    private class ConversationHandler implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Conversation conversation = dataSnapshot.getValue(Conversation.class);

            MessageHandler messageHandler = new MessageHandler(conversation);
            messagesRef.child(conversation.getId()).addChildEventListener(messageHandler);
            messageHandlerMap.put(conversation.getId(), messageHandler);
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

    private class MessageHandler implements ChildEventListener {
        private final Conversation conversation;

        MessageHandler(Conversation conversation) {
            this.conversation = conversation;
        }

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            final Message message = dataSnapshot.getValue(Message.class);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (!ChatActivityBase.isShowing) {
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(LoginActivity.this)
                                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                                .setContentTitle("Message from " + message.getSenderName()) // title for notification
                                .setContentText(message.getContent()) // message for notification
                                .setAutoCancel(true); // clear notification after click

                        Intent intent;
                        if (conversation.isGroup()) {
                            intent = new Intent(LoginActivity.this, GroupChatActivity.class);
                            intent.putExtra(GroupChatActivity.CONVERSATION, conversation);
                            intent.putExtra(GroupChatActivity.USER_ID, userId);
                            intent.putExtra(GroupChatActivity.USER_NAME, userName);
                        } else {
                            intent = new Intent(LoginActivity.this, SingleChatActivity.class);
                            intent.putExtra(SingleChatActivity.CONVERSATION, conversation);
                            intent.putExtra(SingleChatActivity.USER_ID, userId);
                            intent.putExtra(SingleChatActivity.USER_NAME, userName);
                        }

                        PendingIntent pi = PendingIntent.getActivity(LoginActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        mBuilder.setContentIntent(pi);
                        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.notify(0, mBuilder.build());
                    }
                }
            });
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
}
