package com.android.systemui.log;

import android.app.ActivityManager;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/log/LogBufferHelper.class */
public final class LogBufferHelper {
    public static final Companion Companion = new Companion(null);

    /* loaded from: mainsysui33.jar:com/android/systemui/log/LogBufferHelper$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final int adjustMaxSize(int i) {
            int i2 = i;
            if (ActivityManager.isLowRamDeviceStatic()) {
                i2 = Math.min(i, 20);
            }
            return i2;
        }
    }
}