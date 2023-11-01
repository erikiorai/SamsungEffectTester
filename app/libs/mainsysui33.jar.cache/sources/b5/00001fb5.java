package com.android.systemui.plugins.log;

import com.google.errorprone.annotations.CompileTimeConstant;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/log/ConstantStringsLoggerImpl.class */
public final class ConstantStringsLoggerImpl implements ConstantStringsLogger {
    private final LogBuffer buffer;
    private final String tag;

    public ConstantStringsLoggerImpl(LogBuffer logBuffer, String str) {
        this.buffer = logBuffer;
        this.tag = str;
    }

    @Override // com.android.systemui.plugins.log.ConstantStringsLogger
    public void d(@CompileTimeConstant String str) {
        this.buffer.log(this.tag, LogLevel.DEBUG, str);
    }

    @Override // com.android.systemui.plugins.log.ConstantStringsLogger
    public void e(@CompileTimeConstant String str) {
        this.buffer.log(this.tag, LogLevel.ERROR, str);
    }

    public final LogBuffer getBuffer() {
        return this.buffer;
    }

    public final String getTag() {
        return this.tag;
    }

    @Override // com.android.systemui.plugins.log.ConstantStringsLogger
    public void v(@CompileTimeConstant String str) {
        this.buffer.log(this.tag, LogLevel.VERBOSE, str);
    }

    @Override // com.android.systemui.plugins.log.ConstantStringsLogger
    public void w(@CompileTimeConstant String str) {
        this.buffer.log(this.tag, LogLevel.WARNING, str);
    }
}