package com.android.keyguard.logging;

import com.android.systemui.plugins.log.LogBuffer;
import com.android.systemui.plugins.log.LogLevel;
import com.android.systemui.plugins.log.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/keyguard/logging/BiometricMessageDeferralLogger.class */
public class BiometricMessageDeferralLogger {
    public final LogBuffer logBuffer;
    public final String tag;

    public BiometricMessageDeferralLogger(LogBuffer logBuffer, String str) {
        this.logBuffer = logBuffer;
        this.tag = str;
    }

    public final void logFrameProcessed(int i, int i2, String str) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain(this.tag, LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.BiometricMessageDeferralLogger$logFrameProcessed$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                String str1 = logMessage.getStr1();
                return "frameProcessed acquiredInfo=" + int1 + " totalFrames=" + int2 + " messageToShowOnTimeout=" + str1;
            }
        }, null);
        obtain.setInt1(i);
        obtain.setInt2(i2);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logUpdateMessage(int i, String str) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain(this.tag, LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.BiometricMessageDeferralLogger$logUpdateMessage$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                return "updateMessage acquiredInfo=" + int1 + " helpString=" + str1;
            }
        }, null);
        obtain.setInt1(i);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void reset() {
        this.logBuffer.log(this.tag, LogLevel.DEBUG, "reset");
    }
}