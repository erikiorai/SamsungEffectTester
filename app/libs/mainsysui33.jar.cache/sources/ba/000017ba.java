package com.android.systemui.flags;

import android.util.Log;
import com.android.systemui.keyguard.WakefulnessLifecycle;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/FeatureFlagsDebugRestarter.class */
public final class FeatureFlagsDebugRestarter implements Restarter {
    public boolean androidRestartRequested;
    public final WakefulnessLifecycle.Observer observer = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.flags.FeatureFlagsDebugRestarter$observer$1
        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public void onFinishedGoingToSleep() {
            Log.d("SysUIFlags", "Restarting due to systemui flag change");
            FeatureFlagsDebugRestarter.access$restartNow(FeatureFlagsDebugRestarter.this);
        }
    };
    public final SystemExitRestarter systemExitRestarter;
    public final WakefulnessLifecycle wakefulnessLifecycle;

    public FeatureFlagsDebugRestarter(WakefulnessLifecycle wakefulnessLifecycle, SystemExitRestarter systemExitRestarter) {
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        this.systemExitRestarter = systemExitRestarter;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.flags.FeatureFlagsDebugRestarter$observer$1.onFinishedGoingToSleep():void] */
    public static final /* synthetic */ void access$restartNow(FeatureFlagsDebugRestarter featureFlagsDebugRestarter) {
        featureFlagsDebugRestarter.restartNow();
    }

    @Override // com.android.systemui.flags.Restarter
    public void restartAndroid() {
        Log.d("SysUIFlags", "Android Restart requested. Restarting on next screen off.");
        this.androidRestartRequested = true;
        scheduleRestart();
    }

    public final void restartNow() {
        if (this.androidRestartRequested) {
            this.systemExitRestarter.restartAndroid();
        } else {
            this.systemExitRestarter.restartSystemUI();
        }
    }

    @Override // com.android.systemui.flags.Restarter
    public void restartSystemUI() {
        Log.d("SysUIFlags", "SystemUI Restart requested. Restarting on next screen off.");
        scheduleRestart();
    }

    public final void scheduleRestart() {
        if (this.wakefulnessLifecycle.getWakefulness() == 0) {
            restartNow();
        } else {
            this.wakefulnessLifecycle.addObserver(this.observer);
        }
    }
}