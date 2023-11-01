package com.android.systemui.doze;

import java.text.SimpleDateFormat;
import java.util.Locale;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeLoggerKt.class */
public final class DozeLoggerKt {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.S", Locale.US);

    public static final SimpleDateFormat getDATE_FORMAT() {
        return DATE_FORMAT;
    }
}