package com.example.asiantech.travelapp.activities.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.objects.User;
import com.firebase.client.Firebase;

import java.util.List;

/**
 * Created by phuong on 15/04/2017.
 */

public class ListUserChatAdapter extends RecyclerView.Adapter<ListUserChatAdapter.MyViewHolder>  {
    private List<User> mTvUser;
    private Context mContext;
    private Firebase myFirebaseRef;
    private itemClick mListener;
    public ListUserChatAdapter(Context mContext, List<User> mTvUser,itemClick listener) {
        this.mContext = mContext;
        this.mTvUser = mTvUser;
        mListener = listener;
    }

    @Override
    public ListUserChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Firebase.setAndroidContext(mContext);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_user_chat_recycleview, parent, false);

        return new ListUserChatAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.mTvUserChat.setText(mTvUser.get(position).getPhoneNumber());
        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (mTvUser == null) ? 0 : mTvUser.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvUserChat;
        private LinearLayout mRelativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvUserChat = (TextView) itemView.findViewById(R.id.tvListUserChat);
            mRelativeLayout = (LinearLayout) itemView.findViewById(R.id.llItemChat);
        }
    }

    public interface itemClick{
        void onItemClick(int position);
    }
}
