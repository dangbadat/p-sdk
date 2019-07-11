package com.lib.jsdk.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;

import java.util.List;

import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;

public class MethodUtils {
    public static boolean isAppInstall(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        List<ApplicationInfo> applicationInfos = manager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo app : applicationInfos) {
            if (manager.getLaunchIntentForPackage(app.packageName) != null) {
                if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0 || (app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {

                } else {
                    if (app.packageName.equals(packageName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void intentGPlay(Context context, String packageName) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
        }
    }

    public static boolean foregrounded() {
        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(appProcessInfo);
        return (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE);
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
