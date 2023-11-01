package com.android.systemui.media.nearby;

import com.android.systemui.plugins.log.LogBuffer;
import com.android.systemui.plugins.log.LogLevel;
import com.android.systemui.plugins.log.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/media/nearby/NearbyMediaDevicesLogger.class */
public final class NearbyMediaDevicesLogger {
    public final LogBuffer buffer;

    public NearbyMediaDevicesLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logProviderBinderDied(int i) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NearbyMediaDevices", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.nearby.NearbyMediaDevicesLogger$logProviderBinderDied$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                return "Provider binder died; total providers = " + int1;
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logProviderRegistered(int i) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NearbyMediaDevices", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.nearby.NearbyMediaDevicesLogger$logProviderRegistered$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                return "Provider registered; total providers = " + int1;
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logProviderUnregistered(int i) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NearbyMediaDevices", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.media.nearby.NearbyMediaDevicesLogger$logProviderUnregistered$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                return "Provider unregistered; total providers = " + int1;
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }
}