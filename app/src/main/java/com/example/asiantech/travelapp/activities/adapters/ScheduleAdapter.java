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
    private List<TourSchedule> mTourSchedules;

    public ScheduleAdapter(List<TourSchedule> mTourSchedules) {
        this.mTourSchedules = mTourSchedules;
    }

    @Override
    public ItemTourScheduleVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new ItemTourScheduleVH(view);
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

    class ItemTourScheduleVH extends RecyclerView.ViewHolder {
        private TextView mTvTime;
        private TextView mTvDescription;

        ItemTourScheduleVH(View itemView) {
            super(itemView);
            mTvTime = (TextView) itemView.findViewById(R.id.tvDate);
            mTvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
        }
    }

}
