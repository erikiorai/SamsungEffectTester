package com.android.settingslib.core.lifecycle;

import android.app.Activity;
import android.provider.Settings;
import android.util.EventLog;
import android.view.Window;
import android.view.WindowManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/* loaded from: mainsysui33.jar:com/android/settingslib/core/lifecycle/HideNonSystemOverlayMixin.class */
public class HideNonSystemOverlayMixin implements LifecycleObserver {
    public final Activity mActivity;

    public boolean isEnabled() {
        boolean z = false;
        if (Settings.Secure.getInt(this.mActivity.getContentResolver(), "secure_overlay_settings", 0) == 0) {
            z = true;
        }
        return z;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        if (this.mActivity == null || !isEnabled()) {
            return;
        }
        this.mActivity.getWindow().addSystemFlags(524288);
        EventLog.writeEvent(1397638484, "120484087", -1, "");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        if (this.mActivity == null || !isEnabled()) {
            return;
        }
        Window window = this.mActivity.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.privateFlags &= -524289;
        window.setAttributes(attributes);
    }
}