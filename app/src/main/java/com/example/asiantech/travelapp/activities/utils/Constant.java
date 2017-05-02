package com.example.asiantech.travelapp.activities.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by phuong on 25/03/2017.
 */

public class Constant {
    public static final String NAME_USER_LOGIN = "username";
    public static final String IS_USER_LOGIN = "idUser";
    public static final String DATA_USER_LOGIN = "dataUserLogin";
    public static final boolean TURN_ON_GPS = true;
    private static double mLongitude;
    private static double mLatitude;
    public static String unAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    public static String getLocationAddress(Context context) {
        TrackGPS gps = new TrackGPS(context);
        if (gps.canGetLocation()) {
            mLongitude = gps.getLongitude();
            mLatitude = gps.getLatitude();
        } else {
            gps.showSettingsAlert();
        }

        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(mLatitude, mLongitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String city = "";
        if (addresses != null) {
            city = addresses.get(0).getAddressLine(3);
        }
        return city;
    }

    public static boolean isNetWork(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            return true;
        }
        networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
