package com.android.systemui.plugins.log;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/log/LogcatEchoTracker.class */
public interface LogcatEchoTracker {
    boolean getLogInBackgroundThread();

    boolean isBufferLoggable(String str, LogLevel logLevel);

    boolean isTagLoggable(String str, LogLevel logLevel);
}