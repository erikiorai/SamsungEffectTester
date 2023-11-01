package com.android.keyguard.logging;

import com.android.systemui.plugins.log.LogBuffer;

/* loaded from: mainsysui33.jar:com/android/keyguard/logging/FaceMessageDeferralLogger.class */
public final class FaceMessageDeferralLogger extends BiometricMessageDeferralLogger {
    public final LogBuffer logBuffer;

    public FaceMessageDeferralLogger(LogBuffer logBuffer) {
        super(logBuffer, "FaceMessageDeferralLogger");
        this.logBuffer = logBuffer;
    }
}