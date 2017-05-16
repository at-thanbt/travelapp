package com.example.asiantech.travelapp.activities.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.objects.Tour;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by phuong on 08/04/2017.
 */

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.MyVietHolder> {
    private List<Tour> mTours;
    private Context mContext;
    private SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
    private onItemClick mListener;


    public TourAdapter(Context mContext, List<Tour> tours, onItemClick listener) {
        this.mContext = mContext;
        this.mTours = tours;
        mListener = listener;
    }

    @Override
    public MyVietHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tour, parent, false);
        return new MyVietHolder(view);
    }

    @Override
    public void onBindViewHolder(MyVietHolder holder, int position) {
        final Tour tour = mTours.get(position);
        holder.mTvNameTour.setText(tour.getTourName());
        holder.mTvTime.setText(tour.getStartDate() + " - " + tour.getEndDate());

        try {
            Date date1 = myFormat.parse(tour.getStartDate());
            Date date2 = myFormat.parse(tour.getEndDate());
            long diff = date2.getTime() - date1.getTime();
            holder.mTvDuration.setText((TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1) + " ngày");
        } catch (ParseException e) {
            e.printStackTrace();
            holder.mTvDuration.setText("Đang cập nhật");
        }

        holder.mRlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.itemClick(tour.getIdTour());
            }
        });
    }



    @Override
    public int getItemCount() {
        return (mTours == null) ? 0 : mTours.size();
    }

    public interface onItemClick
    {
        void itemClick (String idTour);
    }

    class MyVietHolder extends RecyclerView.ViewHolder {

        private TextView mTvNameTour;
        private TextView mTvTime;
        private TextView mTvDuration;
        private LinearLayout mRlItem;

        public MyVietHolder(View itemView) {
            super(itemView);
            mTvNameTour = (TextView) itemView.findViewById(R.id.tvNameTour);
            mTvTime = (TextView) itemView.findViewById(R.id.tvTime);
            mTvDuration = (TextView) itemView.findViewById(R.id.tvDuration);
            mRlItem = (LinearLayout) itemView.findViewById(R.id.rlItem);
        }
    }

}
