package com.android.settingslib.core.instrumentation;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/* loaded from: mainsysui33.jar:com/android/settingslib/core/instrumentation/VisibilityLoggerMixin.class */
public class VisibilityLoggerMixin implements LifecycleObserver {
    public long mCreationTimestamp;

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        this.mCreationTimestamp = 0L;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
    }
}