package com.android.systemui.log;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.log.LogBuffer;
import com.android.systemui.plugins.log.LogcatEchoTracker;

/* loaded from: mainsysui33.jar:com/android/systemui/log/LogBufferFactory.class */
public final class LogBufferFactory {
    public final DumpManager dumpManager;
    public final LogcatEchoTracker logcatEchoTracker;

    public LogBufferFactory(DumpManager dumpManager, LogcatEchoTracker logcatEchoTracker) {
        this.dumpManager = dumpManager;
        this.logcatEchoTracker = logcatEchoTracker;
    }

    public static /* synthetic */ LogBuffer create$default(LogBufferFactory logBufferFactory, String str, int i, boolean z, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z = true;
        }
        return logBufferFactory.create(str, i, z);
    }

    public final LogBuffer create(String str, int i) {
        return create$default(this, str, i, false, 4, null);
    }

    public final LogBuffer create(String str, int i, boolean z) {
        LogBuffer logBuffer = new LogBuffer(str, LogBufferHelper.Companion.adjustMaxSize(i), this.logcatEchoTracker, z);
        this.dumpManager.registerBuffer(str, logBuffer);
        return logBuffer;
    }
}