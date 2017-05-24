package com.example.asiantech.travelapp.activities.adapters;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.objects.DaySchedule;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import static com.example.asiantech.travelapp.activities.adapters.ScheduleDetailAdapter.ScheduleDetailItemType.BUTTON;
import static com.example.asiantech.travelapp.activities.adapters.ScheduleDetailAdapter.ScheduleDetailItemType.DATE_TIME_TITLE;
import static com.example.asiantech.travelapp.activities.adapters.ScheduleDetailAdapter.ScheduleDetailItemType.TIME_DETAIL;

/**
 * Created by phuong on 18/05/2017.
 */
public class ScheduleDetailAdapter extends BaseAdapter {
    private static final int TITLE_DAY_TIME_OFFSET = 0;
    private static final int DETAIL_TIME_OFFSET = 1;
    private final List<DaySchedule> mDaySchedules;
    private final String mTitleDateTime;
    private final OnScheduleDetailListener mOnScheduleDetailListener;

    public ScheduleDetailAdapter(@NonNull Context context, List<DaySchedule> daySchedules, String titleDateTime, OnScheduleDetailListener onScheduleDetailListener) {
        super(context);
        this.mDaySchedules = daySchedules;
        this.mTitleDateTime = titleDateTime;
        this.mOnScheduleDetailListener = onScheduleDetailListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == TITLE_DAY_TIME_OFFSET) {
            return ScheduleDetailItemType.DATE_TIME_TITLE;
        } else if (position == mDaySchedules.size() + DETAIL_TIME_OFFSET) {
            return ScheduleDetailItemType.BUTTON;
        } else {
            return ScheduleDetailItemType.TIME_DETAIL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ScheduleDetailItemType.DATE_TIME_TITLE:
                return new ItemDateTimeTitleVH(getViewFromResource(R.layout.item_recycler_schedule_detail_title, parent));
            case ScheduleDetailItemType.BUTTON:
                return new ItemButtonVH(getViewFromResource(R.layout.item_recycler_schedule_detail_button, parent), mOnScheduleDetailListener);
            case ScheduleDetailItemType.TIME_DETAIL:
                return new ItemDetailTimeVH(getViewFromResource(R.layout.item_recycler_schedule_detail_time, parent), mOnScheduleDetailListener);
        }
        return null;
    }

    private View getViewFromResource(int resId, ViewGroup parent) {
        return LayoutInflater.from(getContext()).inflate(resId, parent, false);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemDateTimeTitleVH) {
            onBindItemDateTimeTitle(((ItemDateTimeTitleVH) holder));
        } else if (holder instanceof ItemDetailTimeVH) {
            onBindItemDetailTime(((ItemDetailTimeVH) holder), position - DETAIL_TIME_OFFSET);
        }
    }

    private void onBindItemDetailTime(ItemDetailTimeVH holder, int position) {
        DaySchedule daySchedule = mDaySchedules.get(position);
        holder.mTvTime.setText(daySchedule.getTime());
        holder.mTvNote.setText(daySchedule.getNote());
        holder.mTvContent.setText(daySchedule.getContent());
        holder.mTvTimeDetailTitle.setText(daySchedule.getTitle());
    }

    private void onBindItemDateTimeTitle(ItemDateTimeTitleVH holder) {
        holder.mTvDateTime.setText(mTitleDateTime);
    }

    @Override
    public int getItemCount() {
        return mDaySchedules.size() + 2;
    }

    /**
     * ScheduleDetailItemType definition
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({DATE_TIME_TITLE, TIME_DETAIL, BUTTON})
    @interface ScheduleDetailItemType {
        int DATE_TIME_TITLE = 1;
        int TIME_DETAIL = 2;
        int BUTTON = 3;
    }

    /**
     * OnScheduleDetailListener interface
     */
    public interface OnScheduleDetailListener {
        void onDetailClick();

        void onDirectionClick();

        void onTimeDetailClick(int position);
    }

    /**
     * ItemDateTimeTitleVH class
     */
    private static class ItemDateTimeTitleVH extends RecyclerView.ViewHolder {
        private final TextView mTvDateTime;

        ItemDateTimeTitleVH(View itemView) {
            super(itemView);
            mTvDateTime = (TextView) itemView.findViewById(R.id.tvDateTime);
        }
    }

    /**
     * ItemDetailTimeVH class
     */
    private static class ItemDetailTimeVH extends RecyclerView.ViewHolder {
        private final TextView mTvTime;
        private final TextView mTvTimeDetailTitle;
        private final TextView mTvContent;
        private final TextView mTvNote;

        ItemDetailTimeVH(View itemView, final OnScheduleDetailListener onScheduleDetailListener) {
            super(itemView);
            mTvTime = (TextView) itemView.findViewById(R.id.tvTime);
            mTvTimeDetailTitle = (TextView) itemView.findViewById(R.id.tvTimeDetailTitle);
            mTvContent = (TextView) itemView.findViewById(R.id.tvContent);
            mTvNote = (TextView) itemView.findViewById(R.id.tvNote);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onScheduleDetailListener != null) {
                        onScheduleDetailListener.onTimeDetailClick(getLayoutPosition() - DETAIL_TIME_OFFSET);
                    }
                }
            });
        }
    }

    /**
     * ItemButtonVH class
     */
    private static class ItemButtonVH extends RecyclerView.ViewHolder {
        private final TextView mBtnDetail;
        private final TextView mBtnDirection;

        ItemButtonVH(View itemView, final OnScheduleDetailListener onScheduleDetailListener) {
            super(itemView);
            mBtnDetail = (TextView) itemView.findViewById(R.id.btnDetail);
            mBtnDirection = (TextView) itemView.findViewById(R.id.btnDirection);

            mBtnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onScheduleDetailListener != null) {
                        onScheduleDetailListener.onDetailClick();
                    }
                }
            });

            mBtnDirection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onScheduleDetailListener != null) {
                        onScheduleDetailListener.onDirectionClick();
                    }
                }
            });
        }
    }
}
