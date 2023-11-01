package com.android.systemui.assist;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import com.android.systemui.BootCompleteCache;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.PackageManagerWrapper;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Optional;

/* loaded from: mainsysui33.jar:com/android/systemui/assist/PhoneStateMonitor.class */
public final class PhoneStateMonitor {
    public static final String[] DEFAULT_HOME_CHANGE_ACTIONS = {"android.intent.action.ACTION_PREFERRED_ACTIVITY_CHANGED", "android.intent.action.PACKAGE_ADDED", "android.intent.action.PACKAGE_CHANGED", "android.intent.action.PACKAGE_REMOVED"};
    public final Lazy<Optional<CentralSurfaces>> mCentralSurfacesOptionalLazy;
    public final Context mContext;
    public ComponentName mDefaultHome = getCurrentDefaultHome();
    public boolean mLauncherShowing;
    public final StatusBarStateController mStatusBarStateController;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.assist.PhoneStateMonitor$$ExternalSyntheticLambda0.onBootComplete():void] */
    public static /* synthetic */ void $r8$lambda$GUABkCGu7fz0odzjodKjcl6hYw8(PhoneStateMonitor phoneStateMonitor) {
        phoneStateMonitor.lambda$new$0();
    }

    /* renamed from: -$$Nest$smgetCurrentDefaultHome */
    public static /* bridge */ /* synthetic */ ComponentName m1477$$Nest$smgetCurrentDefaultHome() {
        return getCurrentDefaultHome();
    }

    public PhoneStateMonitor(Context context, BroadcastDispatcher broadcastDispatcher, Lazy<Optional<CentralSurfaces>> lazy, BootCompleteCache bootCompleteCache, StatusBarStateController statusBarStateController) {
        this.mContext = context;
        this.mCentralSurfacesOptionalLazy = lazy;
        this.mStatusBarStateController = statusBarStateController;
        bootCompleteCache.addListener(new BootCompleteCache.BootCompleteListener() { // from class: com.android.systemui.assist.PhoneStateMonitor$$ExternalSyntheticLambda0
            @Override // com.android.systemui.BootCompleteCache.BootCompleteListener
            public final void onBootComplete() {
                PhoneStateMonitor.$r8$lambda$GUABkCGu7fz0odzjodKjcl6hYw8(PhoneStateMonitor.this);
            }
        });
        IntentFilter intentFilter = new IntentFilter();
        for (String str : DEFAULT_HOME_CHANGE_ACTIONS) {
            intentFilter.addAction(str);
        }
        broadcastDispatcher.registerReceiver(new BroadcastReceiver() { // from class: com.android.systemui.assist.PhoneStateMonitor.1
            {
                PhoneStateMonitor.this = this;
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                PhoneStateMonitor.this.mDefaultHome = PhoneStateMonitor.m1477$$Nest$smgetCurrentDefaultHome();
            }
        }, intentFilter);
        this.mLauncherShowing = isLauncherShowing(ActivityManagerWrapper.getInstance().getRunningTask());
        TaskStackChangeListeners.getInstance().registerTaskStackListener(new TaskStackChangeListener() { // from class: com.android.systemui.assist.PhoneStateMonitor.2
            {
                PhoneStateMonitor.this = this;
            }

            public void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
                PhoneStateMonitor phoneStateMonitor = PhoneStateMonitor.this;
                phoneStateMonitor.mLauncherShowing = phoneStateMonitor.isLauncherShowing(runningTaskInfo);
            }
        });
    }

    public static ComponentName getCurrentDefaultHome() {
        ArrayList<ResolveInfo> arrayList = new ArrayList();
        ComponentName homeActivities = PackageManagerWrapper.getInstance().getHomeActivities(arrayList);
        if (homeActivities != null) {
            return homeActivities;
        }
        int i = Integer.MIN_VALUE;
        while (true) {
            ComponentName componentName = null;
            for (ResolveInfo resolveInfo : arrayList) {
                int i2 = resolveInfo.priority;
                if (i2 > i) {
                    componentName = resolveInfo.activityInfo.getComponentName();
                    i = resolveInfo.priority;
                } else if (i2 == i) {
                    break;
                }
            }
            return componentName;
        }
    }

    public /* synthetic */ void lambda$new$0() {
        this.mDefaultHome = getCurrentDefaultHome();
    }

    public final int getPhoneLauncherState() {
        if (isLauncherInOverview()) {
            return 6;
        }
        return isLauncherInAllApps() ? 7 : 5;
    }

    public final int getPhoneLockscreenState() {
        if (isDozing()) {
            return 1;
        }
        if (isBouncerShowing()) {
            return 3;
        }
        return isKeyguardLocked() ? 2 : 4;
    }

    public int getPhoneState() {
        return isShadeFullscreen() ? getPhoneLockscreenState() : this.mLauncherShowing ? getPhoneLauncherState() : 9;
    }

    public final boolean isBouncerShowing() {
        return ((Boolean) ((Optional) this.mCentralSurfacesOptionalLazy.get()).map(new PhoneStateMonitor$$ExternalSyntheticLambda1()).orElse(Boolean.FALSE)).booleanValue();
    }

    public final boolean isDozing() {
        return this.mStatusBarStateController.isDozing();
    }

    public final boolean isKeyguardLocked() {
        KeyguardManager keyguardManager = (KeyguardManager) this.mContext.getSystemService(KeyguardManager.class);
        return keyguardManager != null && keyguardManager.isKeyguardLocked();
    }

    public final boolean isLauncherInAllApps() {
        return false;
    }

    public final boolean isLauncherInOverview() {
        return false;
    }

    public final boolean isLauncherShowing(ActivityManager.RunningTaskInfo runningTaskInfo) {
        ComponentName componentName;
        if (runningTaskInfo == null || (componentName = runningTaskInfo.topActivity) == null) {
            return false;
        }
        return componentName.equals(this.mDefaultHome);
    }

    public final boolean isShadeFullscreen() {
        int state = this.mStatusBarStateController.getState();
        boolean z = true;
        if (state != 1) {
            z = state == 2;
        }
        return z;
    }
}