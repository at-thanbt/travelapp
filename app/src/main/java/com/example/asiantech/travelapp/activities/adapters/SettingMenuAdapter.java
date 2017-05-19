package com.example.asiantech.travelapp.activities.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.objects.MenuItem;
import com.example.asiantech.travelapp.activities.utils.Constant;

import java.util.List;

public class SettingMenuAdapter extends RecyclerView.Adapter {
    private static final int MENU_HEADER = 0;
    private static final int MENU_ITEM_INFORMATION = 1;
    private static final int MENU_FOOTER = 2;
    protected final Context mContext;
    private final List mItems;
    private SharedPreferences mSharedPreferencesLogin;
    private OnMenuItemClickListener onMenuItemClickListener;

    public SettingMenuAdapter(List mItems, Context mContext, OnMenuItemClickListener onMenuItemClickListener) {
        this.mContext = mContext;
        this.mItems = mItems;
        mSharedPreferencesLogin = mContext.getSharedPreferences(Constant.DATA_USER_LOGIN, 0);
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case MENU_HEADER:
                View header = inflater.inflate(R.layout.menu_header, parent, false);
                viewHolder = new HeaderHolder(header);

                break;
            case MENU_ITEM_INFORMATION:
                View itemInfor = inflater.inflate(R.layout.menu_item, parent, false);
                viewHolder = new ItemHolder(itemInfor);
                break;
            case MENU_FOOTER:
                View footer = inflater.inflate(R.layout.menu_footer, parent, false);
                viewHolder = new FooterHolder(footer);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case MENU_HEADER:
                String username = mSharedPreferencesLogin.getString(Constant.NAME_USER_LOGIN,"");
                HeaderHolder mHeader = (HeaderHolder) holder;
                mHeader.mTvUsername.setText(username);
                break;
            case MENU_ITEM_INFORMATION:
                MenuItem menuItem = (MenuItem) mItems.get(position);
                final ItemHolder mItem = (ItemHolder) holder;
                mItem.item = menuItem;
                mItem.mTvMenuItemName.setText(menuItem.getTitle());
                mItem.mImgIcon.setImageResource(menuItem.getIcon());

                mItem.mRlItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onMenuItemClickListener != null)
                            onMenuItemClickListener.onMenuItemClick(mItem.item);
                    }
                });
                break;
        }

    }

    @Override
    public int getItemCount() {
        return (mItems == null) ? 0 : mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems.get(position) instanceof MenuItem) {
            return MENU_ITEM_INFORMATION;
        }
        if (mItems.get(position) instanceof String) {
            return MENU_HEADER;
        }
        if (mItems.get(position) instanceof Integer) {
            return MENU_FOOTER;
        }
        return -1;
    }

    public interface itemClick {

    }

    public class HeaderHolder extends RecyclerView.ViewHolder {
        TextView mTvUsername;

        public HeaderHolder(View itemView) {
            super(itemView);
            mTvUsername = (TextView) itemView.findViewById(R.id.tvname);
        }
    }

    public class FooterHolder extends RecyclerView.ViewHolder {
        TextView mTvNameApp;

        public FooterHolder(View itemView) {
            super(itemView);
            mTvNameApp = (TextView) itemView.findViewById(R.id.tvNameApp);
        }
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        TextView mTvMenuItemName;
        ImageView mImgIcon;
        RelativeLayout mRlItem;

        MenuItem item;

        public ItemHolder(View itemView) {
            super(itemView);
            mTvMenuItemName = (TextView) itemView.findViewById(R.id.tvMenuItemName);
            mImgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);
            mRlItem = (RelativeLayout) itemView.findViewById(R.id.rlMenuItem);
        }
    }

    public interface OnMenuItemClickListener {
        void onMenuItemClick(MenuItem item);
    }
}
