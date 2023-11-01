package com.android.systemui.media.taptotransfer.common;

import com.android.systemui.plugins.log.LogBuffer;
import com.android.systemui.plugins.log.LogLevel;
import com.android.systemui.plugins.log.LogMessage;
import com.android.systemui.temporarydisplay.TemporaryViewInfo;
import com.android.systemui.temporarydisplay.TemporaryViewLogger;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/common/MediaTttLogger.class */
public final class MediaTttLogger<T extends TemporaryViewInfo> extends TemporaryViewLogger<T> {
    public MediaTttLogger(String str, LogBuffer logBuffer) {
        super(logBuffer, "MediaTtt" + str);
    }

    public final void logInvalidStateTransitionError(String str, String str2) {
        LogBuffer buffer$frameworks__base__packages__SystemUI__android_common__SystemUI_core = getBuffer$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        LogMessage obtain = buffer$frameworks__base__packages__SystemUI__android_common__SystemUI_core.obtain(getTag$frameworks__base__packages__SystemUI__android_common__SystemUI_core(), LogLevel.ERROR, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.taptotransfer.common.MediaTttLogger$logInvalidStateTransitionError$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str22 = logMessage.getStr2();
                String str1 = logMessage.getStr1();
                return "Cannot display state=" + str22 + " after state=" + str1 + "; invalid transition";
            }
        }, null);
        obtain.setStr1(str);
        obtain.setStr2(str2);
        buffer$frameworks__base__packages__SystemUI__android_common__SystemUI_core.commit(obtain);
    }

    public final void logPackageNotFound(String str) {
        LogBuffer buffer$frameworks__base__packages__SystemUI__android_common__SystemUI_core = getBuffer$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        LogMessage obtain = buffer$frameworks__base__packages__SystemUI__android_common__SystemUI_core.obtain(getTag$frameworks__base__packages__SystemUI__android_common__SystemUI_core(), LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.taptotransfer.common.MediaTttLogger$logPackageNotFound$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "Package " + str1 + " could not be found";
            }
        }, null);
        obtain.setStr1(str);
        buffer$frameworks__base__packages__SystemUI__android_common__SystemUI_core.commit(obtain);
    }

    public final void logRemovalBypass(String str, String str2) {
        LogBuffer buffer$frameworks__base__packages__SystemUI__android_common__SystemUI_core = getBuffer$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        LogMessage obtain = buffer$frameworks__base__packages__SystemUI__android_common__SystemUI_core.obtain(getTag$frameworks__base__packages__SystemUI__android_common__SystemUI_core(), LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.taptotransfer.common.MediaTttLogger$logRemovalBypass$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                return "Chip removal requested due to " + str1 + "; however, removal was ignored because " + str22;
            }
        }, null);
        obtain.setStr1(str);
        obtain.setStr2(str2);
        buffer$frameworks__base__packages__SystemUI__android_common__SystemUI_core.commit(obtain);
    }

    public final void logStateChange(String str, String str2, String str3) {
        LogBuffer buffer$frameworks__base__packages__SystemUI__android_common__SystemUI_core = getBuffer$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        LogMessage obtain = buffer$frameworks__base__packages__SystemUI__android_common__SystemUI_core.obtain(getTag$frameworks__base__packages__SystemUI__android_common__SystemUI_core(), LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.taptotransfer.common.MediaTttLogger$logStateChange$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                String str32 = logMessage.getStr3();
                return "State changed to " + str1 + " for ID=" + str22 + " package=" + str32;
            }
        }, null);
        obtain.setStr1(str);
        obtain.setStr2(str2);
        obtain.setStr3(str3);
        buffer$frameworks__base__packages__SystemUI__android_common__SystemUI_core.commit(obtain);
    }

    public final void logStateChangeError(int i) {
        LogBuffer buffer$frameworks__base__packages__SystemUI__android_common__SystemUI_core = getBuffer$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        LogMessage obtain = buffer$frameworks__base__packages__SystemUI__android_common__SystemUI_core.obtain(getTag$frameworks__base__packages__SystemUI__android_common__SystemUI_core(), LogLevel.ERROR, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.taptotransfer.common.MediaTttLogger$logStateChangeError$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                return "Cannot display state=" + int1 + "; aborting";
            }
        }, null);
        obtain.setInt1(i);
        buffer$frameworks__base__packages__SystemUI__android_common__SystemUI_core.commit(obtain);
    }
}