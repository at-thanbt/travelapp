package com.example.asiantech.travelapp.activities.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.AddTourristActivity;
import com.example.asiantech.travelapp.activities.objects.User;
import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by phuong on 08/04/2017.
 */

public class ListTouristAdapter extends RecyclerView.Adapter<ListTouristAdapter.MyVietHolder> {
    private List<User> mTvTouristes;
    private Context mContext;
    private ImageButton ibtnDeleteTourist;
    private Firebase myFirebaseRef;

    public ListTouristAdapter(Context mContext, List<User> mTvTouristes) {
        this.mContext = mContext;
        this.mTvTouristes = mTvTouristes;
    }

    @Override
    public MyVietHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Firebase.setAndroidContext(mContext);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_tourist_recycleview, parent, false);
        ibtnDeleteTourist = (ImageButton) view.findViewById(R.id.ibtnDeleteTourist);

        return new MyVietHolder(view);
    }

    @Override
    public void onBindViewHolder(MyVietHolder holder, final int position) {
        holder.mTvTourist.setText(mTvTouristes.get(position).getPhoneNumber());
        ibtnDeleteTourist.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                AlertDialog.Builder b = new AlertDialog.Builder(mContext);

                b.setTitle("Question");
                b.setMessage("Do you want to delete this tourist");
                b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myFirebaseRef = new Firebase("https://travelapp-4961a.firebaseio.com/user");
                        Log.d("tag1234", "abc " + mTvTouristes.get(position).getId());
                        myFirebaseRef.child(mTvTouristes.get(position).getId()).removeValue();
                        mTvTouristes.remove(position);
                        notifyDataSetChanged();

                    }
                });
                b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override

                    public void onClick(DialogInterface dialog, int which)

                    {
                        dialog.cancel();

                    }

                });
                b.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mTvTouristes == null) ? 0 : mTvTouristes.size();
    }

    class MyVietHolder extends RecyclerView.ViewHolder {

        private TextView mTvTourist;
        private RelativeLayout mRelativeLayout;

        public MyVietHolder(View itemView) {
            super(itemView);
            mTvTourist = (TextView) itemView.findViewById(R.id.tvTourist);
            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.rlTouristItem);
        }
    }

}
