package com.example.asiantech.travelapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.objects.Conversation;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ConversationActivity extends AppCompatActivity {

    private ConversationAdapter conversationAdapter;
    private Firebase userConversations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        ((RecyclerView) findViewById(R.id.conversation_view)).setAdapter(conversationAdapter = new ConversationAdapter(new ArrayList<Conversation>()));

        Firebase.setAndroidContext(this);
        userConversations = new Firebase("https://travelapp-4961a.firebaseio.com/conversations").child(App.getInstance().getUserId());
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
    }

    private void onConversationItemClick(Conversation conversation) {
        Intent intent = new Intent(this, SingleChatActivity.class);
        intent.putExtra(SingleChatActivity.CONVERSATION, conversation);
        startActivity(intent);
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

            Conversation conversation;
            ConversationViewHolder(View itemView) {
                super(itemView);
                nameView = (TextView) itemView.findViewById(R.id.name_view);
            }
        }

    }
}
