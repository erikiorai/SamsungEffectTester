package com.android.systemui.media.controls.resume;

import android.content.ComponentName;
import com.android.systemui.plugins.log.LogBuffer;
import com.android.systemui.plugins.log.LogLevel;
import com.android.systemui.plugins.log.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/resume/ResumeMediaBrowserLogger.class */
public final class ResumeMediaBrowserLogger {
    public final LogBuffer buffer;

    public ResumeMediaBrowserLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logConnection(ComponentName componentName, String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaBrowser", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.controls.resume.ResumeMediaBrowserLogger$logConnection$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                return "Connecting browser for component " + str1 + " due to " + str2;
            }
        }, null);
        obtain.setStr1(componentName.toShortString());
        obtain.setStr2(str);
        logBuffer.commit(obtain);
    }

    public final void logDisconnect(ComponentName componentName) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaBrowser", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.controls.resume.ResumeMediaBrowserLogger$logDisconnect$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "Disconnecting browser for component " + str1;
            }
        }, null);
        obtain.setStr1(componentName.toShortString());
        logBuffer.commit(obtain);
    }

    public final void logSessionDestroyed(boolean z, ComponentName componentName) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaBrowser", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.controls.resume.ResumeMediaBrowserLogger$logSessionDestroyed$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                String str1 = logMessage.getStr1();
                return "Session destroyed. Active browser = " + bool1 + ". Browser component = " + str1 + ".";
            }
        }, null);
        obtain.setBool1(z);
        obtain.setStr1(componentName.toShortString());
        logBuffer.commit(obtain);
    }
}