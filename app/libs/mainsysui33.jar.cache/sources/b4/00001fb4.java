package com.android.systemui.plugins.log;

import com.google.errorprone.annotations.CompileTimeConstant;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/log/ConstantStringsLogger.class */
public interface ConstantStringsLogger {
    void d(@CompileTimeConstant String str);

    void e(@CompileTimeConstant String str);

    void v(@CompileTimeConstant String str);

    void w(@CompileTimeConstant String str);
}