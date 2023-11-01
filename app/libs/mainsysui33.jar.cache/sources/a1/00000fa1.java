package com.android.systemui;

import android.view.MotionEvent;

/* loaded from: mainsysui33.jar:com/android/systemui/Gefingerpoken.class */
public interface Gefingerpoken {
    default boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    default boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }
}