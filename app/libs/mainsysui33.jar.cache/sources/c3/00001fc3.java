package com.android.systemui.plugins.log;

import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/log/LogMessageImplKt.class */
public final class LogMessageImplKt {
    private static final Function1<LogMessage, String> DEFAULT_PRINTER = new Function1<LogMessage, String>() { // from class: com.android.systemui.plugins.log.LogMessageImplKt$DEFAULT_PRINTER$1
        /* JADX DEBUG: Method merged with bridge method */
        public final String invoke(LogMessage logMessage) {
            return "Unknown message: " + logMessage;
        }
    };
    private static final String DEFAULT_TAG = "UnknownTag";
}