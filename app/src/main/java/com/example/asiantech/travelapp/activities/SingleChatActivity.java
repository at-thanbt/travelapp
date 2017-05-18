package com.example.asiantech.travelapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.objects.Conversation;
import com.example.asiantech.travelapp.activities.objects.Message;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SingleChatActivity extends AppCompatActivity implements ChildEventListener, View.OnClickListener {
    public static final String CONVERSATION = "conversation";
    public static final String USER_ID = "user-id";
    public static final String USER_NAME = "user-name";
    private Conversation conversation;
    private Firebase messagesRef;
    private MessageAdapter messageAdapter;
    private EditText inputView;
    private String userId;
    private String userName;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_chat);
        setupToolbar();

        userId = getIntent().getStringExtra(USER_ID);
        userName = getIntent().getStringExtra(USER_NAME);

        conversation = (Conversation) getIntent().getSerializableExtra(CONVERSATION);

        setTitle(conversation.getAnotherGuyName());
        recyclerView = (RecyclerView) findViewById(R.id.chat_view);
        recyclerView.setAdapter(messageAdapter = new MessageAdapter(new ArrayList<Message>(), userId, userName));

        Firebase.setAndroidContext(this);
        messagesRef = new Firebase("https://travelapp-4961a.firebaseio.com/messages").child(conversation.getId());
        messagesRef.addChildEventListener(this);

        findViewById(R.id.send_view).setOnClickListener(this);
        inputView = (EditText) findViewById(R.id.input_view);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onDestroy() {
        messagesRef.removeEventListener(this);
        super.onDestroy();
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        final Message message = dataSnapshot.getValue(Message.class);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageAdapter.messages.add(message);
                messageAdapter.notifyDataSetChanged();
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

    @Override
    public void onClick(View v) {
        if(inputView.getText().length() < 1)
            return;

        String text = inputView.getText().toString();
        Firebase messageRef = messagesRef.push();

        String messageId = messageRef.getKey();

        Message message = new Message();
        message.setId(messageId);
        message.setContent(text);
        message.setSenderId(userId);
        message.setTimestamp(new Date().getTime());

        //messageAdapter.messages.add(message);
        //messageAdapter.notifyDataSetChanged();

        messageRef.setValue(message);

        inputView.setText("");
    }

    class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
        private static final int TYPE_ITS_ME = 0;
        private static final int TYPE_YOU_GUY = 1;

        private final List<Message> messages;
        private final String userId;
        private final String name;

        MessageAdapter(List<Message> messages, String userId, String name) {
            this.messages = messages;
            this.userId = userId;
            this.name = name;
        }

        @Override
        public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            int layout = viewType == TYPE_ITS_ME ? R.layout.item_chat_right : R.layout.item_chat_left;
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(layout, parent, false);
            return new MessageViewHolder(view);
        }

        @Override
        public int getItemViewType(int position) {
            Message message = messages.get(position);
            if (message.getSenderId().equals(userId))
                return TYPE_ITS_ME;
            return TYPE_YOU_GUY;
        }

        @Override
        public void onBindViewHolder(final MessageViewHolder holder, int position) {
            Message message = messages.get(position);
            holder.message = message;
            if (message.getSenderId().equals(userId)) {
                holder.nameView.setText(name);
            } else {
                holder.nameView.setText(conversation.getAnotherGuyName());
            }
            holder.contentView.setText(message.getContent());
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        class MessageViewHolder extends RecyclerView.ViewHolder {
            final TextView nameView;
            final TextView contentView;

            Message message;

            MessageViewHolder(View itemView) {
                super(itemView);
                nameView = (TextView) itemView.findViewById(R.id.name_view);
                contentView = (TextView) itemView.findViewById(R.id.content_view);
            }
        }

    }
}
