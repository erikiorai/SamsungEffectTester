package com.android.systemui.media.controls.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/util/MediaDataUtils.class */
public class MediaDataUtils {
    public static String getAppLabel(Context context, String str, String str2) {
        ApplicationInfo applicationInfo;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        PackageManager packageManager = context.getPackageManager();
        try {
            applicationInfo = packageManager.getApplicationInfo(str, 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        if (applicationInfo != null) {
            str2 = packageManager.getApplicationLabel(applicationInfo);
        }
        return str2;
    }
}