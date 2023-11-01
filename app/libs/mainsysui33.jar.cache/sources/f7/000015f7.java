package com.android.systemui.demomode;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import com.android.systemui.util.settings.GlobalSettings;

/* loaded from: mainsysui33.jar:com/android/systemui/demomode/DemoModeAvailabilityTracker.class */
public abstract class DemoModeAvailabilityTracker {
    public final DemoModeAvailabilityTracker$allowedObserver$1 allowedObserver;
    public final Context context;
    public final GlobalSettings globalSettings;
    public final DemoModeAvailabilityTracker$onObserver$1 onObserver;
    public boolean isInDemoMode = checkIsDemoModeOn();
    public boolean isDemoModeAvailable = checkIsDemoModeAllowed();

    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.demomode.DemoModeAvailabilityTracker$allowedObserver$1] */
    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.demomode.DemoModeAvailabilityTracker$onObserver$1] */
    public DemoModeAvailabilityTracker(Context context, GlobalSettings globalSettings) {
        this.context = context;
        this.globalSettings = globalSettings;
        final Handler handler = new Handler(Looper.getMainLooper());
        this.allowedObserver = new ContentObserver(handler) { // from class: com.android.systemui.demomode.DemoModeAvailabilityTracker$allowedObserver$1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                boolean checkIsDemoModeAllowed;
                checkIsDemoModeAllowed = DemoModeAvailabilityTracker.this.checkIsDemoModeAllowed();
                if (DemoModeAvailabilityTracker.this.isDemoModeAvailable() == checkIsDemoModeAllowed) {
                    return;
                }
                DemoModeAvailabilityTracker.this.setDemoModeAvailable(checkIsDemoModeAllowed);
                DemoModeAvailabilityTracker.this.onDemoModeAvailabilityChanged();
            }
        };
        final Handler handler2 = new Handler(Looper.getMainLooper());
        this.onObserver = new ContentObserver(handler2) { // from class: com.android.systemui.demomode.DemoModeAvailabilityTracker$onObserver$1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                boolean checkIsDemoModeOn;
                checkIsDemoModeOn = DemoModeAvailabilityTracker.this.checkIsDemoModeOn();
                if (DemoModeAvailabilityTracker.this.isInDemoMode() == checkIsDemoModeOn) {
                    return;
                }
                DemoModeAvailabilityTracker.this.setInDemoMode(checkIsDemoModeOn);
                if (checkIsDemoModeOn) {
                    DemoModeAvailabilityTracker.this.onDemoModeStarted();
                } else {
                    DemoModeAvailabilityTracker.this.onDemoModeFinished();
                }
            }
        };
    }

    public final boolean checkIsDemoModeAllowed() {
        boolean z = false;
        if (this.globalSettings.getInt("sysui_demo_allowed", 0) != 0) {
            z = true;
        }
        return z;
    }

    public final boolean checkIsDemoModeOn() {
        boolean z = false;
        if (this.globalSettings.getInt("sysui_tuner_demo_on", 0) != 0) {
            z = true;
        }
        return z;
    }

    public final boolean isDemoModeAvailable() {
        return this.isDemoModeAvailable;
    }

    public final boolean isInDemoMode() {
        return this.isInDemoMode;
    }

    public abstract void onDemoModeAvailabilityChanged();

    public abstract void onDemoModeFinished();

    public abstract void onDemoModeStarted();

    public final void setDemoModeAvailable(boolean z) {
        this.isDemoModeAvailable = z;
    }

    public final void setInDemoMode(boolean z) {
        this.isInDemoMode = z;
    }

    public final void startTracking() {
        ContentResolver contentResolver = this.context.getContentResolver();
        contentResolver.registerContentObserver(this.globalSettings.getUriFor("sysui_demo_allowed"), false, this.allowedObserver);
        contentResolver.registerContentObserver(this.globalSettings.getUriFor("sysui_tuner_demo_on"), false, this.onObserver);
    }

    public final void stopTracking() {
        ContentResolver contentResolver = this.context.getContentResolver();
        contentResolver.unregisterContentObserver(this.allowedObserver);
        contentResolver.unregisterContentObserver(this.onObserver);
    }
}