package com.android.systemui.plugins.log;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/log/LogcatEchoTrackerProd.class */
public final class LogcatEchoTrackerProd implements LogcatEchoTracker {
    private final boolean logInBackgroundThread;

    @Override // com.android.systemui.plugins.log.LogcatEchoTracker
    public boolean getLogInBackgroundThread() {
        return this.logInBackgroundThread;
    }

    @Override // com.android.systemui.plugins.log.LogcatEchoTracker
    public boolean isBufferLoggable(String str, LogLevel logLevel) {
        return logLevel.compareTo(LogLevel.WARNING) >= 0;
    }

    @Override // com.android.systemui.plugins.log.LogcatEchoTracker
    public boolean isTagLoggable(String str, LogLevel logLevel) {
        return logLevel.compareTo(LogLevel.WARNING) >= 0;
    }
}