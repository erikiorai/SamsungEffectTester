package com.android.systemui.controls.management;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import android.os.UserHandle;
import android.service.controls.Control;
import android.util.Log;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlsRequestReceiver.class */
public final class ControlsRequestReceiver extends BroadcastReceiver {
    public static final Companion Companion = new Companion(null);

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlsRequestReceiver$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final boolean isPackageInForeground(Context context, String str) {
            try {
                int packageUid = context.getPackageManager().getPackageUid(str, 0);
                ActivityManager activityManager = (ActivityManager) context.getSystemService(ActivityManager.class);
                if ((activityManager != null ? activityManager.getUidImportance(packageUid) : 1000) != 100) {
                    Log.w("ControlsRequestReceiver", "Uid " + packageUid + " not in foreground");
                    return false;
                }
                return true;
            } catch (PackageManager.NameNotFoundException e) {
                Log.w("ControlsRequestReceiver", "Package " + str + " not found");
                return false;
            }
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (context.getPackageManager().hasSystemFeature("android.software.controls")) {
            try {
                ComponentName componentName = (ComponentName) intent.getParcelableExtra("android.intent.extra.COMPONENT_NAME");
                try {
                    Parcelable parcelable = (Control) intent.getParcelableExtra("android.service.controls.extra.CONTROL");
                    String packageName = componentName != null ? componentName.getPackageName() : null;
                    if (packageName == null || !Companion.isPackageInForeground(context, packageName)) {
                        return;
                    }
                    Intent intent2 = new Intent(context, ControlsRequestDialog.class);
                    intent2.putExtra("android.intent.extra.COMPONENT_NAME", componentName);
                    intent2.putExtra("android.service.controls.extra.CONTROL", parcelable);
                    intent2.addFlags(268566528);
                    intent2.putExtra("android.intent.extra.USER_ID", context.getUserId());
                    context.startActivityAsUser(intent2, UserHandle.SYSTEM);
                } catch (ClassCastException e) {
                    Log.e("ControlsRequestReceiver", "Malformed intent extra Control", e);
                }
            } catch (ClassCastException e2) {
                Log.e("ControlsRequestReceiver", "Malformed intent extra ComponentName", e2);
            }
        }
    }
}