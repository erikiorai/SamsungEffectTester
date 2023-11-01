package com.android.systemui.media.controls.ui;

import com.android.systemui.plugins.log.LogBuffer;
import com.android.systemui.plugins.log.LogLevel;
import com.android.systemui.plugins.log.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaViewLogger.class */
public final class MediaViewLogger {
    public final LogBuffer buffer;

    public MediaViewLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logMediaLocation(String str, int i, int i2) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaView", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.controls.ui.MediaViewLogger$logMediaLocation$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                return "location (" + str1 + "): " + int1 + " -> " + int2;
            }
        }, null);
        obtain.setStr1(str);
        obtain.setInt1(i);
        obtain.setInt2(i2);
        logBuffer.commit(obtain);
    }

    public final void logMediaSize(String str, int i, int i2) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaView", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.controls.ui.MediaViewLogger$logMediaSize$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                return "size (" + str1 + "): " + int1 + " x " + int2;
            }
        }, null);
        obtain.setStr1(str);
        obtain.setInt1(i);
        obtain.setInt2(i2);
        logBuffer.commit(obtain);
    }
}