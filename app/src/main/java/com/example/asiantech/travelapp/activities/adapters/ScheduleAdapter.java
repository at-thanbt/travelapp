package com.example.asiantech.travelapp.activities.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.objects.TourSchedule;

import java.util.List;

/**
 * Created by phuong on 16/05/2017.
 */

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ItemTourScheduleVH> {
    private final List<TourSchedule> mTourSchedules;
    private final OnTourScheduleListener mOnTourScheduleListener;

    public ScheduleAdapter(List<TourSchedule> mTourSchedules, OnTourScheduleListener mOnTourScheduleListener) {
        this.mTourSchedules = mTourSchedules;
        this.mOnTourScheduleListener = mOnTourScheduleListener;
    }

    @Override
    public ItemTourScheduleVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new ItemTourScheduleVH(view, mOnTourScheduleListener);
    }

    @Override
    public void onBindViewHolder(ItemTourScheduleVH holder, int position) {
        TourSchedule tourSchedule = mTourSchedules.get(position);
        holder.mTvDescription.setText(tourSchedule.getDescription());
        holder.mTvTime.setText(tourSchedule.getDate());
    }

    @Override
    public int getItemCount() {
        return mTourSchedules == null ? 0 : mTourSchedules.size();
    }

    /**
     * OnTourScheduleListener
     */
    public interface OnTourScheduleListener {
        void onTourScheduleClick(int position);
    }

    static class ItemTourScheduleVH extends RecyclerView.ViewHolder {
        private TextView mTvTime;
        private TextView mTvDescription;

        ItemTourScheduleVH(View itemView, final OnTourScheduleListener onTourScheduleListener) {
            super(itemView);
            mTvTime = (TextView) itemView.findViewById(R.id.tvDate);
            mTvDescription = (TextView) itemView.findViewById(R.id.tvDescription);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onTourScheduleListener != null) {
                        onTourScheduleListener.onTourScheduleClick(getLayoutPosition());
                    }
                }
            });
        }
    }
}
