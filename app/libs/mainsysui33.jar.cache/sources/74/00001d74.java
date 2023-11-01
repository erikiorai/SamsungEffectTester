package com.android.systemui.media.muteawait;

import com.android.systemui.plugins.log.LogBuffer;
import com.android.systemui.plugins.log.LogLevel;
import com.android.systemui.plugins.log.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/media/muteawait/MediaMuteAwaitLogger.class */
public final class MediaMuteAwaitLogger {
    public final LogBuffer buffer;

    public MediaMuteAwaitLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logMutedDeviceAdded(String str, String str2, boolean z) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaMuteAwait", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.muteawait.MediaMuteAwaitLogger$logMutedDeviceAdded$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                boolean bool1 = logMessage.getBool1();
                return "Muted device added: address=" + str1 + " name=" + str22 + " hasMediaUsage=" + bool1;
            }
        }, null);
        obtain.setStr1(str);
        obtain.setStr2(str2);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logMutedDeviceRemoved(String str, String str2, boolean z, boolean z2) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaMuteAwait", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.muteawait.MediaMuteAwaitLogger$logMutedDeviceRemoved$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                return "Muted device removed: address=" + str1 + " name=" + str22 + " hasMediaUsage=" + bool1 + " isMostRecentDevice=" + bool2;
            }
        }, null);
        obtain.setStr1(str);
        obtain.setStr2(str2);
        obtain.setBool1(z);
        obtain.setBool2(z2);
        logBuffer.commit(obtain);
    }
}