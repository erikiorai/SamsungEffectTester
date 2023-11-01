package com.android.systemui.biometrics.udfps;

import android.view.MotionEvent;
import com.android.systemui.biometrics.UdfpsOverlayParams;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/udfps/TouchProcessor.class */
public interface TouchProcessor {
    TouchProcessorResult processTouch(MotionEvent motionEvent, int i, UdfpsOverlayParams udfpsOverlayParams);
}