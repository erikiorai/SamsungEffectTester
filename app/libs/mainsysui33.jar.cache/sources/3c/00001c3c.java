package com.android.systemui.media.controls.pipeline;

import android.media.session.PlaybackState;
import com.android.systemui.plugins.log.LogBuffer;
import com.android.systemui.plugins.log.LogLevel;
import com.android.systemui.plugins.log.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/MediaTimeoutLogger.class */
public final class MediaTimeoutLogger {
    public final LogBuffer buffer;

    public MediaTimeoutLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logCancelIgnored(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaTimeout", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutLogger$logCancelIgnored$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "cancellation already exists for " + str1;
            }
        }, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logDelayedUpdate(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaTimeout", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutLogger$logDelayedUpdate$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "deliver delayed playback state for " + str1;
            }
        }, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logMigrateListener(String str, String str2, boolean z) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaTimeout", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutLogger$logMigrateListener$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                boolean bool1 = logMessage.getBool1();
                return "migrate from " + str1 + " to " + str22 + ", had listener? " + bool1;
            }
        }, null);
        obtain.setStr1(str);
        obtain.setStr2(str2);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logPlaybackState(String str, PlaybackState playbackState) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaTimeout", LogLevel.VERBOSE, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutLogger$logPlaybackState$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                return "state update: key=" + str1 + " state=" + str2;
            }
        }, null);
        obtain.setStr1(str);
        String str2 = null;
        if (playbackState != null) {
            str2 = playbackState.toString();
        }
        obtain.setStr2(str2);
        logBuffer.commit(obtain);
    }

    public final void logReuseListener(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaTimeout", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutLogger$logReuseListener$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "reuse listener: " + str1;
            }
        }, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logScheduleTimeout(String str, boolean z, boolean z2) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaTimeout", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutLogger$logScheduleTimeout$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                return "schedule timeout " + str1 + ", playing=" + bool1 + " resumption=" + bool2;
            }
        }, null);
        obtain.setStr1(str);
        obtain.setBool1(z);
        obtain.setBool2(z2);
        logBuffer.commit(obtain);
    }

    public final void logSessionDestroyed(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaTimeout", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutLogger$logSessionDestroyed$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "session destroyed " + str1;
            }
        }, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logStateCallback(final String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaTimeout", LogLevel.VERBOSE, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutLogger$logStateCallback$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str2 = str;
                return "dispatching state update for " + str2;
            }
        }, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logTimeout(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaTimeout", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutLogger$logTimeout$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "execute timeout for " + str1;
            }
        }, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logTimeoutCancelled(String str, String str2) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaTimeout", LogLevel.VERBOSE, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutLogger$logTimeoutCancelled$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                return "media timeout cancelled for " + str1 + ", reason: " + str22;
            }
        }, null);
        obtain.setStr1(str);
        obtain.setStr2(str2);
        logBuffer.commit(obtain);
    }

    public final void logUpdateListener(String str, boolean z) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaTimeout", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutLogger$logUpdateListener$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                return "updating " + str1 + ", was playing? " + bool1;
            }
        }, null);
        obtain.setStr1(str);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }
}