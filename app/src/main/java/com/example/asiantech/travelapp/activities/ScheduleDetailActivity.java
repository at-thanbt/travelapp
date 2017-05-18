package com.example.asiantech.travelapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.adapters.ScheduleDetailAdapter;
import com.example.asiantech.travelapp.activities.objects.DaySchedule;
import com.example.asiantech.travelapp.activities.utils.PermissionUtil;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 17/05/2017.
 */
public class ScheduleDetailActivity extends BaseActivity implements ScheduleDetailAdapter.OnScheduleDetailListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, LocationListener, GoogleApiClient.OnConnectionFailedListener, DirectionCallback {
    public static final String TITLE_KEY = "mTitle";
    public static final String DATE_TIME_KEY = "mDateTime";
    public static final String TOUR_SCHEDULE_DATE_ID = "mTourScheduleId";
    private static final int REQUEST_LOCATION_ID = 231;
    private static final int DEFAULT_LAT_LNG_BOUND_PADDING = 70;
    private static final int DEFAULT_DIRECTION_STROKE_WIDTH = 2;
    private static final int ADD_DAY_REQUEST_CODE = 144;
    private Toolbar mToolbar;
    private TextView mTvDateTime;
    private TextView mTvTitle;
    private RecyclerView mRecyclerView;
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private GoogleApiClient mGoogleApiClient;

    private String mTitle;
    private String mDateTime;
    private String mTourScheduleId;
    private LatLng mCurrentPosition;
    private LatLng mSchedulePosition;
    private Marker mScheduleMarker;
    private Marker mUserMarker;
    private DaySchedule mCurrentSchedule;
    private Polyline mDirection;

    private List<DaySchedule> mDaySchedules = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_detail);
        mTitle = getIntent().getStringExtra(TITLE_KEY);
        mDateTime = getIntent().getStringExtra(DATE_TIME_KEY);
        mTourScheduleId = getIntent().getStringExtra(TOUR_SCHEDULE_DATE_ID);

        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        mTvDateTime = (TextView) findViewById(R.id.tvDateTime);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mMapView = (MapView) findViewById(R.id.mapView);
        mTvTitle = (TextView) findViewById(R.id.tvTitle);

        initToolbar();
        initTitle();
        initRecyclerView();
        initMap(savedInstanceState);
        getDataFromServer();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setTitle(mTitle);
    }

    private void initTitle() {
        mTvDateTime.setText(mDateTime);
        mTvTitle.setText(mTitle);
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new ScheduleDetailAdapter(this, mDaySchedules, mDateTime, this));
    }

    private void initMap(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
        buildGoogleApiClient();
        mMapView.getMapAsync(this);
    }

    private void getDataFromServer() {
        Firebase firebase = new Firebase(String.format("%sDaySchedules/%s", getString(R.string.URL_BASE), mTourScheduleId));
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDaySchedules.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    mDaySchedules.add(data.getValue(DaySchedule.class));
                }
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_schedule_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemShare:
                // TODO: 18/05/2017 Handle when click share
                break;
            case R.id.itemAddDay:
                Intent intent = new Intent(this, AddDayScheduleActivity.class);
                intent.putExtra(AddDayScheduleActivity.ID_TOUR_SCHEDULE, mTourScheduleId);
                startActivityForResult(intent, ADD_DAY_REQUEST_CODE);
            case R.id.itemEditPlan:
                // TODO: 18/05/2017 Handle when click edit plan
                break;
            case R.id.itemMovePlan:
                // TODO: 18/05/2017 Handle when click move plan
                break;
            case R.id.itemDeletePlan:
                // TODO: 18/05/2017 Handle when click delete plan
                break;
            case android.R.id.home:
                finish();
        }
        return false;
    }

    @Override
    public void onDetailClick() {
        // TODO: 18/05/2017 Handle when click detail
    }

    @Override
    public void onDirectionClick() {
        findDirection();
    }

    @Override
    public void onTimeDetailClick(int position) {
        mCurrentSchedule = mDaySchedules.get(position);
        loadSchedulePosition();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        setMapStyle();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    private void setMapStyle() {
        mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            PermissionUtil.requestPermissions(this, REQUEST_LOCATION_ID, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_ID) {
            boolean status = true;
            if (permissions.length == grantResults.length) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        status = false;
                        break;
                    }
                }
                if (status) {
                    setMapStyle();
                    getCurrentLocation();
                }
            }
        }
    }

    private void getCurrentLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
    }

    public void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        // No-op
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            if (isLocationChange(location)) {
                mCurrentPosition = new LatLng(location.getLatitude(), location.getLongitude());
                if (mSchedulePosition != null) {
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds.Builder().include(mCurrentPosition).include(mSchedulePosition).build(), DEFAULT_LAT_LNG_BOUND_PADDING));
                } else {
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mCurrentPosition, 15));
                }
                if (mUserMarker != null) {
                    mUserMarker.remove();
                }
                mUserMarker = mGoogleMap.addMarker(new MarkerOptions()
                        .position(mCurrentPosition)
                        .title(getString(R.string.current_position))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_user_position)));
                findDirection();
            }
        } else {
            showMessageDialog(getString(R.string.cant_get_location));
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // No-op
    }

    private void findDirection() {
        if (mCurrentSchedule == null) {
            return;
        }
        mSchedulePosition = new LatLng(mCurrentSchedule.getLat(), mCurrentSchedule.getLng());
        if (mSchedulePosition != mCurrentPosition) {
            GoogleDirection.withServerKey(getString(R.string.direction_key))
                    .from(mCurrentPosition)
                    .to(mSchedulePosition)
                    .transportMode(TransportMode.DRIVING)
                    .execute(this);
        }
    }

    private void loadSchedulePosition() {
        if (mCurrentSchedule != null) {
            mSchedulePosition = new LatLng(mCurrentSchedule.getLat(), mCurrentSchedule.getLng());
            if (mScheduleMarker != null) {
                mScheduleMarker.remove();
            }
            mScheduleMarker = mGoogleMap.addMarker(new MarkerOptions()
                    .position(mSchedulePosition)
                    .title(mCurrentSchedule.getTitle())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_24dp)));
            if (mCurrentPosition != null) {
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds.Builder().include(mCurrentPosition).include(mSchedulePosition).build(), DEFAULT_LAT_LNG_BOUND_PADDING));
            } else {
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mSchedulePosition, 15));
            }
        }
    }

    private boolean isLocationChange(Location location) {
        return mCurrentPosition == null || mCurrentPosition.latitude != location.getLatitude() || mCurrentPosition.longitude != location.getLongitude();
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        getDirection(direction);
    }

    @Override
    public void onDirectionFailure(Throwable t) {
        // No-op
    }

    private void getDirection(Direction direction) {
        if (mDirection != null) {
            mDirection.remove();
        }
        if (direction.getRouteList().size() > 0) {
            ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
            mDirection = mGoogleMap.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, DEFAULT_DIRECTION_STROKE_WIDTH, Color.RED));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_DAY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                getDataFromServer();
            }
        }
    }
}
