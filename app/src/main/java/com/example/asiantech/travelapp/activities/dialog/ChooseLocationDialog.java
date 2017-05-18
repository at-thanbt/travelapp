package com.example.asiantech.travelapp.activities.dialog;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.example.asiantech.travelapp.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 18-May-17.
 */
public class ChooseLocationDialog extends DialogFragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @Setter
    @Accessors(prefix = "m")
    private OnChooseLocationListener mOnChooseLocationListener;

    private MapView mMapView;
    private boolean mIsAdded;
    private Marker mCurrentMarker;
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mGoogleMap;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.layout_choose_location_dialog, null);
        TextView btnCancel = (TextView) view.findViewById(R.id.btnCancel);
        TextView btnOK = (TextView) view.findViewById(R.id.btnOk);
        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnChooseLocationListener != null) {
                    mOnChooseLocationListener.onChooseLocation(mCurrentMarker.getPosition());
                }
                dismiss();
            }
        });
        initMap();
        builder.setView(view);
        return builder.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (mIsAdded) {
            return;
        }
        mIsAdded = true;
        super.show(manager, tag);
    }

    @Override
    public void dismiss() {
        if (!mIsAdded) {
            return;
        }
        super.dismiss();
    }

    private void initMap() {
        buildGoogleApiClient();
        mMapView.getMapAsync(this);
    }

    public void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(16.0474325, 108.1712201), 15));
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (mCurrentMarker != null) {
                    mCurrentMarker.remove();
                }
                mCurrentMarker = mGoogleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_user_position)));
            }
        });
        setMapStyle();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // No-op
    }

    @Override
    public void onConnectionSuspended(int i) {
        // No-op
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // No-op
    }

    private void setMapStyle() {
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
    }

    /**
     * OnChooseLocationListener
     */
    public interface OnChooseLocationListener {
        void onChooseLocation(LatLng latLng);
    }
}
