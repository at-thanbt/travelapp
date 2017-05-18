package com.example.asiantech.travelapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.objects.Conversation;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ConversationActivity extends AppCompatActivity implements ChildEventListener {
    static final String USER_ID = "user-id";
    static final String USER_NAME = "user-name";

    private ConversationAdapter conversationAdapter;
    private Firebase userConversations;
    private String userId;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        setupToolbar();
        setTitle("Conversations");

        userId = getIntent().getStringExtra(USER_ID);
        userName = getIntent().getStringExtra(USER_NAME);

        ((RecyclerView) findViewById(R.id.conversation_view)).setAdapter(conversationAdapter = new ConversationAdapter(new ArrayList<Conversation>()));

        Firebase.setAndroidContext(this);
        userConversations = new Firebase("https://travelapp-4961a.firebaseio.com/conversations").child(userId);
        userConversations.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Conversation conversation = snapshot.getValue(Conversation.class);
                        conversationAdapter.conversations.add(conversation);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            conversationAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        userConversations.addChildEventListener(this);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void onConversationItemClick(Conversation conversation) {
        Intent intent;
        if (conversation.isGroup()) {
            intent = new Intent(this, GroupChatActivity.class);
            intent.putExtra(GroupChatActivity.CONVERSATION, conversation);
            intent.putExtra(GroupChatActivity.USER_ID, userId);
            intent.putExtra(GroupChatActivity.USER_NAME, userName);
        } else {
            intent = new Intent(this, SingleChatActivity.class);
            intent.putExtra(SingleChatActivity.CONVERSATION, conversation);
            intent.putExtra(SingleChatActivity.USER_ID, userId);
            intent.putExtra(SingleChatActivity.USER_NAME, userName);
        }
        startActivity(intent);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        final Conversation conversation = dataSnapshot.getValue(Conversation.class);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                conversationAdapter.conversations.add(conversation);
                conversationAdapter.notifyDataSetChanged();
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

    class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder> {

        private List<Conversation> conversations;

        ConversationAdapter(List<Conversation> conversations) {
            this.conversations = conversations;
        }

        @Override
        public ConversationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_conversation, parent, false);
            return new ConversationViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ConversationViewHolder holder, int position) {
            Conversation conversation = conversations.get(position);
            holder.conversation = conversation;
            holder.nameView.setText(conversation.getAnotherGuyName());
            holder.imageView.setImageResource(conversation.isGroup() ? R.drawable.ic_people : R.drawable.ic_face);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onConversationItemClick(holder.conversation);
                }
            });
        }

        @Override
        public int getItemCount() {
            return conversations.size();
        }

        class ConversationViewHolder extends RecyclerView.ViewHolder {
            final TextView nameView;
            final ImageView imageView;

            Conversation conversation;

            ConversationViewHolder(View itemView) {
                super(itemView);
                nameView = (TextView) itemView.findViewById(R.id.name_view);
                imageView = (ImageView) itemView.findViewById(R.id.image_view);
            }
        }

    }
}
