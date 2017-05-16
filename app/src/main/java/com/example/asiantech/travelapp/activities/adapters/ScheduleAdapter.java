package com.example.asiantech.travelapp.activities.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.objects.Schedule;

import java.util.List;

/**
 * Created by phuong on 16/05/2017.
 */

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyVietHolder> {
    private List<Schedule> mSchedules;
    private Context mContext;

    public ScheduleAdapter(List<Schedule> mSchedules, Context mContext) {
        this.mSchedules = mSchedules;
        this.mContext = mContext;
    }

    @Override
    public MyVietHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new MyVietHolder(view);
    }

    @Override
    public void onBindViewHolder(MyVietHolder holder, int position) {
        Schedule schedule = mSchedules.get(position);
        holder.mTvNote.setText(schedule.getNote());
        holder.mTvTime.setText(schedule.getTime());
        holder.mTvLocation.setText(schedule.getLocation());
    }

    @Override
    public int getItemCount() {
        return mSchedules == null ? 0 : mSchedules.size();
    }

    class MyVietHolder extends RecyclerView.ViewHolder {

        private TextView mTvTime;
        private TextView mTvLocation;
        private TextView mTvNote;

        public MyVietHolder(View itemView) {
            super(itemView);
            mTvTime = (TextView) itemView.findViewById(R.id.idDate);
            mTvLocation = (TextView) itemView.findViewById(R.id.idLocation);
            mTvNote = (TextView) itemView.findViewById(R.id.idContent);
        }
    }

}
