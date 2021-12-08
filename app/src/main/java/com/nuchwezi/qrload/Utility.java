package com.nuchwezi.qrload;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

class Utility {
    public static int getVersionNumber(Context context) {
        PackageInfo pinfo = null;
        try {
            pinfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pinfo != null ? pinfo.versionCode : 1;
    }

    public static String getVersionName(Context context) {
        PackageInfo pinfo = null;
        try {
            pinfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pinfo != null ? pinfo.versionName : "DEFAULT";
    }
}
