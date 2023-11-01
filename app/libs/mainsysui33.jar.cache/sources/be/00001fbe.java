package com.android.systemui.plugins.log;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/log/LogLevel.class */
public enum LogLevel {
    VERBOSE(2, "V"),
    DEBUG(3, "D"),
    INFO(4, "I"),
    WARNING(5, "W"),
    ERROR(6, "E"),
    WTF(7, "WTF");
    
    private final int nativeLevel;
    private final String shortString;

    LogLevel(int i, String str) {
        this.nativeLevel = i;
        this.shortString = str;
    }

    public final int getNativeLevel() {
        return this.nativeLevel;
    }

    public final String getShortString() {
        return this.shortString;
    }
}