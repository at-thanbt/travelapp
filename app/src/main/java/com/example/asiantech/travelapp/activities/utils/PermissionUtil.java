package com.example.asiantech.travelapp.activities.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 16/01/2017.
 */
public final class PermissionUtil {

    private PermissionUtil() {
    }

    public static boolean isPermissionsGranted(Context context, String... permissions) {
        for (String permission : permissions) {
            if (!isPermissionGranted(context, permission)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPermissionGranted(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermissions(Object object, int permissionId, String... permissions) {
        if (object instanceof Fragment) {
            FragmentCompat.requestPermissions((Fragment) object, permissions, permissionId);
        } else if (object instanceof Activity) {
            ActivityCompat.requestPermissions((AppCompatActivity) object, permissions, permissionId);
        }
    }

    public static boolean isPermissionNotAskAgain(Activity activity, String permission) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !activity.shouldShowRequestPermissionRationale(permission);
    }

    public static void openSettingWhenNotAskPermission(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .addCategory(Intent.CATEGORY_DEFAULT)
                .setData(Uri.parse("package:" + activity.getPackageName()))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        activity.startActivity(intent);
    }
}
