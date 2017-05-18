package com.example.asiantech.travelapp.activities.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.objects.Message;

import java.util.List;

/**
 * Created by phuong on 18/05/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyVieWHolder> {
    private List<Message> mMessages;
    private Context mContext;

    public MessageAdapter(List<Message> mMessages, Context mContext) {
        this.mMessages = mMessages;
        this.mContext = mContext;
    }

    @Override
    public MyVieWHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_tourist_recycleview, parent, false);
        return new MyVieWHolder(view);
    }

    @Override
    public void onBindViewHolder(MyVieWHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mMessages == null ? 0 : mMessages.size();
    }

    class MyVieWHolder extends RecyclerView.ViewHolder {

        private TextView mTvUser;
        private ImageView mImgAvarta;
        private ImageButton mImgDelete;

        public MyVieWHolder(View itemView) {
            super(itemView);
            mTvUser = (TextView) itemView.findViewById(R.id.tvTourist);
            mImgAvarta = (ImageView) itemView.findViewById(R.id.avarta);
            mImgDelete = (ImageButton) itemView.findViewById(R.id.ibtnDeleteTourist);
        }
    }
}
