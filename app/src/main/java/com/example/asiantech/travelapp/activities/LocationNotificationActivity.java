package com.example.asiantech.travelapp.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.objects.LocationNotification;
import com.example.asiantech.travelapp.activities.utils.PermissionUtil;
import com.firebase.client.Firebase;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashSet;
import java.util.Set;

public class LocationNotificationActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, LocationListener, GoogleMap.OnMarkerDragListener, GoogleApiClient.OnConnectionFailedListener {
    public static final String TOUR_ID = "tour-id";
    public static final String LOCATION_NOTIFICATION = "location-notification";

    private static final String NOTIFICATIONS_SET = "notification-set";
    private static final int REQUEST_LOCATION_ID = 231;
    private MapView mapView;
    private GoogleMap googleMap;
    private GoogleApiClient googleApiClient;
    private LatLng currentLocation;
    private String tourId;
    private LocationNotification locationNotification;
    private Firebase notificationRef;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_notification);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        setSupportActionBar((Toolbar) findViewById(R.id.toolBar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tourId = getIntent().getStringExtra(TOUR_ID);
        locationNotification = (LocationNotification) getIntent().getSerializableExtra(LOCATION_NOTIFICATION);

        notificationRef = new Firebase(getString(R.string.URL_BASE) + "/notifications");

        if (locationNotification == null) {
            setTitle("Viêt thông điệp");
        } else {
            setTitle("Thông điệp của " + locationNotification.getWho());

            sharedPreferences = getSharedPreferences("read-notifications", MODE_PRIVATE);
            Set<String> notificationSet = sharedPreferences.getStringSet(NOTIFICATIONS_SET, new HashSet<String>());
            notificationSet.add(locationNotification.getId());
            sharedPreferences.edit().putStringSet(NOTIFICATIONS_SET, notificationSet).apply();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (locationNotification == null) {
            getMenuInflater().inflate(R.menu.notify_location, menu);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_send && currentLocation != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Nhập nội dung thông điệp");
            final EditText editText = new EditText(this);
            builder.setView(editText);
            builder.setPositiveButton("Gửi", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String content = editText.getText().toString();
                    sendMessage(content, currentLocation);
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
        return true;
    }

    private void sendMessage(String content, LatLng currentLocation) {
        String who = App.getInstance().getNameTourguide();
        LocationNotification locationNotification = new LocationNotification();
        locationNotification.setContent(content);
        locationNotification.setLatitude(currentLocation.latitude);
        locationNotification.setLongitude(currentLocation.longitude);
        locationNotification.setWho(who);
        Firebase tourNotificationRef = notificationRef.child(tourId).push();
        locationNotification.setId(tourNotificationRef.getKey());
        tourNotificationRef.setValue(locationNotification);
        Toast.makeText(this, "Đã gửi thông điệp", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            PermissionUtil.requestPermissions(this, REQUEST_LOCATION_ID, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
            return;
        }

        if (locationNotification == null) {
            googleMap.setMyLocationEnabled(true);
            buildGoogleApiClient();
            googleMap.setOnMarkerDragListener(this);
        } else {
            LatLng currentPosition = new LatLng(locationNotification.getLatitude(), locationNotification.getLongitude());
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15));
            googleMap.addMarker(new MarkerOptions()
                    .position(currentPosition)
                    .title(locationNotification.getWho())
                    .snippet(locationNotification.getContent())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_user_position)));
        }
    }

    private void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null && currentLocation == null) {
            LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15));
            googleMap.addMarker(new MarkerOptions()
                    .position(currentPosition)
                    .title(getString(R.string.current_position))
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_user_position)));
            currentLocation = currentPosition;
        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        currentLocation = marker.getPosition();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}