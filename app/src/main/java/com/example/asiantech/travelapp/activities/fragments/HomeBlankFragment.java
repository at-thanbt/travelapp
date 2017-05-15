package com.example.asiantech.travelapp.activities.fragments;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.AddTourActivity;
import com.example.asiantech.travelapp.activities.adapters.TourAdapter;
import com.example.asiantech.travelapp.activities.objects.Tour;
import com.example.asiantech.travelapp.activities.utils.Constant;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by asiantech on 30/04/2017.
 */
@TargetApi(Build.VERSION_CODES.M)
public class HomeBlankFragment extends Fragment {
    Button mBtnAddTour;
    private List<Tour> mTours;

    private SharedPreferences mSharedPreferencesUserLogin;
    private String idUserLogin;

    private RelativeLayout mRlIntro;
    private RecyclerView mRecyclerViewTour;
    private TourAdapter mTourAdapter;
    private FloatingActionButton mBtnAdd;
    private ProgressBar mProgressBarLoading;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_fragment_before_add_tour, container, false);
        mBtnAddTour = (Button) view.findViewById(R.id.btnAddTour);
        mRlIntro = (RelativeLayout) view.findViewById(R.id.rlIntro);
        mRecyclerViewTour = (RecyclerView) view.findViewById(R.id.recyclerViewTour);
        mBtnAdd = (FloatingActionButton) view.findViewById(R.id.fab);
        mProgressBarLoading = (ProgressBar) view.findViewById(R.id.progressBarLoading);

        Firebase.setAndroidContext(getActivity());
        mSharedPreferencesUserLogin = getActivity().getSharedPreferences(Constant.DATA_USER_LOGIN, 0);
        idUserLogin = mSharedPreferencesUserLogin.getString(Constant.NAME_USER_LOGIN, "");

        mBtnAddTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getActivity(), AddTourActivity.class);
                startActivity(mIntent);
            }
        });
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getActivity(), AddTourActivity.class);
                startActivity(mIntent);
            }
        });

        return view;
    }

    public void accessView() {
        getData();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mTours.size() == 0) {
                    mRlIntro.setVisibility(View.VISIBLE);
                    mRecyclerViewTour.setVisibility(View.GONE);
                } else {
                    mRlIntro.setVisibility(View.GONE);
                    mRecyclerViewTour.setVisibility(View.VISIBLE);

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    mTourAdapter = new TourAdapter(getContext(), mTours);
                    mRecyclerViewTour.setLayoutManager(layoutManager);
                    mRecyclerViewTour.setAdapter(mTourAdapter);
                    mProgressBarLoading.setVisibility(View.GONE);
                }
            }
        }, 8000);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("tag1233", "123");
        accessView();
    }

    public void getData() {
        Firebase firebaseTour = new Firebase(getString(R.string.URL_BASE) + "/tours");
        mTours = new ArrayList<>();
        firebaseTour.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    HashMap<String, Object> map = (HashMap<String, Object>) data.getValue();
                    if (map.get("usernameTourGuide").toString().equals(idUserLogin)) {
                        Tour tour = new Tour();
                        tour.setUsernameTourGuide(map.get("usernameTourGuide").toString());
                        tour.setDescriptionn(map.get("descriptionn").toString());
                        tour.setDestination(map.get("destination").toString());
                        tour.setIdTour(map.get("idTour").toString());
                        tour.setTourName(map.get("tourName").toString());
                        tour.setStartDate(map.get("startDate").toString());
                        tour.setEndDate(map.get("endDate").toString());
                        mTours.add(tour);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
