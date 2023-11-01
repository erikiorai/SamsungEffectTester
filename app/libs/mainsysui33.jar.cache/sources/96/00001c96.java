package com.android.systemui.media.controls.ui;

import com.android.systemui.plugins.log.LogBuffer;
import com.android.systemui.plugins.log.LogLevel;
import com.android.systemui.plugins.log.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaCarouselControllerLogger.class */
public final class MediaCarouselControllerLogger {
    public final LogBuffer buffer;

    public MediaCarouselControllerLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logMediaLoaded(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaCarouselCtlrLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.controls.ui.MediaCarouselControllerLogger$logMediaLoaded$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "add player " + str1;
            }
        }, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logMediaRemoved(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaCarouselCtlrLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.controls.ui.MediaCarouselControllerLogger$logMediaRemoved$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "removing player " + str1;
            }
        }, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logPotentialMemoryLeak(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaCarouselCtlrLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.controls.ui.MediaCarouselControllerLogger$logPotentialMemoryLeak$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "Potential memory leak: Removing control panel for " + str1 + " from map without calling #onDestroy";
            }
        }, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logRecommendationLoaded(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaCarouselCtlrLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.controls.ui.MediaCarouselControllerLogger$logRecommendationLoaded$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "add recommendation " + str1;
            }
        }, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logRecommendationRemoved(String str, boolean z) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaCarouselCtlrLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.controls.ui.MediaCarouselControllerLogger$logRecommendationRemoved$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                return "removing recommendation " + str1 + ", immediate=" + bool1;
            }
        }, null);
        obtain.setStr1(str);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }
}