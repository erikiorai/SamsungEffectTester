package com.android.systemui;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/ActivityIntentHelper.class */
public class ActivityIntentHelper {
    public final PackageManager mPm;

    public ActivityIntentHelper(Context context) {
        this.mPm = context.getPackageManager();
    }

    public ActivityInfo getPendingTargetActivityInfo(PendingIntent pendingIntent, int i, boolean z) {
        int i2 = !z ? 852096 : 65664;
        List queryIntentComponents = pendingIntent.queryIntentComponents(i2);
        if (queryIntentComponents.size() == 0) {
            return null;
        }
        if (queryIntentComponents.size() == 1) {
            return queryIntentComponents.get(0).activityInfo;
        }
        ResolveInfo resolveActivityAsUser = this.mPm.resolveActivityAsUser(pendingIntent.getIntent(), i2, i);
        if (resolveActivityAsUser == null || wouldLaunchResolverActivity(resolveActivityAsUser, queryIntentComponents)) {
            return null;
        }
        return resolveActivityAsUser.activityInfo;
    }

    public ActivityInfo getTargetActivityInfo(Intent intent, int i, boolean z) {
        int i2 = !z ? 852096 : 65664;
        List queryIntentActivitiesAsUser = this.mPm.queryIntentActivitiesAsUser(intent, i2, i);
        if (queryIntentActivitiesAsUser.size() == 0) {
            return null;
        }
        if (queryIntentActivitiesAsUser.size() == 1) {
            return queryIntentActivitiesAsUser.get(0).activityInfo;
        }
        ResolveInfo resolveActivityAsUser = this.mPm.resolveActivityAsUser(intent, i2, i);
        if (resolveActivityAsUser == null || wouldLaunchResolverActivity(resolveActivityAsUser, queryIntentActivitiesAsUser)) {
            return null;
        }
        return resolveActivityAsUser.activityInfo;
    }

    public boolean wouldLaunchResolverActivity(Intent intent, int i) {
        boolean z = false;
        if (getTargetActivityInfo(intent, i, false) == null) {
            z = true;
        }
        return z;
    }

    public boolean wouldLaunchResolverActivity(ResolveInfo resolveInfo, List<ResolveInfo> list) {
        for (int i = 0; i < list.size(); i++) {
            ResolveInfo resolveInfo2 = list.get(i);
            if (resolveInfo2.activityInfo.name.equals(resolveInfo.activityInfo.name) && resolveInfo2.activityInfo.packageName.equals(resolveInfo.activityInfo.packageName)) {
                return false;
            }
        }
        return true;
    }

    public boolean wouldPendingLaunchResolverActivity(PendingIntent pendingIntent, int i) {
        boolean z = false;
        if (getPendingTargetActivityInfo(pendingIntent, i, false) == null) {
            z = true;
        }
        return z;
    }

    public boolean wouldPendingShowOverLockscreen(PendingIntent pendingIntent, int i) {
        ActivityInfo pendingTargetActivityInfo = getPendingTargetActivityInfo(pendingIntent, i, false);
        boolean z = false;
        if (pendingTargetActivityInfo != null) {
            z = false;
            if ((pendingTargetActivityInfo.flags & 8389632) > 0) {
                z = true;
            }
        }
        return z;
    }

    public boolean wouldShowOverLockscreen(Intent intent, int i) {
        ActivityInfo targetActivityInfo = getTargetActivityInfo(intent, i, false);
        boolean z = false;
        if (targetActivityInfo != null) {
            z = false;
            if ((targetActivityInfo.flags & 8389632) > 0) {
                z = true;
            }
        }
        return z;
    }
}