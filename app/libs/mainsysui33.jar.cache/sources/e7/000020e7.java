package com.android.systemui.qs;

import com.android.systemui.plugins.log.LogBuffer;
import com.android.systemui.plugins.log.LogLevel;
import com.android.systemui.plugins.log.LogMessage;
import com.android.systemui.statusbar.DisableFlagsLogger;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSFragmentDisableFlagsLogger.class */
public final class QSFragmentDisableFlagsLogger {
    public final LogBuffer buffer;
    public final DisableFlagsLogger disableFlagsLogger;

    public QSFragmentDisableFlagsLogger(LogBuffer logBuffer, DisableFlagsLogger disableFlagsLogger) {
        this.buffer = logBuffer;
        this.disableFlagsLogger = disableFlagsLogger;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSFragmentDisableFlagsLogger$logDisableFlagChange$2.invoke(com.android.systemui.plugins.log.LogMessage):java.lang.String] */
    public static final /* synthetic */ DisableFlagsLogger access$getDisableFlagsLogger$p(QSFragmentDisableFlagsLogger qSFragmentDisableFlagsLogger) {
        return qSFragmentDisableFlagsLogger.disableFlagsLogger;
    }

    public final void logDisableFlagChange(DisableFlagsLogger.DisableState disableState, DisableFlagsLogger.DisableState disableState2) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSFragmentDisableFlagsLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.qs.QSFragmentDisableFlagsLogger$logDisableFlagChange$2
            {
                super(1);
            }

            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                return QSFragmentDisableFlagsLogger.access$getDisableFlagsLogger$p(QSFragmentDisableFlagsLogger.this).getDisableFlagsString((DisableFlagsLogger.DisableState) null, new DisableFlagsLogger.DisableState(logMessage.getInt1(), logMessage.getInt2()), new DisableFlagsLogger.DisableState((int) logMessage.getLong1(), (int) logMessage.getLong2()));
            }
        }, null);
        obtain.setInt1(disableState.getDisable1());
        obtain.setInt2(disableState.getDisable2());
        obtain.setLong1(disableState2.getDisable1());
        obtain.setLong2(disableState2.getDisable2());
        logBuffer.commit(obtain);
    }
}